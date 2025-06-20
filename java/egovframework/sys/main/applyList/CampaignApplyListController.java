package egovframework.sys.main.applyList;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.sys.main.applyList.CampaignApplyListService;
import egovframework.sys.sec.handler.CitUserDetails;

@Controller
public class CampaignApplyListController {
    
    private static final Logger logger = LoggerFactory.getLogger(CampaignApplyListController.class);
    
    @Resource(name = "campaignApplyList")
    private CampaignApplyListService campaignApplyListService;
    
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    @Resource(name = "beanValidator")
    protected DefaultBeanValidator beanValidator;
    
    @RequestMapping(value = "/main/apply.do", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView CampaignApplyList(ModelAndView mv, HttpServletRequest req) throws Exception {
        mv.setViewName("/home/campaignApplyList");
        
        EgovMap params = this.paramMap(req);
        
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();
        
        String auth = userDetails.getAuthority();
        String userId = userDetails.getUser_id();
        
        
        params.put("userId", userId);
        List<EgovMap> approvedCampaignList = this.campaignApplyListService.selectApprovedCampaignList(params);
        List<EgovMap> campaignInfo = this.campaignApplyListService.selectCampaignInfo(params);
        int approvedStudentCount = this.campaignApplyListService.selectApprovedStudentCount(params);
        System.out.println(approvedCampaignList);
        
        String CSRF = UUID.randomUUID().toString();
        
        mv.addObject("campaignInfo", campaignInfo);
        mv.addObject("approvedStudentCount", approvedStudentCount);
        mv.addObject("list", approvedCampaignList);
        mv.addObject("auth", auth);
        mv.addObject("userId", userId);
        mv.addObject("CSRF_TOKEN", CSRF);
        return mv;
    }
    
    @RequestMapping(value = "/main/apply/ajax/modifyStatus.do", method = RequestMethod.GET)
    @ResponseBody
    public void modifyStatus(HttpServletRequest req,  HttpServletResponse res) throws Exception {
        EgovMap params = this.paramMap(req);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();
        
        String auth = userDetails.getAuthority();
        String userId = userDetails.getUser_id();
        
        params.put("userId", userId);
        
        
        if(req.getParameter("applyStatus").toString().equals("2")){
            System.out.println("승인");
            this.campaignApplyListService.updateApplyStatus(params);
        }else if(req.getParameter("applyStatus").toString().equals("6")) {
            System.out.println("거절");
            this.campaignApplyListService.updateApplyStatus(params);
//            this.campaignApplyListService.updateTeacherBalanceMinus(params);
            this.campaignApplyListService.updateStudentBalancePlus(params);
        }else if(req.getParameter("applyStatus").toString().equals("3")) {
            System.out.println("예약확정");
            this.campaignApplyListService.updateApplyStatus(params);
        }
        System.out.println(req.getParameter("applyStatus"));
        System.out.println(req.getParameter("cId"));
        System.out.println(params.get("uId"));
    }
    
    // 강제 모집완료 버튼을 눌렀을때 타는 함수
    @RequestMapping(value = "/main/apply/ajax/confirmCampaign.do", method = RequestMethod.GET)
    @ResponseBody
    public void confirmCampaign(HttpServletRequest req,  HttpServletResponse res) throws Exception {
        EgovMap params = this.paramMap(req);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();
        
        String auth = userDetails.getAuthority();
        String userId = userDetails.getUser_id();
     // context-security에서 hasanyrole에 A(인증된 유저)나 R(관리자)만 접근 가능하게 하자
        params.put("userId", userId);
        this.campaignApplyListService.updateStudentConfirm(params);
        this.campaignApplyListService.updateCampaign(params);
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
