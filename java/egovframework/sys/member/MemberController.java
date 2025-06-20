package egovframework.sys.member;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import egovframework.sys.main.MainService;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import egovframework.sys.cmm.util.EncryptionComponent;
//import egovframework.sys.cmm.util.MailUtil;
import egovframework.sys.sec.handler.CitUserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Resource(name = "memberService")
	private MemberService memberService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;

	@Autowired
	private EncryptionComponent encComp;

	@Autowired
	protected AuthenticationManager authenticationManager;
	

  @Autowired
  private JavaMailSender mailSender;

	/* 회원가입 */
	@RequestMapping(value = "/join.do", method = RequestMethod.POST)
	public String join(RedirectAttributes redirect, HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		
		EgovMap params = paramMap(req);
		try {
			params.put("uId", params.get("joinId").toString());
			params.put("uCrpt", this.encComp.sha256(params.get("joinId").toString()));
			params.put("uPw", this.encComp.sha256(params.get("joinPw").toString()));
			params.put("idGenAgr", "Y");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(params.get("lon_x"));
		System.out.println(params.get("lat_y"));
		int insertResult = this.memberService.insertMemberJoin(params);

		String id = req.getParameter("join_id").toString();
		String password = req.getParameter("join_pw").toString();

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(id, password);
		req.getSession();

		token.setDetails(new WebAuthenticationDetails(req));
		Authentication authenticatedUser = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

		String message = null;
		boolean mFlag = true;
		if (insertResult > 0) {
			message = "회원가입이 정상적으로 처리되었습니다.";
		} else {
			message = "회원가입 도중 문제가 발생하였습니다. 잠시후에 다시 시도해주시기바랍니다.";
		}
		redirect.addFlashAttribute("mFlag", mFlag);
		redirect.addFlashAttribute("message", message);

		return "redirect:/index.do";
	}

	/* 회원가입 아이디 중복검사 */
	@RequestMapping(value = "/member/check/id.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView idCheck(HttpServletRequest req, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap params = paramMap(req);
		int data = this.memberService.idConfirm(params);
		mv.addObject("cnt", data);

		return mv;
	}

	@RequestMapping(value = "/member/find/password.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView findPassword(HttpServletRequest req, ModelAndView mv) {

		mv.setViewName("jsonView");
		EgovMap params = paramMap(req);
		params.put("uId", req.getParameter("pwchk_id"));
		params.put("uNm", req.getParameter("pwchk_name"));

		List<EgovMap> userInfo = this.memberService.selectMemberNo(params);
		String userId = userInfo.get(0).get("id").toString();
		String userEmail = userInfo.get(0).get("uId").toString();
		int result = this.memberService.pwchkConfirm(params);
		boolean mailSend = false;
		if (result == 1) {
			String title = "JW CRM 비밀번호 재설정";
			String body = "<a style=\"display: inline-block;\" href=\""
					+ "http://118.67.133.195:8080" + "/index\">"
					+ "<div style=\"background-image:url('" + "http://118.67.133.195:8080"
					+ "/resources/images/datarget_logo.svg'); background-size:250px 40px; width:250px; height:40px;\"></div></a>\r\n"
					+ "<p>안녕하세요.</p>\r\n" + "<p>조금 전 누군가가 회원님의 JW CRM 비밀번호 변경을 요청하였습니다.<br>\r\n"
					+ "회원님이 요청하신 경우 여기에서 비밀번호를 새로 설정할 수 있습니다.</p>\r\n" + "<h3><a href='"
					+ "http://118.67.133.195:8080" + "/member/rewrite/password.do?seq=" + userId
					+ "' style=\"display: inline-block; width: 200px; text-align: center; padding: 10px; text-decoration: none; background-color: #007bff; color: #FFFFFF;\">비밀번호 재설정</a></h3>\r\n"
					+ "<p>비밀번호를 변경하고 싶지 않거나<br>\r\n" + "본인이 요청한 것이 아닌 경우, 본 메일을 통해 비밀번호를 재설정하지 않아도 무방합니다.</p>\r\n"
					+ "<p>계정을 안전하게 보호하려면 본 이메일을 누구에게도 전달하지 마세요.<br>\r\n"
					+ "더 궁금하신 사안이 있으시다면 <a href=\"mailto:td1118@yonsei.ac.kr\">td1118@yonsei.ac.kr</a> 로 메일을 보내주세요.\r\n"
					+ "<br><br>\r\n" + "감사합니다.\r\n" + "</p>";

			final MimeMessagePreparator preparator = new MimeMessagePreparator() {
				@Override
				public void prepare(MimeMessage mimeMessage) throws Exception {
					final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					helper.setFrom("JW CRM <td1118@yonsei.ac.kr>");
					helper.setTo(new InternetAddress(userEmail));
					helper.setSubject(title);
					helper.setText(body, true);
				}
			};
			mailSender.send(preparator);

			mailSend = true;
		}

		String message = null;
		boolean mFlag = true;
		if (result > 0) {
			if (mailSend) {
				message = "입력하신 메일 주소로 비밀번호 재설정 메일을 발송하였습니다.";
				this.memberService.updateMemberRwPw(params);
			} else {
				message = "재설정 메일을 발송하는 중 문제가 발생하였습니다. 잠시 후 다시 진행해주세요.";
				mFlag = false;
			}
		} else {
			message = "일치하는 계정이 없습니다.";
			mFlag = false;
		}

		mv.addObject("mFlag", mFlag);
		mv.addObject("message", message);

		return mv;
	}

	//비밀번호 찾기 success
	@RequestMapping(value = "/member/rewrite/password.do", method = RequestMethod.GET)
	public ModelAndView campaignPwchkConfirm(ModelAndView mv, HttpServletRequest req, @RequestParam("seq") String seq) {
		mv.setViewName("index/index"); //

		EgovMap params = paramMap(req);
		String userEmail = this.memberService.selectMemberEmail(params);

		mv.addObject("userEmail", userEmail);
		if (userEmail != null) {
			mv.addObject("rewrite", true);
		} else {
			mv.addObject("rewrite", false);
		}
		return mv;
	}

	@RequestMapping(value = "/member/change/passwordSameCk.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView passwordSameCk(ModelAndView mv, HttpServletRequest req, HttpServletResponse res,
									   HttpSession session) {
		mv.setViewName("jsonView");
		EgovMap params = paramMap(req);

		String beforePW = this.memberService.selectBeforePassword(params);
		mv.addObject("beforePW", beforePW);

		return mv;
	}

	@RequestMapping(value = "/member/change/password.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView pwChangeAJAX(ModelAndView mv, HttpServletRequest req, HttpServletResponse res,
									 HttpSession session) {
		mv.setViewName("index/index");
		EgovMap params = paramMap(req);
		this.memberService.updateMemberpwChange(params);

		return mv;
	}

	/* 회원정보 수정 */
	@RequestMapping(value = "/member/change/modifyInfo.do", method = RequestMethod.POST)
	public String MemberModify(ModelAndView mv, HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		mv.setViewName("home/main");

		EgovMap params = paramMap(req);

		try {
			params.put("uPw", this.encComp.sha256(req.getParameter("change_pw").toString()));
			params.put("uId", this.encComp.sha256(req.getParameter("pwChange_id").toString()));
			System.out.println(params.get("uPw"));
			System.out.println(params.get("uId"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.memberService.updateMemberModify(params);

		String id = req.getParameter("pwChange_id").toString();
		String password = req.getParameter("change_pw").toString();

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(id, password);
		req.getSession();

		token.setDetails(new WebAuthenticationDetails(req));
		Authentication authenticatedUser = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CitUserDetails userDetails = (CitUserDetails) authentication.getDetails();

		String auth = userDetails.getAuthority();

		if ("A".equals(auth)) {
			return "redirect:/mainATable.do";
		} else {
			return "redirect:/mainBTable.do";
		}
	}

	/* 기존 비밀번호 동일 여부 */
	@RequestMapping(value = "/member/password/history.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView passwordHistory(HttpServletRequest req, ModelAndView mv) {
		mv.setViewName("jsonView");
		EgovMap params = paramMap(req);
		int data = this.memberService.passwordHistory(params);
		mv.addObject("cnt", data);
		// 1이면 기존비밀번호 동일
		// 0이면 다름
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

}
