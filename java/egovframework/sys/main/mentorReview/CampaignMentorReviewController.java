package egovframework.sys.main.mentorReview;

import java.util.HashMap;
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
import egovframework.sys.main.mentorReview.*;
import egovframework.sys.sec.handler.CitUserDetails;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
public class CampaignMentorReviewController {
	private static final Logger logger = LoggerFactory.getLogger(CampaignMentorReviewController.class);

	@Resource(name = "campaignMentorReview")
	private CampaignMentorReviewService campaignMentorReviewService;

	@Resource(name = "mainService")
	private MainService mainService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;

	@RequestMapping(value = "/main/mentorReview.do", method = RequestMethod.GET)
	public ModelAndView CampaignMentorReview(ModelAndView mv, HttpServletRequest req, HttpSession session) throws Exception {
		EgovMap params = this.paramMap(req);
		mv.setViewName("home/mentorReview");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();
		String auth = userDetails.getAuthority();
		String userId = userDetails.getUser_id();
		
		params.put("uId", userId);
		
		int alarmCount = this.mainService.selectUserAlarmCountByHeader(params);
		mv.addObject("alarmCount", alarmCount);
		mv.addObject("auth", auth);
		mv.addObject("userId", userId);
		
		params.put("mentorPkId", req.getParameter("mentorPkId"));
		mv.addObject("mentorPkId", req.getParameter("mentorPkId"));
/*		System.out.println("########");
		System.out.println(params.get("mentorPkId"));
		
		List<EgovMap> reviewList = this.campaignMentorReviewService.selectReviewList(params);
		System.out.println(reviewList);
		System.out.println(reviewList.size());

		System.out.println(reviewList.get(0));
		
		
		mv.addObject("reviewCount", reviewList.size());
		mv.addObject("reviewList", reviewList);*/
	
		return mv;
	}
	
	@RequestMapping(value = "/main/viewReview/ajax/viewReview.do", method = RequestMethod.GET)
	public ModelAndView viewReview(ModelAndView mv, HttpServletRequest req, HttpServletResponse res) throws Exception {
		mv.setViewName("jsonView");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();
		EgovMap params = this.paramMap(req);
		
		String auth = userDetails.getAuthority();
	    String userId = null;
	      
	    userId = userDetails.getUser_id();
		params.put("uId", userId);
		
		int alarmCount = this.mainService.selectUserAlarmCountByHeader(params);
		mv.addObject("alarmCount", alarmCount);
		mv.addObject("auth", auth);
		mv.addObject("userId", userId);
		
		params.put("mentorPkId", req.getParameter("mentorPkId"));
		mv.addObject("mentorPkId", req.getParameter("mentorPkId"));
		
		System.out.println("########22");
		System.out.println(params.get("mentorPkId"));
		
		List<EgovMap> reviewList = this.campaignMentorReviewService.selectReviewList(params);
		
		mv.addObject("reviewCount", reviewList.size());
		mv.addObject("reviewList", reviewList);
		
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