package egovframework.sys.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import egovframework.sys.cmm.util.MenuInfo;
import egovframework.sys.cmm.util.PagingVO;
import egovframework.sys.main.MainService;
import egovframework.sys.main.create.CampaignCreateService;
import egovframework.sys.scheduler.SchedulerApplicationService;
import egovframework.sys.sec.handler.CitUserDetails;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Resource(name = "adminService")
	private AdminService adminService;

	@Resource(name = "mainService")
	private MainService mainService;

	@Resource(name = "schedulerService")
	private SchedulerApplicationService SchedulerApplicationService;

	@Resource(name = "campaignCreate")
	private CampaignCreateService campaignCreateService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;

	// 캠페인 승인 페이지
	@RequestMapping(value = "/adminIndex.do")
	public ModelAndView adminIndex(ModelAndView mv, HttpServletRequest req, HttpServletResponse res) throws Exception {
		mv.setViewName("admin/adminIndex");
		EgovMap params = this.paramMap(req);

		return mv;
	}

	// 페이징
	@RequestMapping(value = "/ajax/campaignApprove.do", method = RequestMethod.GET)
	public ModelAndView campaignApprove(HttpServletRequest req, HttpServletResponse res, ModelAndView mv)
			throws Exception {
		mv.setViewName("jsonView");
		EgovMap params = this.paramMap(req);

		// 총 게시글 갯수
		int totalRecord = this.campaignCreateService.campaignCount(params);

		// 페이징
		int pageNum = Integer.parseInt((String) params.get("pageNum"));
		PagingVO pagingVO = new PagingVO(totalRecord, pageNum, 15);

		params.put("start", pagingVO.getStart());
		params.put("end", pagingVO.getEnd());

		List<EgovMap> accessLogList = this.campaignCreateService.selectCampaign(params);

		mv.addObject("accessLogList", accessLogList);
		mv.addObject("totalPage", pagingVO.getLastPage());
		mv.addObject("totalRecord", totalRecord);

		return mv;
	}

	// 캠페인 승인 목록
	@RequestMapping(value = "/campaignApprove.do")
	public ModelAndView campaignApprove(ModelAndView mv, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		mv.setViewName("admin/campaignApprove");
		EgovMap params = this.paramMap(req);

		return mv;
	}

	// 페이징
	@RequestMapping(value = "/ajax/campaignApproveList.do", method = RequestMethod.GET)
	public ModelAndView campaignApproveList(HttpServletRequest req, HttpServletResponse res, ModelAndView mv)
			throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);

		// 총 게시글 갯수
		int totalRecord = this.adminService.selectCampainApprovedCount(params);

		// 페이징
		int pageNum = Integer.parseInt((String) params.get("pageNum"));
		PagingVO pagingVO = new PagingVO(totalRecord, pageNum, 15);

		params.put("start", pagingVO.getStart());
		params.put("end", pagingVO.getEnd());

		List<EgovMap> accessLogList = this.adminService.selectCampainApproved(params);

		mv.addObject("accessLogList", accessLogList);
		mv.addObject("totalPage", pagingVO.getLastPage());
		mv.addObject("totalRecord", totalRecord);

		return mv;
	}

	// 캠페인 거절 목록
	@RequestMapping(value = "/campaignRefuse.do")
	public ModelAndView campaignRefuse(ModelAndView mv, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		mv.setViewName("admin/campaignRefuseList");
		EgovMap params = this.paramMap(req);

		return mv;
	}

	// 페이징
	@RequestMapping(value = "/ajax/campaignRefuseList", method = RequestMethod.GET)
	public ModelAndView campaignRefuseList(HttpServletRequest req, HttpServletResponse res, ModelAndView mv)
			throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);

		// 총 게시글 갯수
		int totalRecord = this.adminService.selectCampainRefusedCount(params);

		// 페이징
		int pageNum = Integer.parseInt((String) params.get("pageNum"));
		PagingVO pagingVO = new PagingVO(totalRecord, pageNum, 15);

		params.put("start", pagingVO.getStart());
		params.put("end", pagingVO.getEnd());

		List<EgovMap> accessLogList = this.adminService.selectCampainRefused(params);

		mv.addObject("accessLogList", accessLogList);
		mv.addObject("totalPage", pagingVO.getLastPage());
		mv.addObject("totalRecord", totalRecord);

		return mv;
	}

	// 진행중인 캠페인
	@RequestMapping(value = "/campaignProgress.do")
	public ModelAndView campaignProgress(ModelAndView mv, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		mv.setViewName("admin/campaignProgressList");
		EgovMap params = this.paramMap(req);

		int campaignCount = this.adminService.selectCampainProgressedCount(params);

		// 페이징
		int totalRecord = campaignCount;

		int pageNum;
		if (params.get("pageNum") == null) {
			pageNum = 1;
		} else {
			pageNum = Integer.parseInt((String) params.get("pageNum"));
		}

		PagingVO pagingVO = new PagingVO(totalRecord, pageNum, 5);

		params.put("start", pageNum - 1);
		params.put("end", pagingVO.getEnd());

		List<EgovMap> campaignListPage = this.adminService.selectCampainProgressed(params);

		mv.addObject("list", campaignListPage);
		mv.addObject("page", pagingVO);

		return mv;
	}

	// 페이징
	@RequestMapping(value = "/ajax/campaignProgressList", method = RequestMethod.GET)
	public ModelAndView campaignProgressList(HttpServletRequest req, HttpServletResponse res, ModelAndView mv)
			throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);

		// 총 게시글 갯수
		int totalRecord = this.adminService.selectCampainProgressedCount(params);

		// 페이징
		int pageNum = Integer.parseInt((String) params.get("pageNum"));
		PagingVO pagingVO = new PagingVO(totalRecord, pageNum, 15);

		params.put("start", pagingVO.getStart());
		params.put("end", pagingVO.getEnd());

		List<EgovMap> accessLogList = this.adminService.selectCampainProgressed(params);

		mv.addObject("accessLogList", accessLogList);
		mv.addObject("totalPage", pagingVO.getLastPage());
		mv.addObject("totalRecord", totalRecord);

		return mv;
	}

	// 사용자 인증 요청
	@RequestMapping(value = "/memberManagement.do")
	public ModelAndView memberManagement(ModelAndView mv, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		mv.setViewName("admin/memberManagement");
		EgovMap params = this.paramMap(req);

		return mv;
	}

	// 페이징
	@RequestMapping(value = "/ajax/memberMenagementList", method = RequestMethod.GET)
	public ModelAndView memberMenagementList(HttpServletRequest req, HttpServletResponse res, ModelAndView mv)
			throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);

		// 총 게시글 갯수
		int totalRecord = this.adminService.selectMemberCount(params);

		// 페이징
		int pageNum = Integer.parseInt((String) params.get("pageNum"));
		PagingVO pagingVO = new PagingVO(totalRecord, pageNum, 15);

		params.put("start", pagingVO.getStart());
		params.put("end", pagingVO.getEnd());

		List<EgovMap> accessLogList = this.adminService.selectMember(params);

		mv.addObject("accessLogList", accessLogList);
		mv.addObject("totalPage", pagingVO.getLastPage());
		mv.addObject("totalRecord", totalRecord);

		return mv;
	}

	// 인증된 사용자
	@RequestMapping(value = "/authManagement.do")
	public ModelAndView authManagement(ModelAndView mv, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		mv.setViewName("admin/authManagement");
		EgovMap params = this.paramMap(req);

		return mv;
	}

	// 페이징
	@RequestMapping(value = "/ajax/authMenagementList.do", method = RequestMethod.GET)
	public ModelAndView authMenagementList(HttpServletRequest req, HttpServletResponse res, ModelAndView mv)
			throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);

		// 총 게시글 갯수
		int totalRecord = this.adminService.selectAuthMemberCount(params);

		// 페이징
		int pageNum = Integer.parseInt((String) params.get("pageNum"));
		PagingVO pagingVO = new PagingVO(totalRecord, pageNum, 15);

		params.put("start", pagingVO.getStart());
		params.put("end", pagingVO.getEnd());

		List<EgovMap> accessLogList = this.adminService.selectAuthMember(params);

		mv.addObject("accessLogList", accessLogList);
		mv.addObject("totalPage", pagingVO.getLastPage());
		mv.addObject("totalRecord", totalRecord);

		return mv;
	}

	// 로그관리 /admin/logManagement.do
	@RequestMapping(value = "/logManagement.do")
	public ModelAndView logManagement(ModelAndView mv, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		mv.setViewName("admin/logManagement");
		EgovMap params = this.paramMap(req);

		return mv;
	}

	// 스케줄러 로그 관리
	@RequestMapping(value = "/SchedulerlogManagement.do")
	public ModelAndView SchedulerlogManagement(ModelAndView mv, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		mv.setViewName("admin/SchedulerlogManagement");

//		List<EgovMap> schedulerLogList = this.SchedulerApplicationService.selectSchedulerLog();

//		mv.addObject("schedulerLogList", schedulerLogList);

		return mv;
	}

	// 스케줄러 로그 스크롤 페이지네이션
	@RequestMapping(value = "/ajax/schedulerLogList.do", method = RequestMethod.GET)
	@ResponseBody
	public List<EgovMap> selectSchedulerlogBoard(HttpServletRequest req, HttpServletResponse res, ModelAndView mv)
			throws Exception {
		
		
		mv.setViewName("jsonView");

		int pagenum = Integer.parseInt(req.getParameter("pageNum"));
		

		List<EgovMap> Board = this.SchedulerApplicationService.selectSchedulerLog(pagenum);
		

		return Board;
		

	}

	// 클릭시 캠페인 정보 표시
	@RequestMapping(value = "/ajax/selectCampaignBoard.do", method = RequestMethod.GET)
	public ModelAndView selectCampaignBoard(HttpServletRequest req, HttpServletResponse res, ModelAndView mv)
			throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);

		EgovMap Board = this.adminService.selectCampaignBoard(params);

		mv.addObject("list", Board);

		return mv;
	}

	// 캠페인 승인 c_st = 2
	@RequestMapping(value = "/ajax/approveCampaign.do", method = RequestMethod.GET)
	public ModelAndView approveCampaign(HttpServletRequest req, HttpServletResponse res, ModelAndView mv)
			throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);

		System.out.println(params);

		this.adminService.UpdateApproveCampaign(params);

		return mv;
	}

	// 캠페인 거절 c_st = 0
	@RequestMapping(value = "/ajax/refuseCampaign", method = RequestMethod.GET)
	public ModelAndView refuseCampaign(HttpServletRequest req, HttpServletResponse res, ModelAndView mv)
			throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);
		this.adminService.UpdateRefuseCampaign(params);

		return mv;
	}

	// 캠페인 삭제
	@RequestMapping(value = "/ajax/deleteCampaign.do", method = RequestMethod.GET)
	public ModelAndView deleteCampaign(HttpServletRequest req, HttpServletResponse res, ModelAndView mv)
			throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);
		this.adminService.deleteCampaign(params);

		return mv;
	}

	// 미인증 유저 정보 표출
	@RequestMapping(value = "/ajax/selectApplyMemberBoard.do", method = RequestMethod.GET)
	public ModelAndView selectApplyMemberBoard(HttpServletRequest req, HttpServletResponse res, ModelAndView mv)
			throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);

		EgovMap Board = this.adminService.selectApplyMemberBoard(params);

		mv.addObject("list", Board);

		return mv;
	}

	// 인증된 유저 정보 표출
	@RequestMapping(value = "/ajax/selectMemberBoard.do", method = RequestMethod.GET)
	public ModelAndView selectMemberBoard(HttpServletRequest req, HttpServletResponse res, ModelAndView mv)
			throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);

		EgovMap Board = this.adminService.selectMemberBoard(params);

		mv.addObject("list", Board);

		return mv;
	}

	// 일반 사용자 -> 인증된 사용자
	@RequestMapping(value = "/ajax/approveMember.do", method = RequestMethod.GET)
	public ModelAndView approveMember(HttpServletRequest req, HttpServletResponse res, ModelAndView mv)
			throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);

		this.adminService.updateApproveMember(params);

		return mv;
	}

	// 인증된 사용자 -> 일반 사용자
	@RequestMapping(value = "/ajax/approveNormalMember.do", method = RequestMethod.GET)
	public ModelAndView approveNormalMember(HttpServletRequest req, HttpServletResponse res, ModelAndView mv)
			throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);

		this.adminService.updateApproveNormalMember(params);

		return mv;
	}

	@RequestMapping(value = "/memberMgmt.do")
	public ModelAndView memberMgmt(ModelAndView mv, HttpServletRequest req, HttpServletResponse res) throws Exception {
		mv.setViewName("admin/memberMgmt");

		EgovMap params = paramMap(req);
		List<EgovMap> memberList = this.adminService.selectMemberInfoList(params);
		mv.addObject("memberList", memberList);

		return mv;
	}

	@RequestMapping(value = "/logMgmt.do")
	public ModelAndView logMgmt(ModelAndView mv, HttpServletRequest req, HttpServletResponse res) throws Exception {
		mv.setViewName("admin/logMgmt");

		EgovMap params = paramMap(req);
		List<EgovMap> accessLogList = this.adminService.selectAccessLogList(params);
		mv.addObject("accessLogList", accessLogList);

		return mv;
	}

	@RequestMapping(value = "/ajax/accessLogList.do", method = RequestMethod.GET)
	public ModelAndView selectBoardList(HttpServletRequest req, HttpServletResponse res, ModelAndView mv)
			throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);

		// 총 게시글 갯수
		int totalRecord = this.adminService.selectCountAccessLogList(params);

		// 페이징
		int pageNum = Integer.parseInt((String) params.get("pageNum"));
		PagingVO pagingVO = new PagingVO(totalRecord, pageNum, 15);

		params.put("start", pagingVO.getStart());
		params.put("end", pagingVO.getEnd());

		List<EgovMap> accessLogList = this.adminService.selectAccessLogList(params);

		mv.addObject("accessLogList", accessLogList);
		mv.addObject("totalPage", pagingVO.getLastPage());
		mv.addObject("totalRecord", totalRecord);

		return mv;
	}

	// 파일다운
	@RequestMapping(value = "/download/ajax/campaignDownloadFile.do", method = RequestMethod.GET)
	public void fileCampaignDown(HttpServletRequest req, HttpServletResponse res, ModelAndView mv) throws Exception {
		String filePath = "C:\\upload\\campaign\\file\\";

		// 다운로드 받을 파일명
		String fileName = req.getParameter("fileSvrName");

		res.setContentType("application/octet-stream");
		res.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
		File file = new File(filePath, fileName);
		FileInputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);
			//IOUtils.copy(inputStream, res.getOutputStream());
			res.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	// 파일다운
	@RequestMapping(value = "/download/ajax/campaignDownload.do", method = RequestMethod.GET)
	public void fileDown(HttpServletRequest req, HttpServletResponse res, ModelAndView mv) throws Exception {
		String filePath = "C:\\upload\\campaign\\mentor\\";

		// 다운로드 받을 파일명
		String fileName = req.getParameter("fileSvrName");

		res.setContentType("application/octet-stream");
		res.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
		File file = new File(filePath, fileName);
		FileInputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);
			//IOUtils.copy(inputStream, res.getOutputStream());
			res.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	// 알람
	@RequestMapping(value = "/alarm.do")
	public ModelAndView alarm(ModelAndView mv, HttpServletRequest req, HttpServletResponse res) throws Exception {
		mv.setViewName("admin/alarm");
		EgovMap params = this.paramMap(req);

		List<EgovMap> recentList = this.adminService.selectRecentMessage(params);

		Integer alarmNum = this.adminService.selectAlarmCount(params);
		int max;
		int min;

		System.out.println("alarma    " + alarmNum);

		if (alarmNum != null) {
			max = alarmNum;
			if (max - 30 <= 0) {
				min = 1;
				max = min + 30;
			} else {
				min = max - 30;
			}
		} else {
			min = 0;
			max = 30; // max 따로 구해야함
		}

		params.put("min", min);
		params.put("max", max);

		List<EgovMap> alarmList = this.adminService.selectAlarm(params);

		mv.addObject("recentList", recentList);
		mv.addObject("alarm", alarmList);
		mv.addObject("alarmNum", alarmNum);
		mv.addObject("email", params.get("email"));
		mv.addObject("sender", params.get("sender"));
		System.out.println(alarmList);

		return mv;
	}

	@RequestMapping(value = "/ajax/alarmScroll.do", method = RequestMethod.GET)
	public ModelAndView alarmScroll(HttpServletRequest req, HttpServletResponse res, ModelAndView mv) throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);

		List<EgovMap> alarmList = this.adminService.selectAlarm(params);

		mv.addObject("alarm", alarmList);

		return mv;
	}

	@RequestMapping(value = "/ajax/alarmSubmit.do", method = RequestMethod.GET)
	public ModelAndView alarmSubmit(HttpServletRequest req, HttpServletResponse res, ModelAndView mv) throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);

		this.adminService.insertAlarmSubmit(params);
		this.adminService.updateAlarmSubmit(params);

		return mv;
	}

	private EgovMap paramMap(HttpServletRequest request) {
		EgovMap paramMap = new EgovMap();
		Set keySet = request.getParameterMap().keySet();
		Iterator<?> iter = keySet.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			paramMap.put(key, request.getParameter(key));
			logger.info("request.getParameter(\"" + key + "\", \"" + request.getParameter(key) + "\")");
		}
		return paramMap;
	}

	@RequestMapping(value = "/setSidebar.do")
	public ModelAndView setSidebar(ModelAndView mv, HttpServletRequest req, HttpServletResponse res) throws Exception {
		mv.setViewName("admin/setSidebar");

		List<EgovMap> mainmenu = mainService.selectMainmenu();

		List<EgovMap> submenu = mainService.selectSubmenu();

		mv.addObject("mainmenu", mainmenu);

		mv.addObject("submenu", submenu);

		return mv;
	}

	@RequestMapping(value = "/ajax/editSidebar.do", method = RequestMethod.GET)
	public void editSidebar(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "editmenuname", required = false) String editmenuname,
			@RequestParam(value = "mainmenu", required = false) String mainmenu,
			@RequestParam(value = "menuname", required = false) String menuname,
			@RequestParam(value = "icon", required = false) String icon,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "fn", required = false) String fn) throws Exception {

		MenuInfo menuInfo = new MenuInfo();

		int fnnum = Integer.parseInt(fn);

		menuInfo.setMainmenu(editmenuname);
		menuInfo.setSubmainmenu(mainmenu);
		menuInfo.setSubmenu(menuname);
		menuInfo.setNewname(menuname);
		menuInfo.setIcon(icon);
		menuInfo.setId(Integer.parseInt(id));

		// 메인메뉴 수정
		if (fnnum == 1) {

			if (editmenuname.equals(menuname)) {
				// 기존 메뉴 이름이 유지된채로 업데이트 된다면

				// 이미 존재하므로 업데이트 문이 실행된다.
				this.mainService.insertnewmainmenu(menuInfo);

			} else {
				// 기존 메뉴 이름이 수정되었다면 , 존재하지 않는 메뉴를 만들어야 하므로

				// 존재하지 않으면 새로 인서트
				this.mainService.insertnewmainmenu(menuInfo);
				// 서브메뉴들 새로만든 메뉴로 업데이트 한다
				this.mainService.updatemainmenuinsub(menuInfo);
				// 기존 메뉴 삭제한다.
				this.mainService.deletemainmenu(menuInfo);

			}

		} // 메인메뉴 삭제
		else if (fnnum == 2) {

			// 서브메뉴들 부터 삭제한다.
			this.mainService.deletsubmenuinmain(menuInfo);
			// 메인 메뉴 삭제
			this.mainService.deletemainmenu(menuInfo);
			// 메인메뉴 추가
		} else if (fnnum == 3) {
			// 존재하면은 업데이트, 존재하지 않으면 추가한다.
			this.mainService.insertnewmainmenu(menuInfo);
			// 서브메뉴 수정
		} else if (fnnum == 4) {

			// 업데이트문 실행
			this.mainService.updatesubmenu(menuInfo);
			// 서브메뉴 삭제
		} else if (fnnum == 5) {

			this.mainService.deletesubmenu(menuInfo);
			// 서브메뉴 추가
		} else if (fnnum == 6) {

			this.mainService.insertsubmenu(menuInfo);

		}

	}

	@RequestMapping(value = "/ajax/getLeftMainmenu.do", method = RequestMethod.GET)
	@ResponseBody
	public List<EgovMap> getmainmenu(HttpServletRequest req, HttpServletResponse res) throws Exception {

		List<EgovMap> mainmenu = mainService.selectMainmenu();

		return mainmenu;

	}

	@RequestMapping(value = "/ajax/getLeftSubmenu.do", method = RequestMethod.GET)
	@ResponseBody
	public List<EgovMap> getsubmenu(HttpServletRequest req, HttpServletResponse res) throws Exception {

		List<EgovMap> submenu = mainService.selectSubmenu();

		return submenu;

	}

	@RequestMapping(value = "/ajax/getmainmenuinfo.do", method = RequestMethod.GET)
	@ResponseBody
	public EgovMap getmainmenuinfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "mainmenu", required = false) String mainmenu) throws Exception {

		EgovMap mainmenuinfo = mainService.selectmainmenuname(mainmenu);

		return mainmenuinfo;

	}

	@RequestMapping(value = "/ajax/getsubmenuinfo.do", method = RequestMethod.GET)
	@ResponseBody
	public EgovMap getsubmenuinfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "submenu", required = false) String submenu) throws Exception {

		EgovMap submenuinfo = mainService.selectsubmenuname(submenu);

		return submenuinfo;

	}

}
