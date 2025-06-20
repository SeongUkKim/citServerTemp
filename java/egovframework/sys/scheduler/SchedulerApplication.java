package egovframework.sys.scheduler;

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
import egovframework.sys.admin.AdminService;
import egovframework.sys.cmm.util.PagingVO;
import egovframework.sys.main.create.CampaignCreateService;
import egovframework.sys.sec.handler.CitUserDetails;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
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
import org.springmodules.validation.commons.DefaultBeanValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableScheduling
@Component
public class SchedulerApplication {
	
	@Resource(name = "schedulerService")
	private SchedulerApplicationService schedulerService;
	
	@Scheduled(cron = "0 0 0 * * *") //  매일 00시
//	@Scheduled(fixedRate = 5000)
	public void demoServiceMethod() {
		
		schedulerService.updateToProgressCampaign();
		
	}
	
	
	//진행중인거 중에 종료날짜 -> 캠페인 종료 + 종료 알람
	@Scheduled(cron = "0 0 0 * * *") //  매일 00시
	//@Scheduled(fixedRate = 5000)
	public void updateToEndCampaign() {
		
		schedulerService.updateToEndCampaign();
		
	}
	
	
}