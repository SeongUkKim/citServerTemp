package egovframework.sys.main.modify;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import org.springframework.stereotype.Repository;

@Repository("campaignModifyDAO")
public class CampaignModifyDAO extends EgovAbstractMapper {
	public List<EgovMap> selectMentorCategory(EgovMap params){
		return selectList("modify.selectMentorCategory", params);
	}

	public List<EgovMap> selectCampaignInfo(EgovMap params) {
		return selectList("modify.selectCampaignInfo", params);
	}

	public List<EgovMap> selectCampaignCategory(EgovMap params) {
		return selectList("modify.selectCampaignCategory", params);
	}

	public List<EgovMap> selectCampaignCalender(EgovMap params) {
		return selectList("modify.selectCampaignCalender", params);
	}

	public List<EgovMap> selectCampaignImg(EgovMap params) {
		return selectList("modify.selectCampaignImg", params);
	}

	public List<EgovMap> selectCampaignLocation(EgovMap params) {
		return selectList("modify.selectCampaignLocation", params);
	}

	public List<EgovMap> selectCampaignFile(EgovMap params) {
		return selectList("modify.selectCampaignFile", params);
	}

	public void insertCampaignImg(List<Map<String, Object>> listFileInfo) {
		update("modify.insertCampaignImg", listFileInfo);
	}

	public void updateCampaignInfo(EgovMap params) {
		update("modify.updateCampaignInfo", params);
	}

	public void insertCampainCategory(List<EgovMap> params) {
		insert("modify.insertCampaignCategory", params);
	}

	public void deleteCampaignCategory(EgovMap params) {
		delete("modify.deleteCampaignCategory", params);
	}

	public void deleteCampaignImg(EgovMap params) {
		delete("modify.deleteCampaignImg", params);
	}

	public void deleteCampaignExistFile(EgovMap params) {
		delete("modify.deleteCampaignExistFile", params);
	}

	public void insertCampaignFile(List<Map<String, Object>> listFileInfo) {
		insert("modify.insertCampaignFile", listFileInfo);
	}

	public void insertCampaignCalender(List<Map<String, String>> calenderParams) {
		insert("modify.insertCampaignCalender", calenderParams);
	}

	public void deleteCampaignCalender(EgovMap params) {
		delete("modify.deleteCampaignCalender", params);
	}
}
