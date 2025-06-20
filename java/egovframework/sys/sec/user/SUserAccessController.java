package egovframework.sys.sec.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SUserAccessController {
	
	private static final Logger logger = LoggerFactory.getLogger(SUserAccessController.class);
	
	@Resource(name = "userAccessService")
	private SUserAccessService SERVICE;	

	@RequestMapping(value = "/loginSuccess.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView loginSuccess(ModelAndView mv,HttpServletRequest request, UsernamePasswordAuthenticationToken authenticationToken) {
		mv.setViewName("index/loginSuccess");
		Object adminId = authenticationToken.getDetails();

		mv.addObject("id", authenticationToken.getPrincipal());
		mv.addObject("auth", authenticationToken.getAuthorities());
		mv.addObject("Details", authenticationToken.getDetails());
		
		return mv;
	}

	@RequestMapping(value = "/invalid", method = { RequestMethod.GET, RequestMethod.POST })
	public String closeSession(RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return "redirect:/index.do";
	}
	
	@RequestMapping(value = "/expired", method = { RequestMethod.GET, RequestMethod.POST })
	public String overlapSession(ModelMap model) {
		return "error/overlap-session";
	}

	@RequestMapping(value = "/accessDenied.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String accessDenied(ModelMap model) {
		return "error/401";
	}

}
