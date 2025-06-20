package egovframework.sys.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import egovframework.sys.cmm.util.PagingVO;
import egovframework.sys.main.MainService;
import egovframework.sys.sec.handler.CitUserDetails;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springmodules.validation.commons.DefaultBeanValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	protected AuthenticationManager authenticationManager;

	@Resource(name = "mainService")
	private MainService mainService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;

	/* 로그인 이후 첫화면 -> 권한에 따라서 나눠진다. */
	@RequestMapping(value = "/mainATable.do")
	@ResponseBody
	public ModelAndView mainATable(ModelAndView mv, HttpServletRequest req) {

		mv.setViewName("home/mainATable");

		/* 로그인 권한 , id 표출 */

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();

		String auth = userDetails.getAuthority();
		String userId = userDetails.getUser_id();

		EgovMap alarmParams = this.paramMap(req);
		alarmParams.put("uId", userId);

		int alarmCount = this.mainService.selectUserAlarmCountByHeader(alarmParams);
		mv.addObject("alarmCount", alarmCount);

		mv.addObject("auth", auth);
		mv.addObject("userId", userId);


		/* 리스트 첫화면 */

		/* 권한 유저 리스트 갯수 ------------------------------------------------------------- */

		int campaignListCountforA = 0;
		int campaignwaitCount = 0;
		int campaigncollectCount = 0;
		int campaigningCountforA = 0;
		int campaignendCountforA = 0;

		campaignListCountforA = mainService.countCampaignlistforAUser(userId);
		campaignwaitCount = mainService.countCampaignWaitforA(userId);
		campaigncollectCount = mainService.countCampaignCollectforA(userId);
		campaigningCountforA = mainService.countCampaigningforA(userId);
		campaignendCountforA = mainService.countCampaignendforA(userId);

		/* 리스트 필터 갯수 구하고 추가하기 */

		mv.addObject("campaignListCountforA", campaignListCountforA);
		mv.addObject("campaignwaitCount", campaignwaitCount);
		mv.addObject("campaigncollectCount", campaigncollectCount);
		mv.addObject("campaigningCountforA", campaigningCountforA);
		mv.addObject("campaignendCountforA", campaignendCountforA);

		/* 페이징을 위해서 now페이지 넘기기 */

		/*------------------- 캠페인 카드 표출 완료 ------------------------------------*/

		/* 리스트 첫화면 */

		int nowPage;
		int cSt;

		if (req.getParameter("nowPage") == null && req.getParameter("cSt") == null) {
			// 로그인 이후 첫 페이지
			nowPage = 1;
			cSt = 0;

		} else {
			nowPage = Integer.parseInt(req.getParameter("nowPage"));
			cSt = Integer.parseInt(req.getParameter("cSt"));
		}

		mv.addObject("nowPagenum", nowPage);
		mv.addObject("cStnum", cSt);

		if (cSt % 2 == 1) {
			cSt = 0;
		}

		switch (cSt) {

		case 0: {
			PagingVO campaigntotalCountVO = new PagingVO(campaignListCountforA, nowPage, 10);

			List<EgovMap> campaignlistBoard = mainService.selectCampaignBoardforAUser(campaigntotalCountVO, userId);

			mv.addObject("campaigntotalCountVO", campaigntotalCountVO);
			mv.addObject("viewAll", campaignlistBoard);
		}
			break;
		case 2: {
			PagingVO campaignwaitCountVO = new PagingVO(campaignwaitCount, nowPage, 10);

			List<EgovMap> campaignlistwaitBoard = mainService.selectWaitBoardforAUser(campaignwaitCountVO, userId);

			mv.addObject("campaignwaitCountVO", campaignwaitCountVO);
			mv.addObject("viewAll", campaignlistwaitBoard);

		}

			break;
		case 4: {
			PagingVO campaigncollectCountVO = new PagingVO(campaigncollectCount, nowPage, 10);

			List<EgovMap> campaignlistcollectBoard = mainService.selectCollectBoardforAUser(campaigncollectCountVO,
					userId);

			mv.addObject("campaigncollectCountVO", campaigncollectCountVO);
			mv.addObject("viewAll", campaignlistcollectBoard);

		}
			break;
		case 6: {
			PagingVO campaigningCountforAVO = new PagingVO(campaigningCountforA, nowPage, 10);
			List<EgovMap> campaignlistingBoard = mainService.selectIngBoardforAUser(campaigningCountforAVO, userId);

			mv.addObject("campaigningCountforAVO", campaigningCountforAVO);
			mv.addObject("viewAll", campaignlistingBoard);

		}
			break;
		case 8: {
			PagingVO campaignendCountforAVO = new PagingVO(campaignendCountforA, nowPage, 10);
			List<EgovMap> campaignlistendBoard = mainService.selectEndBoardforAUser(campaignendCountforAVO, userId);

			mv.addObject("campaignendCountforAVO", campaignendCountforAVO);
			mv.addObject("viewAll", campaignlistendBoard);

		}

		}

		return mv;

	}

	@RequestMapping(value = "/mainAGallery.do")
	@ResponseBody
	public ModelAndView mainAGallery(ModelAndView mv, HttpServletRequest req) {

		mv.setViewName("home/mainAGallery");

		/* 로그인 권한 , id 표출 */

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();

		String auth = userDetails.getAuthority();
		String userId = userDetails.getUser_id();

		EgovMap alarmParams = this.paramMap(req);
		alarmParams.put("uId", userId);

		int alarmCount = this.mainService.selectUserAlarmCountByHeader(alarmParams);
		mv.addObject("alarmCount", alarmCount);

		mv.addObject("auth", auth);
		mv.addObject("userId", userId);

	

		/* 리스트 첫화면 */

		/* 권한 유저 리스트 갯수 ------------------------------------------------------------- */

		int campaignListCountforA = 0;
		int campaignwaitCount = 0;
		int campaigncollectCount = 0;
		int campaigningCountforA = 0;
		int campaignendCountforA = 0;

		campaignListCountforA = mainService.countCampaignlistforAUser(userId);
		campaignwaitCount = mainService.countCampaignWaitforA(userId);
		campaigncollectCount = mainService.countCampaignCollectforA(userId);
		campaigningCountforA = mainService.countCampaigningforA(userId);
		campaignendCountforA = mainService.countCampaignendforA(userId);

		/* 리스트 필터 갯수 구하고 추가하기 */

		mv.addObject("campaignListCountforA", campaignListCountforA);
		mv.addObject("campaignwaitCount", campaignwaitCount);
		mv.addObject("campaigncollectCount", campaigncollectCount);
		mv.addObject("campaigningCountforA", campaigningCountforA);
		mv.addObject("campaignendCountforA", campaignendCountforA);

		/* 페이징을 위해서 now페이지 넘기기 */

		/*------------------- 캠페인 카드 표출 완료 ------------------------------------*/

		int waitnowPage;
		int collectnowPage;
		int ingnowPage;
		int endnowPage;

		int gst;

		if (req.getParameter("waitnowPage") == null && req.getParameter("collectnowPage") == null
				&& req.getParameter("ingnowPage") == null && req.getParameter("endnowPage") == null) {
			// 로그인 이후 첫 페이지
			waitnowPage = 1;
			collectnowPage = 1;
			ingnowPage = 1;
			endnowPage = 1;

		} else {
			waitnowPage = Integer.parseInt(req.getParameter("waitnowPage"));
			collectnowPage = Integer.parseInt(req.getParameter("collectnowPage"));
			ingnowPage = Integer.parseInt(req.getParameter("ingnowPage"));
			endnowPage = Integer.parseInt(req.getParameter("endnowPage"));

		}

		if (req.getParameter("galleryStauts") == null) {
			gst = 0;
		} else {
			gst = Integer.parseInt(req.getParameter("galleryStauts"));

		}
		mv.addObject("waitnowPage", waitnowPage);
		mv.addObject("collectnowPage", collectnowPage);
		mv.addObject("ingnowPage", ingnowPage);
		mv.addObject("endnowPage", endnowPage);

		mv.addObject("gStnum", gst);

		PagingVO gallerynwaitCountVO = new PagingVO(campaignwaitCount, waitnowPage, 3);
		PagingVO gallerycollectCountVO = new PagingVO(campaigncollectCount, collectnowPage, 3);
		PagingVO galleryingCountforAVO = new PagingVO(campaigningCountforA, ingnowPage, 3);
		PagingVO galleryendCountforAVO = new PagingVO(campaignendCountforA, endnowPage, 3);

		mv.addObject("gallerynwaitpaging", gallerynwaitCountVO);
		mv.addObject("gallerycollectpaging", gallerycollectCountVO);
		mv.addObject("galleryingCountpaging", galleryingCountforAVO);
		mv.addObject("galleryendCountpaging", galleryendCountforAVO);

		List<EgovMap> gallerynwaitBoard = mainService.selectWaitBoardforAUser(gallerynwaitCountVO, userId);
		List<EgovMap> gallerycollectBoard = mainService.selectCollectBoardforAUser(gallerycollectCountVO, userId);
		List<EgovMap> galleryingBoard = mainService.selectIngBoardforAUser(galleryingCountforAVO, userId);
		List<EgovMap> galleryendBoard = mainService.selectEndBoardforAUser(galleryendCountforAVO, userId);

		mv.addObject("gallerynwaitBoard", gallerynwaitBoard);
		mv.addObject("gallerycollectBoard", gallerycollectBoard);
		mv.addObject("galleryingBoard", galleryingBoard);
		mv.addObject("galleryendBoard", galleryendBoard);

		return mv;

	}

	@RequestMapping(value = "/mainBTable.do")
	@ResponseBody
	public ModelAndView mainBTable(ModelAndView mv, HttpServletRequest req) {

		mv.setViewName("home/mainBTable");

		/* 로그인 권한 , id 표출 */

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();

	
		String auth = userDetails.getAuthority();
		String userId = userDetails.getUser_id();

		EgovMap alarmParams = this.paramMap(req);
		alarmParams.put("uId", userId);

		int alarmCount = this.mainService.selectUserAlarmCountByHeader(alarmParams);
		mv.addObject("alarmCount", alarmCount);

		mv.addObject("userId", userId);
		
		mv.addObject("auth", auth);

		/* 리스트 첫화면 */
		

		/*--------- 리스트 첫화면 --------------*/

		/* 일반 유저 리스트 갯수 */
		int campaignListCountforB = 0;
		int campaignReservingCount = 0;
		int campaignCheckCount = 0;
		int campaigningCountforB = 0;
		int campaignendCountforB = 0;

		campaignListCountforB = mainService.countCampaignlistforBUser(userId);
		campaignReservingCount = mainService.countCampaignReservingforB(userId);
		campaignCheckCount = mainService.countCampaignChecktforB(userId);
		campaigningCountforB = mainService.countCampaigningforB(userId);
		campaignendCountforB = mainService.countCampaignendforB(userId);

		mv.addObject("campaignListCountforB", campaignListCountforB);
		mv.addObject("campaignReservingCount", campaignReservingCount);
		mv.addObject("campaignCheckCount", campaignCheckCount);
		mv.addObject("campaigningCountforB", campaigningCountforB);
		mv.addObject("campaignendCountforB", campaignendCountforB);

		/*------------------- 캠페인 카드 표출 완료 ------------------------------------*/

		/* 리스트 첫화면 */

		int nowPage;
		int cSt;

		if (req.getParameter("nowPage") == null && req.getParameter("cSt") == null) {
			// 로그인 이후 첫 페이지
			nowPage = 1;
			cSt = 0;

		} else {
			nowPage = Integer.parseInt(req.getParameter("nowPage"));
			cSt = Integer.parseInt(req.getParameter("cSt"));
		}

		mv.addObject("nowPagenum", nowPage);
		mv.addObject("cStnum", cSt);

		if (cSt % 2 == 1) {
			cSt = 0;
		}

		switch (cSt) {

		case 0: {
			PagingVO campaigntotalCountVO2 = new PagingVO(campaignListCountforB, nowPage, 10);

			List<EgovMap> campaignlistBoard2 = mainService.selectCampaignBoardforBUser(campaigntotalCountVO2, userId);

			mv.addObject("campaigntotalCountVO", campaigntotalCountVO2);
			mv.addObject("viewAll", campaignlistBoard2);
		}
			break;
		case 2: {
			PagingVO campaignReservingCountVO = new PagingVO(campaignReservingCount, nowPage, 10);

			List<EgovMap> campaignlistreserveBoard = mainService.selectReservingBoardforBUser(campaignReservingCountVO,
					userId);

			mv.addObject("campaignReservingCountVO", campaignReservingCountVO);
			mv.addObject("viewAll", campaignlistreserveBoard);

		}

			break;
		case 4: {
			PagingVO campaignCheckCountVO = new PagingVO(campaignCheckCount, nowPage, 10);

			List<EgovMap> campaignlistcollectBoard = mainService.selectCheckBoardforBUser(campaignCheckCountVO, userId);

			mv.addObject("campaignCheckCountVO", campaignCheckCountVO);
			mv.addObject("viewAll", campaignlistcollectBoard);

		}
			break;
		case 6: {
			PagingVO campaigningCountforBVO = new PagingVO(campaigningCountforB, nowPage, 10);
			List<EgovMap> campaignlistingBoard = mainService.selectIngBoardforBUser(campaigningCountforBVO, userId);

			mv.addObject("campaigningCountforBVO", campaigningCountforBVO);
			mv.addObject("viewAll", campaignlistingBoard);

		}
			break;
		case 8: {
			PagingVO campaignendCountforBVO = new PagingVO(campaignendCountforB, nowPage, 10);
			List<EgovMap> campaignlistendBoard = mainService.selectEndBoardforBUser(campaignendCountforBVO, userId);

			mv.addObject("campaignendCountforBVO", campaignendCountforBVO);
			mv.addObject("viewAll", campaignlistendBoard);

		}

			break;
		}

		return mv;

	}

	@RequestMapping(value = "/mainBGallery.do")
	@ResponseBody
	public ModelAndView mainBGallery(ModelAndView mv, HttpServletRequest req) {

		mv.setViewName("home/mainBGallery");

		/* 로그인 권한 , id 표출 */

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();

		String auth = userDetails.getAuthority();
		String userId = userDetails.getUser_id();

		EgovMap alarmParams = this.paramMap(req);
		alarmParams.put("uId", userId);

		int alarmCount = this.mainService.selectUserAlarmCountByHeader(alarmParams);
		mv.addObject("alarmCount", alarmCount);

		mv.addObject("auth", auth);
		mv.addObject("userId", userId);

	


		/* 리스트 첫화면 */

		/* 권한 유저 리스트 갯수 ------------------------------------------------------------- */

		int campaignListCountforB = 0;
		int campaignReservingCount = 0;
		int campaignCheckCount = 0;
		int campaigningCountforB = 0;
		int campaignendCountforB = 0;

		campaignListCountforB = mainService.countCampaignlistforBUser(userId);
		campaignReservingCount = mainService.countCampaignReservingforB(userId);
		campaignCheckCount = mainService.countCampaignChecktforB(userId);
		campaigningCountforB = mainService.countCampaigningforB(userId);
		campaignendCountforB = mainService.countCampaignendforB(userId);

		mv.addObject("campaignListCountforB", campaignListCountforB);
		mv.addObject("campaignReservingCount", campaignReservingCount);
		mv.addObject("campaignCheckCount", campaignCheckCount);
		mv.addObject("campaigningCountforB", campaigningCountforB);
		mv.addObject("campaignendCountforB", campaignendCountforB);

		/* 페이징을 위해서 now페이지 넘기기 */

		/*------------------- 캠페인 카드 표출 완료 ------------------------------------*/

		int reservenowPage;
		int checknowPage;
		int ingBnowPage;
		int endBnowPage;

		int gst;

		if (req.getParameter("reservenowPage") == null && req.getParameter("checknowPage") == null
				&& req.getParameter("ingBnowPage") == null && req.getParameter("endBnowPage") == null) {
			// 로그인 이후 첫 페이지
			reservenowPage = 1;
			checknowPage = 1;
			ingBnowPage = 1;
			endBnowPage = 1;

		} else {
			reservenowPage = Integer.parseInt(req.getParameter("reservenowPage"));
			checknowPage = Integer.parseInt(req.getParameter("checknowPage"));
			ingBnowPage = Integer.parseInt(req.getParameter("ingBnowPage"));
			endBnowPage = Integer.parseInt(req.getParameter("endBnowPage"));
		}

		if (req.getParameter("galleryStauts") == null) {
			gst = 0;
		} else {
			gst = Integer.parseInt(req.getParameter("galleryStauts"));

		}
		mv.addObject("reservenowPage", reservenowPage);
		mv.addObject("checknowPage", checknowPage);
		mv.addObject("ingBnowPage", ingBnowPage);
		mv.addObject("endBnowPage", endBnowPage);

		mv.addObject("gStnum", gst);

		PagingVO galleryreservingtCountVO = new PagingVO(campaignReservingCount, reservenowPage, 3);
		PagingVO gallerycheckCountVO = new PagingVO(campaignCheckCount, checknowPage, 3);
		PagingVO galleryingCountforBVO = new PagingVO(campaigningCountforB, ingBnowPage, 3);
		PagingVO galleryendCountforBVO = new PagingVO(campaignendCountforB, endBnowPage, 3);

		mv.addObject("galleryreservingtCountVO", galleryreservingtCountVO);
		mv.addObject("gallerycheckCountVO", gallerycheckCountVO);
		mv.addObject("galleryingCountforBVO", galleryingCountforBVO);
		mv.addObject("galleryendCountforBVO", galleryendCountforBVO);

		List<EgovMap> galleryreservingBoard = mainService.selectReservingBoardforBUser(galleryreservingtCountVO,
				userId);
		List<EgovMap> gallerycheckBoard = mainService.selectCheckBoardforBUser(gallerycheckCountVO, userId);
		List<EgovMap> galleryingBoardB = mainService.selectIngBoardforBUser(galleryingCountforBVO, userId);
		List<EgovMap> galleryendBoardB = mainService.selectEndBoardforBUser(galleryendCountforBVO, userId);

		mv.addObject("galleryreservingBoard", galleryreservingBoard);
		mv.addObject("gallerycheckBoard", gallerycheckBoard);
		mv.addObject("galleryingBoardB", galleryingBoardB);
		mv.addObject("galleryendBoardB", galleryendBoardB);

		return mv;

	}
	
	
	/* left menu ajax */

	@RequestMapping(value = "/main/main/ajax/getLeftMainmenu.do", method = RequestMethod.POST)
	@ResponseBody
	public List<EgovMap> getLeftMainmenu(HttpServletRequest req) throws Exception {

		List<EgovMap> mainmenu = mainService.selectMainmenu();

		return mainmenu;

	}

	@RequestMapping(value = "/main/main/ajax/getLeftSubmenu.do", method = RequestMethod.POST)
	@ResponseBody
	public List<EgovMap> getLeftSubmenu(HttpServletRequest req) throws Exception {

		List<EgovMap> submenu = mainService.selectSubmenu();

		return submenu;

	}

	@RequestMapping(value = "/main/inquire.do", method = RequestMethod.POST)
	public String inquireForm(RedirectAttributes redirect, HttpServletRequest req, HttpServletResponse res,
			HttpSession session) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();

		String auth = userDetails.getAuthority();
		String userId = userDetails.getUser_id();

		EgovMap params = this.paramMap(req);
		params.put("uId", userId);

		int memberInfoId = this.mainService.selectIdByUid(params);

		params.put("id", memberInfoId);

		this.mainService.insertInquireInHeader(params);

		return "redirect:/main.do";
	}

	@RequestMapping(value = "/main/ajax/userAlarmList.do", method = RequestMethod.GET)
	public ModelAndView userAlarmList(ModelAndView mv, HttpServletRequest req) throws Exception {
		mv.setViewName("jsonView");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();

		EgovMap params = this.paramMap(req);
		params.put("uId", userDetails.getUser_id());

		int memberInfoId = this.mainService.selectIdByUid(params);
		params.put("id", memberInfoId);

		// 총 알람 개수
		int totalRecord = this.mainService.selectUserAlarmCount(params);

		// 페이징
		int pageNum = Integer.parseInt((String) params.get("pageNum"));

		PagingVO pagingVO = new PagingVO(totalRecord, pageNum, 3);

		params.put("start", pagingVO.getStart());
		params.put("end", pagingVO.getEnd());
		List<EgovMap> alarmList = this.mainService.selectUserAlarmList(params);

		mv.addObject("alarmList", alarmList);
		mv.addObject("totalPage", pagingVO.getLastPage());
		mv.addObject("totalRecord", totalRecord);

		return mv;

	}

	@RequestMapping(value = "/main/ajax/userAlarmBoard.do", method = RequestMethod.GET)
	public ModelAndView userAlarmBoard(ModelAndView mv, HttpServletRequest req) throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);

		EgovMap alarmBoard = this.mainService.selectUserAlarmBoard(params);
		this.mainService.updateReadByUser(params); // user가 읽을때 상태변환

		mv.addObject("alarmBoard", alarmBoard);

		return mv;

	}

	@RequestMapping(value = "/main/ajax/deleteAlarmUser.do", method = RequestMethod.GET)
	public ModelAndView deleteAlarmUser(ModelAndView mv, HttpServletRequest req) throws Exception {
		mv.setViewName("jsonView");

		EgovMap params = this.paramMap(req);

		// 유저쪽에서 삭제긴한데 관리자에선 삭제아님 + 상태 update 4로 바꿈
		// EgovMap alarmBoard = this.mainService.selectUserAlarmBoard(params);
		this.mainService.updateAlarmStatus4(params);

		return mv;

	}

	@RequestMapping(value = "/main/ajax/alarmLeftIcon.do", method = RequestMethod.GET)
	public ModelAndView alarmLeftIcon(ModelAndView mv, HttpServletRequest req) throws Exception {
		mv.setViewName("jsonView");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();

		EgovMap params = this.paramMap(req);
		params.put("uId", userDetails.getUser_id());

		if (params.get("num") != "0") {
			EgovMap alarmBoard = this.mainService.selectUserAlarmBoardByArrow(params);
			params.put("inqureId", alarmBoard.get("inqureId"));
			this.mainService.updateReadByUser(params); // user가 읽을때 상태변환
			mv.addObject("alarmBoard", alarmBoard);
		}

		return mv;

	}

	@RequestMapping(value = "/main/ajax/alarmRightIcon.do", method = RequestMethod.GET)
	public ModelAndView alarmRightIcon(ModelAndView mv, HttpServletRequest req) throws Exception {
		mv.setViewName("jsonView");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();

		EgovMap params = this.paramMap(req);
		params.put("uId", userDetails.getUser_id());

		int totCount = this.mainService.selectUserAlarmCount(params);

		if (Integer.parseInt((String) params.get("num")) <= totCount) {
			EgovMap alarmBoard = this.mainService.selectUserAlarmBoardByArrow(params);
			params.put("inqureId", alarmBoard.get("inqureId"));
			this.mainService.updateReadByUser(params); // user가 읽을때 상태변환
			mv.addObject("alarmBoard", alarmBoard);
		}

		mv.addObject("totCount", totCount);

		return mv;

	}

	private EgovMap paramMap(HttpServletRequest request) {
		EgovMap paramMap = new EgovMap();
		Set keySet = request.getParameterMap().keySet();
		keySet.stream().forEach(key -> paramMap.put(key, request.getParameter(key.toString())));
		Iterator<?> iter = keySet.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			paramMap.put(key, request.getParameter(key));
			logger.info("request.getParameter(\"" + key + "\", \"" + request.getParameter(key) + "\")");
		}
		return paramMap;
	}

}
