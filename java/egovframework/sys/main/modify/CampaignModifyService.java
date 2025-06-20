package egovframework.sys.main.modify;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface CampaignModifyService {

	List<EgovMap> selectMentorCategory(EgovMap params);

	List<EgovMap> selectCampaignInfo(EgovMap params);

	List<EgovMap> selectCampaignCategory(EgovMap params);

	List<EgovMap> selectCampaignCalender(EgovMap params);

	List<EgovMap> selectCampaignImg(EgovMap params);

	List<EgovMap> selectCampaignLocation(EgovMap params);

	List<EgovMap> selectCampaignFile(EgovMap params);

	void insertCampaignImg(List<Map<String, Object>> listFileInfo);

	void updateCampaignInfo(EgovMap params);

	void deleteCampaignImg(EgovMap params);

	void deleteCampaignExistFile(EgovMap params);

	void insertCampaignFile(List<Map<String, Object>> listFileInfo);

	void insertCampaignCalender(List<Map<String, String>> calenderParams);

	void deleteCampaignCalender(EgovMap params);
}
