package egovframework.sys.excl;

import egovframework.sys.excl.handler.ExcelSheetHandler;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ExclUploadController {
	private static final Logger logger = LoggerFactory.getLogger(ExclUploadController.class);
	String filePath = "C:/file_uploads/";

	@RequestMapping(value = "/excl/excelUploadSax.do", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public void exclUpload(MultipartHttpServletRequest request, MultipartFile mfile, Model model) throws IOException, SQLException {
		// 업로드 한 파일 받기
		MultipartFile excelFile = request.getFile("excelFile");
		// 파일 및 폴더 생성
		File destFile = new File(filePath + excelFile.getOriginalFilename());
		Boolean isSuccess = true;

		// 폴더 없을 시 생성
		/*
		if (!destFile.exists()) {
			System.out.println("없음");
		}else{
			System.out.println("있음");
			FileOutputStream fileOutputStream = new FileOutputStream(destFile, true);
		}*/
		/*
		try {
			// 업로드한 파일 데이터를 생성한 파일로 전송
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}

		// 생성한 파일 경로 변수 생성
		String filePath = "C:/Users/user/Desktop/dev/" + excelFile.getOriginalFilename();
		*/
		try{
			excelFile.transferTo(destFile);
		}catch (Exception e) {
			e.printStackTrace();
		}
		// 파일의 확장자 변수 생성
		String extension = StringUtils.getFilenameExtension(destFile.getPath());

		Map<String, Object> cntResult = new HashMap<String, Object>();
		List<List<String>> excelDatas;
		List<String> headList;

		// xls 처리
		if (extension.equals("xls")) {
			// 핸들러 > xls 처리 메서드 호출
			ExcelSheetHandler excelSheetHandler = ExcelSheetHandler.readExcelXls(destFile);
			// 핸들러 > 행 데이터 가져오기
			excelDatas = excelSheetHandler.getsRows();
			// 핸들러 > 헤더 행 데이터 가져오기
			headList = excelSheetHandler.getHeadersRow();

			List<HashMap<String, String>> params = new ArrayList<>();
			// 헤더 키에 따른 밸류 값 넣기
			for (List<String> row : excelDatas) {
				HashMap<String, String> item = new HashMap<>();
				int idx = 0;
				// 헤더 키 값 넣기
				for (String headerName : headList) {
					item.put(headerName, row.get(idx));
					idx++;
				}
				params.add(item);
			}

			// DB 처리 시 5000건씩 데이터 보내기
			int UPLOAD_UNIT = 5000;

			// 파티션을 이용한 배열 데이터 자르기
			List<List<HashMap<String, String>>> partedPostData = ListUtils.partition(params, UPLOAD_UNIT);
			for (List<HashMap<String, String>> postData : partedPostData) {
				try {
					// DB와 연동하여 처리할 메서드
					//addBatch(headList, postData);
					System.out.println("run");
				} catch (Exception e) {
					System.out.println(e.getMessage());
					isSuccess = false;
					break;
				}
			}
			// xlsx 처리
		} else {
			ExcelSheetHandler excelSheetHandler = ExcelSheetHandler.readExcelXlsx(destFile);
			excelDatas = excelSheetHandler.getRows();
			headList = excelSheetHandler.getHeaderRow();

			List<HashMap<String, String>> params = new ArrayList<>();
			for (List<String> row : excelDatas) {
				HashMap<String, String> item = new HashMap<>();
				int idx = 0;
				for (String headerName : headList) {
					item.put(headerName, row.get(idx));
					idx++;
				}
				params.add(item);
			}

			int UPLOAD_UNIT = 5000;

			List<List<HashMap<String, String>>> partedPostData = ListUtils.partition(params, UPLOAD_UNIT);
			for (List<HashMap<String, String>> postData : partedPostData) {
				try {
					//addBatch(headList, postData);
					System.out.println("run");
				} catch (Exception e) {
					System.out.println(e.getMessage());
					isSuccess = false;
					break;
				}
			}
		}
		// 생성한 파일 삭제하기
		destFile.delete();
	}

	@Transactional
	public void addBatch(List<String> headers, List<HashMap<String, String>> postData) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into 테이블명 values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			Class.forName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
			// DB 연결
			conn = DriverManager.getConnection("jdbc:log4jdbc:oracle:thin:@IP주소:포트번호:SID", "접속 ID", "비밀번호");
			pstmt = conn.prepareStatement(sql);

			// AutoCommit 해제
			conn.setAutoCommit(false);

			int i = 0;
			// sql 변수에 들어갈 내용을 꺼내어 셋팅 및 처리
			for(HashMap<String, String> postDatum:postData) {
				// 변수 번호 / 데이터 반복 처리
				for(int j = 0; j < headers.size(); j++) {
					pstmt.setString(j + 1, postDatum.get(headers.get(j)));
				}

				pstmt.addBatch();
				pstmt.clearParameters();

				i++;

				// 5000건 단위 수동 커밋
				if((i%5000)==0) {
					pstmt.executeBatch();
					pstmt.clearBatch();
					conn.commit();
				}
			}

			// 5000건 외 나머지 처리
			pstmt.executeBatch();
			conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				throw e;
			} catch (SQLException e1) {
				e.getStackTrace();
				throw e1;
			}
		} finally {
			// 자원 반환
			if(pstmt != null) { try {pstmt.close();pstmt = null; } catch (SQLException e) { e.getStackTrace();}}
			if(conn != null) { try {conn.close();conn = null; } catch (SQLException e) { e.getStackTrace();}}
		}
	}
}
