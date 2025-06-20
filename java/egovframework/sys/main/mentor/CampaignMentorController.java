package egovframework.sys.main.mentor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.annotation.Resource;
import javax.json.JsonArray;
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
import org.springframework.util.SocketUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.sys.cmm.util.PagingVO;
import egovframework.sys.main.mentor.*;
import egovframework.sys.sec.handler.CitUserDetails;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.sys.cmm.util.FileUtils;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CampaignMentorController {
	private static final Logger logger = LoggerFactory.getLogger(CampaignMentorController.class);

	@Resource(name = "campaignMentor")
	private CampaignMentorService campaignMentorService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name="fileUtils")
    private FileUtils fileUtils;

	@RequestMapping(value = "/main/mentor.do", method = RequestMethod.GET)
	public ModelAndView CampaignMentor(ModelAndView mv, HttpServletRequest req, HttpSession session) throws Exception {
		EgovMap params = this.paramMap(req);
		mv.setViewName("home/mentor");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();
	      
		String auth = userDetails.getAuthority();
		String userId = null;
		userId = userDetails.getUser_id();

		String CSRF = UUID.randomUUID().toString();
	      
		mv.addObject("auth", auth);
		mv.addObject("userId", userId);
		/*mv.addObject("CSRF_TOKEN", CSRF);*/
				
		return mv;
	}
	
	@RequestMapping(value = "/main/mentor/ajax/infoUpload.do")
    public ModelAndView MentorInfoUpload(ModelAndView mv,  HttpServletRequest req, HttpServletResponse res) throws Exception {

		mv.setViewName("jsonView");		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();
		EgovMap params = this.paramMap(req);	
		
		String auth = userDetails.getAuthority();
		String userId = null;
	      
		userId = userDetails.getUser_id();
		
		params.put("uId", userId);
		List<EgovMap> mentorId = this.campaignMentorService.selectUserId(params);
		
		params.put("id", mentorId.get(0).get("id"));
		params.put("uStatus", "1");
		
		this.campaignMentorService.insertMentorInfo(params);		
		
					
        
        return mv;
    }
	
	@RequestMapping(value = "/main/mentor/ajax/fileUpload.do",  method = RequestMethod.POST)
	public ModelAndView MentorFileUpload(ModelAndView mv, MultipartHttpServletRequest req, HttpServletResponse res, HttpSession session) throws Exception {
				
		mv.setViewName("jsonView");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();
		
		EgovMap params = this.paramMap(req);
		
		String auth = userDetails.getAuthority();
	    String userId = null;
	      
	    userId = userDetails.getUser_id();
	      
		params.put("uId", userId);
		
		List<EgovMap> mentorId = this.campaignMentorService.selectUserId(params);
				
		List<Map<String,Object>> listFileInfo = fileUtils.parseInsertFileInfo(req, "C:\\upload\\campaign\\mentor\\");
//		List<Map<String,Object>> listFileInfo = fileUtils.parseInsertFileInfo(req, "D:\\");

		listFileInfo.get(0).put("id", mentorId.get(0).get("id"));		
		listFileInfo.get(0).put("uId", userDetails.getUser_id());
		this.campaignMentorService.insertMentorFile(listFileInfo);

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
}