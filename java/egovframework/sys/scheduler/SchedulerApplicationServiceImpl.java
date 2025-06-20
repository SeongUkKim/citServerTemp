package egovframework.sys.scheduler;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.sys.admin.AdminDAO;

@Service("schedulerService")
public class SchedulerApplicationServiceImpl implements SchedulerApplicationService{

	@Resource(name = "schedulerDao")
	private SchedulerApplicationDao scedulerDao;
	
	@Override
	public void updateToProgressCampaign() {
		 this.scedulerDao.updateToProgressCampaign();
	}

	@Override
	public void updateToEndCampaign() {
		this.scedulerDao.updateToEndCampaign();
	}
	
	
	@Override
	public List<EgovMap> schedulerTest() {
		return this.scedulerDao.schedulerTest();
	}

	@Override
	public List<EgovMap> selectSchedulerLog(int pagenum) {
		return this.scedulerDao.selectSchedulerLog(pagenum);
	}

}
