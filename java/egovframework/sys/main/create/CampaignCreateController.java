package egovframework.sys.main.create;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.SocketUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.sys.cmm.util.FileUtils;
import egovframework.sys.main.MainService;
import egovframework.sys.sec.handler.CitUserDetails;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
public class CampaignCreateController {
    private static final Logger logger = LoggerFactory.getLogger(CampaignCreateController.class);
    
    
    @Resource(name = "campaignCreate")
    private CampaignCreateService campaignCreateService;

	@Resource(name = "mainService")
	private MainService mainService;
    
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Resource(name = "beanValidator")
    protected DefaultBeanValidator beanValidator;
    
    @Resource(name="fileUtils")
    private FileUtils fileUtils;
    
    
    @RequestMapping(value = "/main/create.do")
    @ResponseBody
    public ModelAndView CampaignInfo(ModelAndView mv, HttpServletRequest req) throws Exception {
        mv.setViewName("home/campaignCreate");
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();
        
        String auth = userDetails.getAuthority();

        String CSRF = UUID.randomUUID().toString();
        
        EgovMap params = this.paramMap(req);
        params.put("uId", userDetails.getUser_id());
        
        //member category를 담을 변수
        List<EgovMap> memberCategory = this.campaignCreateService.selectMentorCategory(params);
       
        //알람개수
        int alarmCount = this.mainService.selectUserAlarmCountByHeader(params);
		mv.addObject("alarmCount", alarmCount);
        
        mv.addObject("memberCategory", memberCategory);
        mv.addObject("auth", auth);
        mv.addObject("userId", userDetails.getUser_id());
        mv.addObject("CSRF_TOKEN", CSRF);
        
        return mv;
    }
    
    @RequestMapping(value = "/main/create/ajax/cpnSubmit.do", method =  RequestMethod.GET)
    public ModelAndView campaignSubmit(ModelAndView mv, HttpServletRequest req,  HttpServletResponse res) throws Exception {
        mv.setViewName("jsonView");
    
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();
        
        
        EgovMap params = this.paramMap(req);
        
        params.put("uId",userDetails.getUser_id());
        
        String calender = (String)params.get("calender");
        String[] c = calender.substring(1, calender.length()-1).split("\\{");
        
        
        List<Map<String, String>> calenderParams = new ArrayList<Map<String, String>>();
        
        for(int i =1; i<c.length; i++) {
            if(c[i].length() != 0) {
    
                ObjectMapper om = new ObjectMapper();

                if(i == c.length-1){
                    //마지막 인덱스는 } 필요
                    Map<String, String> calender1 = om.readValue("{"+ c[i].substring(0,c[i].length()-1).replaceAll("&quot;", "\"")+"}", Map.class);
                    calenderParams.add(calender1);
                }
                else {
                    Map<String, String> calender1 = om.readValue("{"+ c[i].substring(0,c[i].length()-1).replaceAll("&quot;", "\""), Map.class);
                    calenderParams.add(calender1);
                }
            }
        }
        this.campaignCreateService.insertCampaign(params);
        this.campaignCreateService.insertCampaignCalender(calenderParams);
        
        return mv;
    }
    

    @RequestMapping(value = "/main/create/fileUpload.do", method = RequestMethod.POST)
    public String campaignFileUpload(RedirectAttributes redirect, MultipartHttpServletRequest req, HttpServletResponse res, HttpSession session) throws Exception {

        EgovMap params = this.paramMap(req);
        
        List<Map<String,Object>> listFileInfo = fileUtils.parseInsertFileInfo(req, "C:\\upload\\campaign\\file\\");
        this.campaignCreateService.insertFile(listFileInfo);
        
        
        return "redirect:/mainATable.do";
    }
    
    @RequestMapping(value = "/main/create/imgUpload.do", method = RequestMethod.POST)
    public ModelAndView campaignImgUpload(ModelAndView mv, MultipartHttpServletRequest req, HttpServletResponse res, HttpSession session) throws Exception{
        mv.setViewName("jsonView");
        List<Map<String,Object>> listFileInfo = fileUtils.parseInsertFileInfo(req, "C:\\upload\\campaign\\img\\");
        this.campaignCreateService.insertCampaignImg(listFileInfo);
        
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
