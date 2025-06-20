package egovframework.sys.main.search;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.sys.cmm.util.PagingVO;
import egovframework.sys.main.MainService;
import egovframework.sys.main.search.CampaignSearchService;
import egovframework.sys.sec.handler.CitUserDetails;


@Controller
public class CampaignSearchController {
	private static final Logger logger = LoggerFactory.getLogger(CampaignSearchController.class);

	@Resource(name = "campaignSearch")
	private CampaignSearchService campaignSearchService;
	
	@Resource(name = "mainService")
	private MainService mainService;
    
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	// 2차 필터링
	// 체크 여부에 따라 나눔
	@RequestMapping(value = "/main/search.do", method = RequestMethod.GET)
	public ModelAndView campaignFilterSearch(ModelAndView mv,  HttpServletRequest req,  HttpSession session) {
		EgovMap params = this.paramMap(req);
		mv.setViewName("home/campaignSearch");
		System.out.println("필터조건 : " + params);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();
		String auth = userDetails.getAuthority();
	    String userId = userDetails.getUser_id();
	    
		params.put("userId", userId);
		List<EgovMap> userLocation = this.campaignSearchService.selectUserLocation(params);
		params.put("userLonX", userLocation.get(0).get("lonX"));
		params.put("userLatY", userLocation.get(0).get("latY"));
		
		//알람 개수
		params.put("uId", userDetails.getUser_id());
		int alarmCount = this.mainService.selectUserAlarmCountByHeader(params);
		mv.addObject("alarmCount", alarmCount);
        
		
		//총 게시글 갯수
		int totalRecord=this.campaignSearchService.selectKeywordCount(params);
		//페이징
		int pageNum = 1;
		if(params.get("pageNum") != null) {
			pageNum=Integer.parseInt((String) params.get("pageNum"));
		}
		PagingVO pagingVO = new PagingVO(totalRecord,pageNum,9);
		params.put("start", pagingVO.getStart() - 1);
		
		List<EgovMap> keywordList = this.campaignSearchService.selectFilteredKeywordCampaignList(params);
		System.out.println("keywordList : " + keywordList);
		System.out.println("필터조건 : " + params);
		mv.addObject("userAddress", userLocation.get(0).get("uAddr"));
		mv.addObject("auth", auth);
		mv.addObject("userId", userId);
		mv.addObject("keywordList", keywordList);
		mv.addObject("totalPage",pagingVO.getLastPage());
		mv.addObject("keywordList", keywordList);
		mv.addObject("keywordCount", totalRecord);
		mv.addObject("params", params);
		return mv;
	}
	
	private EgovMap paramMap(HttpServletRequest request){
		EgovMap paramMap = new EgovMap();
		Set keySet = request.getParameterMap().keySet();
		Iterator<?> iter = keySet.iterator();
		while(iter.hasNext()){
			String key = (String) iter.next();
			paramMap.put(key, request.getParameter(key));
			logger.info("request.getParameter(\""+key+"\", \""+request.getParameter(key)+"\")");
		}
		return paramMap;
	}
}
