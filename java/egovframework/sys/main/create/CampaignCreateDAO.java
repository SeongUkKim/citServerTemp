package egovframework.sys.main.create;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import org.springframework.stereotype.Repository;


@Repository("campaignCreateDAO")
public class CampaignCreateDAO extends EgovAbstractMapper {

	public List<EgovMap> selectMentorCategory(EgovMap params){
		return selectList("campain.selectMentorCategory", params);
	}
	
	public void insertCampainCreate(EgovMap params) {
		insert("campain.insertCampainCreate", params);	
	}
	
	public List<EgovMap> selectCampainCreate(EgovMap params) {
		return selectList("campain.selectCampainCreate", params);	
	}
	
	public void insertCampainCategory(List<String> params) {
		 insert("campain.insertCampaignCategory", params);	
	}
	
	public void insertFile(List<Map<String, Object>> listFileInfo){
		insert("campain.insertFile", listFileInfo);
	}
	
	public void insertImg(List<Map<String, Object>> listImg){
		insert("campain.insertImg", listImg);
	}
	
	public void insertCampaignCalender(List<Map<String, String>> list){
		insert("campain.insertCampaignCalendar", list);
	}
	public int campaignCount(EgovMap params){
		return selectOne("campain.selectCampainCount", params);
	}
}
