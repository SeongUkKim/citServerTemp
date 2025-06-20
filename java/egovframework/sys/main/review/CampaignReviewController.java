package egovframework.sys.main.review;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.sys.cmm.util.PagingVO;
import egovframework.sys.main.MainService;
import egovframework.sys.main.review.*;
import egovframework.sys.sec.handler.CitUserDetails;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
public class CampaignReviewController {
	private static final Logger logger = LoggerFactory.getLogger(CampaignReviewController.class);

	@Resource(name = "campaignReview")
	private CampaignReviewService campaignReviewService;
	
	@Resource(name = "mainService")
	private MainService mainService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;

	@RequestMapping(value = "/main/review.do", method = RequestMethod.GET)
	public ModelAndView CampaignMentorReview(ModelAndView mv, HttpServletRequest req, HttpSession session) throws Exception {
		EgovMap params = this.paramMap(req);
		mv.setViewName("home/review");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();
		String auth = userDetails.getAuthority();
		String userId = userDetails.getUser_id();
		
		mv.addObject("auth", auth);
		params.put("userId", userId);
		mv.addObject("userId", userId);
		params.put("uId", userId);
		int alarmCount = this.mainService.selectUserAlarmCountByHeader(params);
		mv.addObject("alarmCount", alarmCount);
		params.put("cId", req.getParameter("campaignId"));
		
		EgovMap campaignMentor = this.campaignReviewService.selectCampaignMentor(params);
		EgovMap reviewCheck = this.campaignReviewService.selectReviewCheck(params);
		
		if(reviewCheck.get("exists").toString() == "true") {
//			EgovMap userReview = this.campaignReviewService.selectUserReview(params);
			mv.addObject("reviewCheck", reviewCheck.get("exists"));
//			mv.addObject("uNm", userReview.get("uNm"));
//			mv.addObject("uId", userReview.get("uId"));
//			mv.addObject("star", userReview.get("star"));
//			mv.addObject("comment", userReview.get("comment"));
//			mv.addObject("reviewDate", userReview.get("date"));
		} else {
			mv.addObject("reviewCheck", reviewCheck.get("exists"));
		}
		mv.addObject("campaignId", campaignMentor.get("cId"));
		mv.addObject("campaignName", campaignMentor.get("cNm"));
		mv.addObject("campaignStatus", campaignMentor.get("cSt"));
		mv.addObject("mentorPkId", campaignMentor.get("id"));
		mv.addObject("mentorId", campaignMentor.get("cId"));
		mv.addObject("mentorName", campaignMentor.get("uNm"));
		
		return mv;
	}
	
	@RequestMapping(value = "/main/review/ajax/campaignReview.do", method = RequestMethod.GET)
	public ModelAndView campaignList(ModelAndView mv, HttpServletRequest req, HttpServletResponse res) throws Exception {
		mv.setViewName("jsonView");
		
		EgovMap params = this.paramMap(req);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();
		String userId = userDetails.getUser_id();
		String userName = userDetails.getUser_nm();
		params.put("uId", userId);
		params.put("uNm", userName);

		this.campaignReviewService.insertReview(params);
				
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

//	@RequestMapping(value = "/main/info/ajax/campaignInfoList.do", method = RequestMethod.GET)
//	public ModelAndView campaignList(ModelAndView mv, HttpServletRequest req, HttpServletResponse res) throws Exception {
//		mv.setViewName("jsonView");
//		
//		EgovMap params = this.paramMap(req);
//				
//		List<EgovMap> infoList = this.campaignCreditService.campaignC(params);
//		
//		mv.addObject("infoList", infoList);
//				
//		return mv;
//	}

	/*
	 * @RequestMapping(value = "/ajax/useAreaList.do", method = RequestMethod.GET)
	 * public ModelAndView selectuseAreaList(HttpServletRequest req,
	 * HttpServletResponse res, ModelAndView mv) throws Exception {
	 * mv.setViewName("jsonView");
	 * 
	 * EgovMap params = this.paramMap(req);
	 * 
	 * List<EgovMap> useAreaList = this.mainService.selectuseAreaList();
	 * mv.addObject("useAreaList", useAreaList);
	 * 
	 * return mv; }
	 */
}