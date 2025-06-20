package egovframework.sys.main.mentor;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import org.springframework.stereotype.Repository;


@Repository("campaignMentorDAO")
public class CampaignMentorDAO extends EgovAbstractMapper {

	public void insertMentorInfo(EgovMap params) {
		insert("Member.insertMentorInfo", params);	
	}
	
	public void insertMentorFile(List<Map<String, Object>> listFile){
		insert("Member.insertMentorFile", listFile);
	}
	
	public void insertMentorCategory(List<Map<String, Object>> categoryList) {
		insert("Member.insertMentorCategory", categoryList);
	}
	
	public void updateMentorStatus(EgovMap params) {
		update("Member.updateMentorStatus", params);
	}
	
	public List<EgovMap> selectUserId(EgovMap params) {
		return selectList("Member.selectUserId", params);
	}
}
