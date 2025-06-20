package egovframework.sys.main.modify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import egovframework.sys.main.modify.CampaignModifyController;
import egovframework.sys.sec.handler.CitUserDetails;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CampaignModifyController {
	private static final Logger logger = LoggerFactory.getLogger(CampaignModifyController.class);
	
	@Resource(name= "campaignModify")
	private CampaignModifyService campaignModifyService;
	
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Resource(name = "beanValidator")
    protected DefaultBeanValidator beanValidator;
    
    @Resource(name="fileUtils")
    private FileUtils fileUtils;
    
    private Integer campaignId;
    
    @RequestMapping(value = "/main/modify.do", method =  RequestMethod.GET)
    @ResponseBody
    public ModelAndView CampaignModify(ModelAndView mv, HttpServletRequest req) throws Exception {
        mv.setViewName("home/campaignModify");
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();
        
        String auth = userDetails.getAuthority();
        
        String CSRF = UUID.randomUUID().toString();
        
        EgovMap params = this.paramMap(req);
        params.put("uId", userDetails.getUser_id());
        
        List<EgovMap> memberCategory = this.campaignModifyService.selectMentorCategory(params);
        List<EgovMap> campaignInfo = this.campaignModifyService.selectCampaignInfo(params);
        List<EgovMap> campaignImg = this.campaignModifyService.selectCampaignImg(params);
        List<EgovMap> campaignFile = this.campaignModifyService.selectCampaignFile(params);
        this.campaignId = Integer.parseInt(req.getParameter("campaignId"));
        
        mv.addObject("memberCategory", memberCategory);
        mv.addObject("campaignInfo", campaignInfo);
        mv.addObject("campaignImg", campaignImg);
        mv.addObject("campaignFile", campaignFile);
        mv.addObject("campaignId", req.getParameter("campaignId"));
        mv.addObject("auth", auth);
        mv.addObject("userId", userDetails.getUser_id());
        mv.addObject("CSRF_TOKEN", CSRF);
        
        return mv;
    }
    
    @RequestMapping(value = "/main/modify/ajax/getCampaignInfo.do", method = RequestMethod.GET)
    public ModelAndView getCampaignCategory(ModelAndView mv, HttpServletRequest req,  HttpServletResponse res) throws Exception {
    	mv.setViewName("jsonView");
    	EgovMap params = this.paramMap(req);   
    	List<EgovMap> campaignCategory = this.campaignModifyService.selectCampaignCategory(params);
    	List<EgovMap> campaignLocation = this.campaignModifyService.selectCampaignLocation(params);
    	List<EgovMap> campaignCalender = this.campaignModifyService.selectCampaignCalender(params);
    	List<EgovMap> campaignFile = this.campaignModifyService.selectCampaignFile(params);
    	System.out.println(campaignCalender);
    	mv.addObject("campaignCategory", campaignCategory);
    	mv.addObject("campaignLocation",campaignLocation);
    	mv.addObject("campaignCalender", campaignCalender);
    	mv.addObject("campaignFile", campaignFile);
    	return mv;
    }
    
    @RequestMapping(value = "/main/modify/ajax/cpnSubmit.do", method =  RequestMethod.GET)
    public ModelAndView campaignSubmit(ModelAndView mv, HttpServletRequest req,  HttpServletResponse res) throws Exception {
        mv.setViewName("jsonView");
    
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();
        
        
        EgovMap params = this.paramMap(req);
        
        params.put("uId",userDetails.getUser_id());
        
        String calender = (String)params.get("calender");
        String[] c = calender.substring(1, calender.length()-1).split("\\{");
        
        
        List<Map<String, String>> calenderParams = new ArrayList<Map<String, String>>();
        System.out.println("dd: " + c);
        for(int i =1; i<c.length; i++) {
            if(c[i].length() != 0) {
    
                ObjectMapper om = new ObjectMapper();

                if(i == c.length-1){
                    //마지막 인덱스는 } 필요
                    Map<String, String> calender1 = om.readValue("{"+ c[i].substring(0,c[i].length()-1).replaceAll("&quot;", "\"")+"}", Map.class);
                    calender1.put("cId", Integer.toString(this.campaignId));
                    calenderParams.add(calender1);
                }
                else {
                    Map<String, String> calender1 = om.readValue("{"+ c[i].substring(0,c[i].length()-1).replaceAll("&quot;", "\""), Map.class);
                    calender1.put("cId", Integer.toString(this.campaignId));
                    calenderParams.add(calender1);
                }
            }
        }
        System.out.println("gg" + calenderParams);
        this.campaignModifyService.updateCampaignInfo(params);
        this.campaignModifyService.deleteCampaignCalender(params);
        this.campaignModifyService.insertCampaignCalender(calenderParams);
        
        return mv;
    }
    
    
    
    @RequestMapping(value = "/main/modify/ajax/imgUpload.do", method = RequestMethod.POST)
    public ModelAndView campaignImgUpload(ModelAndView mv, MultipartHttpServletRequest req, HttpServletResponse res, HttpSession session) throws Exception{
        mv.setViewName("jsonView");
        EgovMap params = new EgovMap();
        params.put("cId", this.campaignId);
        this.campaignModifyService.deleteCampaignImg(params);
        List<Map<String,Object>> listFileInfo = fileUtils.parseUpdateFileInfo(req, "C:\\upload\\campaign\\img\\", this.campaignId);
        
        System.out.println(listFileInfo);
        this.campaignModifyService.insertCampaignImg(listFileInfo);

        return mv;
    }
    
    @RequestMapping(value = "/main/modify/ajax/deleteExistFile.do", method = RequestMethod.GET)
    public ModelAndView deleteExistCampaignFile(ModelAndView mv, HttpServletRequest req,  HttpServletResponse res) throws Exception {
    	mv.setViewName("jsonView");
    	EgovMap params = this.paramMap(req);
    	params.put("cId", this.campaignId);
    	for(int i=0; i<params.size()-1 ; i++) {
			EgovMap item = new EgovMap();
    		item.put("cId", params.get("cId"));
    		item.put("fileOrgName", params.get(Integer.toString(i)));
    		this.campaignModifyService.deleteCampaignExistFile(item);
		}
    	
    	return mv;
    }
    
    @RequestMapping(value = "/main/modify/fileUpload.do", method = RequestMethod.POST)
    public String campaignFileUpload(RedirectAttributes redirect, MultipartHttpServletRequest req, HttpServletResponse res, HttpSession session) throws Exception {

        EgovMap params = this.paramMap(req);
        params.put("cId", this.campaignId);
        List<Map<String,Object>> listFileInfo = fileUtils.parseUpdateFileInfo(req, "C:\\upload\\campaign\\file\\", this.campaignId);
        System.out.println("과연: " + listFileInfo);
        if(listFileInfo.size() != 0) {
        	this.campaignModifyService.insertCampaignFile(listFileInfo);
        }
        
        return "redirect:/main/info.do?campaignId=" + Integer.toString(this.campaignId);
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