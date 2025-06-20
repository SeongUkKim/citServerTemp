package egovframework.sys.main.create;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;


public interface CampaignCreateService {

	List<EgovMap> selectMentorCategory(EgovMap params);
	
	void insertCampaign(EgovMap params);
	
	List<EgovMap> selectCampaign(EgovMap params);
	
	void insertFile(List<Map<String,Object>> listFileInfo);
	
	void insertCampaignCalender(List<Map<String,String>> list);
	
	void insertCampaignImg(List<Map<String,Object>> listImg);
	
	int campaignCount(EgovMap params);
}
