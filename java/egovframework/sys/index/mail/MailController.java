package egovframework.sys.index.mail;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.sys.main.MainController;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

@Controller
public class MailController {
	private static final Logger logger = LoggerFactory.getLogger(MailController.class);
	
	@Autowired
	private JavaMailSender mailSender;

	@ResponseBody
	@RequestMapping(value = "/member/ajax/sendJoinCode.do", method = RequestMethod.POST)
	public String checkEmailAjax(HttpServletRequest req, HttpServletResponse res, ModelAndView mv) throws Exception {	
		String to = req.getParameter("email");
		
		int random = new Random().nextInt(90000) + 10000; // 10000 ~ 99999

		String joinCode = String.valueOf(random);
		String subject = "회원가입 인증 코드 입니다.";
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("안녕하세요. JW CRM입니다.\r귀하의 인증 코드는  <" + joinCode + "> 입니다.");
		String from = "td1118@yonsei.ac.kr";
		String content = stringBuilder.toString();

		try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(mail,true,"UTF-8");
  
            mailHelper.setFrom(from);
            mailHelper.setTo(to);
            mailHelper.setSubject(subject);
            mailHelper.setText(content, true);
            mailSender.send(mail);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
		return joinCode;
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
