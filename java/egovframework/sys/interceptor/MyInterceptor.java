package egovframework.sys.interceptor;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.sys.cmm.util.UtilityComponent;
import egovframework.sys.member.MemberService;
import egovframework.sys.sec.handler.CitUserDetails;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public class MyInterceptor extends HandlerInterceptorAdapter{

	@Resource(name = "memberService")
	private MemberService memberService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		EgovMap AccessLog = new EgovMap();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();
		String userId = null;
		String auth = userDetails.getAuthority();
		
		if(auth.equals("B") || auth.equals("A")) {
			try {
				userId = userDetails.getUser_id();
			} catch (Exception e) {
				e.printStackTrace();
			}
			StringBuffer url = request.getRequestURL();
			String AccessUrl = url.toString();
			SimpleDateFormat Svrtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String AccessTime = Svrtime.format(System.currentTimeMillis());
			
			AccessLog.put("uid", userId);
			AccessLog.put("accessurl", AccessUrl);
			AccessLog.put("accesstime", AccessTime);
			
			this.memberService.insertAccessLog(AccessLog);
		}
		else{
			/*관리자 권한*/
			String accessUserId = request.getParameter("uid");
			if(accessUserId!=null) {
				userDetails.setUser_id(accessUserId);
			}else {
				userDetails.setUser_id(userDetails.getUser_id());
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv) throws Exception {

		EgovMap param = new EgovMap();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();
		String userId = null;
		
		String auth = userDetails.getAuthority();
		
		if(auth.equals("B") || auth.equals("A")) {
			/*사용자 권한*/
			try {
				userId = userDetails.getUser_id();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (userId != null) {
				param.put("uid", userId);
				param.put("curCnt", 0);
				param.put("nextCnt", 0);
				//EgovMap cntAlarm = this.memberService.countAlarm(param);
				//mv.addObject("cntAlarm", cntAlarm);
				//List<EgovMap> alarmList = this.memberService.selectAlarmList(param);
				//mv.addObject("alarmList", alarmList);
				//List<EgovMap> campaignList = this.campaignService.selectCampaignList(param);
				//mv.addObject("campaignList", campaignList);
			}
		}else {
			/*관리자 권한*/
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache"); 
		}
	}

}
