package egovframework.sys.scheduler;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface SchedulerApplicationService {

	//모집완료 캠페인 시작일시 -> 진행
	void updateToProgressCampaign();
	
	//진행중인 캠페인 종료일시 -> 종료상태변환
	void updateToEndCampaign();
	
	List<EgovMap> schedulerTest();
	
	List<EgovMap> selectSchedulerLog(int pagenum);

	
}
