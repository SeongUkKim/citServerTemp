package egovframework.sys.scheduler;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("schedulerDao")
public class SchedulerApplicationDao extends EgovAbstractMapper {

	public void updateToProgressCampaign() {

		List<EgovMap> params = selectList("scheduler.selectCampaignProgressList");

		int expectnumOfupdate = selectOne("scheduler.countCampaignProgressList");

		// System.out.println(params);

		// List<EgovMap> params = selectList("scheduler.schedulerTest");

		for (int i = 0; i < params.size(); i++) {
			// EgovMap
			params.get(i).put("email", params.get(i).get("uId"));
			params.get(i).put("title", "Proker 캠페인 알림");
			params.get(i).put("context", "[" + params.get(i).get("cNm") + "]" + " 회원님의 캠페인이 진행됩니다.");
		}

		insert("scheduler.insertAlarmAll", params);

		int numOfupdate = update("scheduler.updateToProgressCampaign");

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("numOfSuccess", numOfupdate);
		map.put("numOfFail", expectnumOfupdate - numOfupdate);

		insert("scheduler.insertSchedulerLog", map);

	}
	
	public void updateToEndCampaign() {
		
		List<EgovMap> params = selectList("scheduler.selectCampaignToEndList"); // 종료될 리스트

		int expectnumOfupdate = selectOne("scheduler.countCampaignToEndList"); //종료될 리스트 숫자

		for (int i = 0; i < params.size(); i++) {
			// EgovMap
			params.get(i).put("email", params.get(i).get("uId"));
			params.get(i).put("title", "Proker 캠페인 알림");
			params.get(i).put("context", "[" + params.get(i).get("cNm") + "]" + " 회원님의 캠페인이 종료됩니다.");
		}
		
		insert("scheduler.insertAlarmAll", params); //알람 보내기
		
		int numOfupdate = update("scheduler.updateToEndCampaign");
		
	}
	

	public List<EgovMap> schedulerTest() {
		return selectList("scheduler.schedulerTest");
	}
	
	public List<EgovMap> selectSchedulerLog(int pagenum) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("start", (pagenum-1)*30);
		map.put("end", pagenum*30);

		return selectList("scheduler.selectSchedulerLog", map);
	}

}
