package egovframework.sys.index;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import egovframework.sys.main.MainService;
import egovframework.sys.sec.handler.CitUserDetails;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springmodules.validation.commons.DefaultBeanValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Resource(name = "indexService")
	private IndexService indexService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView index(ModelAndView mv, HttpServletRequest req, HttpServletResponse response) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			CitUserDetails userDetails = (CitUserDetails)authentication.getDetails();
			String auth = userDetails.getAuthority();
			mv.addObject("auth", auth);
		}
		mv.setViewName("index/index");
		return mv;
	}
	
	@RequestMapping(value = "/main/inquireForm.do")
	public String inquireFormIndex(RedirectAttributes redirect, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws Exception {
		
		EgovMap params = this.paramMap(req);

		this.indexService.insertInquireIndex(params);

		return "redirect:/index.do";
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
