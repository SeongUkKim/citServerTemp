package egovframework.sys.excl.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class ExcelSheetHandler implements SheetContentsHandler {
	private int currentCol = -1;
	private int currRowNum = 0;
	private static int startRowNum = 0;
	private static int headerSize = 0;
	private static int checkHeaderRowNum = -1;

	// xlsx 처리 시 필요한 컬렉션
	private List<List<String>> rows = new ArrayList<List<String>>();
	private List<String> row = new ArrayList<String>();
	private List<String> header = new ArrayList<String>();

	// xlsx 처리 메서드
	public static ExcelSheetHandler readExcelXlsx(File destFile) {
		// 핸들러 정의 및 생성
		ExcelSheetHandler sheetHandler = new ExcelSheetHandler();

		try {
			// OPCPackage를 통해 전달받은 xlsx 파일에 액세스하고 읽기, 쓰기 작업을 처리하기 위한 필수 단계입니다.
			OPCPackage opc = OPCPackage.open(destFile);

			// XSSFReader를 이용해 액세스한 xlsx 파일을 읽게 해줍니다.
			XSSFReader xssfReader = new XSSFReader(opc);

			// getStyletable()로 XSSFReader로 읽어온 xlsx 파일의 스타일을 가져옵니다.
			StylesTable styles = xssfReader.getStylesTable();

			// opc로 연 xlsx 데이터의 공유 문자열을 관리하는 테이블을 생성합니다.
			ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opc);

			// startRowNum = 0 을 통해 시작 행의 번호를 초기화 해줍니다.
			startRowNum = 0;

			while (xssfReader.getSheetsData().hasNext()) {

				// xssfReader로 읽은 xlsx 파일의 시트 데이터를 가져오고 인풋소스를 생성해 XML 리더에 사용합니다.
				InputStream inputStream = xssfReader.getSheetsData().next();
				InputSource inputSource = new InputSource(inputStream);

				// XSSFSheetXSMLHandler를 이용해 XML 기반의 시트 데이터를 처리하며, ContentHandler를 생성합니다.
				ContentHandler handle = new XSSFSheetXMLHandler(styles, strings, sheetHandler, false);

				// 위에서 XML 데이터로 만들어줬기에 읽을 수 있는 POI의 SAXHelper 메서드를 이용해 SAX parser인 XML Reader를
				// 생성합니다.
				XMLReader xmlReader = SAXHelper.newXMLReader();

				// XML Reader에 XML 기반의 시트 데이터인 ContentHandler를 넣어 파싱하는 동안 발생하는 이벤트를 처리해줍니다.
				xmlReader.setContentHandler(handle);

				// xmlReader.parse()를 이용해 XML 데이터를 파싱합니다.
				xmlReader.parse(inputSource);

				// 사용했던 자원을 반환합니다.
				inputStream.close();
				opc.close();
			}

		} catch (Exception e) {
			// 로직에 맞게 예외처리 할 것
		}
		return sheetHandler;
	}

	// xlsx 행 처리를 위한 메서드 생성
	public List<String> getHeaderRow() {
		return header;
	}

	public List<List<String>> getRows() {
		return rows;
	}

	// 아래부턴 XSSFSheetXMLHandler > SheetContentsHandler 오버라이딩
	@Override
	public void startRow(int arg0) {
		this.currentCol = -1;
		this.currRowNum = arg0;
	}

	// 빈 컬럼을 처리
	@Override
	public void cell(String columnName, String value, XSSFComment var3) {
		int iCol = (new CellReference(columnName)).getCol();
		int emptyCol = iCol - currentCol - 1;

		if (row.size() < headerSize) {
			for (int i = 0; i < emptyCol; i++) {
				row.add("");
			}
		}

		currentCol = iCol;

		if (currRowNum > checkHeaderRowNum) {
			if (currentCol > headerSize) {
				headerSize++;
			}
		}

		row.add(value);
	}

	// 마지막 행 체크 및 처리
	@Override
	public void endRow(int rowNum) {
		if (rowNum >= startRowNum) {
			if (rowNum == startRowNum) {
				for (int i = 0; i < headerSize; i++) {
					String str = row.get(i).replaceAll(System.getProperty("line.separator").toString(), " ");
					str = str.replaceAll("(\r\n|\r|\n|\n\r)", " ");
					row.set(i, str);
				}
				header = new ArrayList(row);
				row.clear();
			} else {
				if (row.size() < (header.size() + 1)) {
					for (int i = row.size(); i < header.size(); i++) {
						row.add("");
					}
					rows.add(new ArrayList(row));
				}
				row.clear();
			}
		} else {
			row.clear();
		}
		checkHeaderRowNum++;
	}

	@Override
	public void headerFooter(String arg0, boolean arg1, String arg2) {
		// TODO Auto-generated method stub
	}

	// xls 처리 시 필요한 컬렉션
	private static List<List<String>> sRows = new ArrayList<List<String>>();
	private static List<String> sRow = new ArrayList<String>();
	private static List<String> sHeader = new ArrayList<String>();

	// xls 처리 메서드
	public static ExcelSheetHandler readExcelXls(File destFile) {
		// 핸들러 정의 및 생성
		ExcelSheetHandler sheetHandler = new ExcelSheetHandler();
		try {
			FileInputStream is = null;
			is = new FileInputStream(destFile);

			sRows = new ArrayList<List<String>>();
			sRow = new ArrayList<String>();
			sHeader = new ArrayList<String>();

			// 스트림으로 연 파일 workbook 처리
			HSSFWorkbook workBook = new HSSFWorkbook(is);
			if (workBook != null) {

				// 시트 가져오기
				HSSFSheet sheet = workBook.getSheetAt(0);

				// 행의 수 가져오기
				int sRowCnt = sheet.getPhysicalNumberOfRows();

				// 셀의 수 가져오기
				int hssfCellCnt = sheet.getRow(0).getPhysicalNumberOfCells();

				// 행의 수만큼 반복문을 실행하여 데이터 처리하기
				for (int i = 0; i < sRowCnt; i++) {
					// 시트의 행 데이터 가져오기
					HSSFRow hssfRow = sheet.getRow(i);
					if (hssfRow != null) {
						for (int j = 0; j < hssfCellCnt; j++) {
							// 가져온 행의 각 셀마다 있는 셀 값 가져오기
							HSSFCell hssfCell = hssfRow.getCell(j);
							String val = "";
							DataFormatter df = new DataFormatter();

							if (hssfCell == null) {
								val = "";
							} else {
								// 각기 다른 셀 형식을 String으로 변환
								switch (hssfCell.getCellType()) {
								case HSSFCell.CELL_TYPE_FORMULA:
									val = hssfCell.getCellFormula();
									break;
								case HSSFCell.CELL_TYPE_NUMERIC:
									val = df.formatCellValue(hssfCell);
									break;
								case HSSFCell.CELL_TYPE_STRING:
									val = hssfCell.getStringCellValue() + "";
									break;
								case HSSFCell.CELL_TYPE_BLANK:
									val = hssfCell.getBooleanCellValue() + "";
									break;
								case HSSFCell.CELL_TYPE_ERROR:
									val = hssfCell.getErrorCellValue() + "";
									break;
								default:
									val = new String();
									break;
								}
							}
							// 생성한 핸들러에 데이터 넣기
							sheetHandler.sRow.add(val);
							if ((j + 1) == hssfCellCnt) {
								if (i == 0) {
									for (int k = 0; k < sheetHandler.sRow.size(); k++) {
										String str = sheetHandler.sRow.get(k)
												.replaceAll(System.getProperty("line.separator").toString(), " ");
										str = str.replaceAll("(\r\n|\r|\n|\n\r)", " ");
										sheetHandler.sRow.set(k, str);
									}
									sheetHandler.sHeader = sheetHandler.sRow;
								} else {
									sheetHandler.sRows.add(sheetHandler.sRow);
								}
								sheetHandler.sRow = new ArrayList<String>();
							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			// 로직에 맞게 예외처리 할 것
			e.printStackTrace();
		} catch (IOException e) {
			// 로직에 맞게 예외처리 할 것
			e.printStackTrace();
		}
		return sheetHandler;
	}

	// xls 행 처리를 위한 메서드 생성
	public List<String> getHeadersRow() {
		return sHeader;
	}

	public List<List<String>> getsRows() {
		return sRows;
	}
}