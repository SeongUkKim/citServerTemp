package egovframework.sys.scheduler;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface SchedulerApplicationService {

	void updateToProgressCampaign();
	
	void updateToEndCampaign();
	
	List<EgovMap> schedulerTest();
	
	List<EgovMap> selectSchedulerLog(int pagenum);

	
}
