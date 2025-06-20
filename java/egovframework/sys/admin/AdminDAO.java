package egovframework.sys.admin;

import java.util.List;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import org.springframework.stereotype.Repository;


@Repository("adminDAO")
public class AdminDAO extends EgovAbstractMapper {
	
	public List<EgovMap> selectAccessLogList(EgovMap params) {
		return selectList("Admin.selectAccessLogList", params);	
	}
	
	public List<EgovMap> selectMemberInfoList(EgovMap params) {
		return selectList("Admin.selectMemberInfoList", params);	
	}
	
	public int selectCountAccessLogList(EgovMap params) {
		return selectOne("Admin.selectCountAccessLogList", params);
	}
	
	public List<EgovMap> selectCalculList(EgovMap params) {
		return selectList("Admin.selectCalculList", params);	
	}
	public int selectCampainCount(EgovMap params){
		return selectOne("Admin.selectCampainCount");
	}
	
	public EgovMap selectCampaignBoard(EgovMap params){
		return selectOne("Admin.selectCampaignBoard", params);
	}
	
	public List<EgovMap> selectCampaignFile(EgovMap params){
		return selectList("Admin.selectCampaignFile", params);
	}
	
	public List<EgovMap> selectCampaignCalender(EgovMap params){
		return selectList("Admin.selectCampaignCalender", params);
	}
	
	public List<EgovMap> selectCampaignCategory(EgovMap params){
		return selectList("Admin.selectCampaignCategory", params);
	}
	public EgovMap selectCampaignByCid(EgovMap params) {
		return selectOne("Admin.selectCampaignByCid", params);
	}
	
	public EgovMap selectCampaignImg(EgovMap params){
		return selectOne("Admin.selectCampaignImg", params);
	}
	public EgovMap selectUserInfoByCid(EgovMap params) {
		return selectOne("Admin.selectUserInfoByCid", params);
	}
	
	public void UpdateApproveCampaign(EgovMap params){
		 update("Admin.UpdateApproveCampaign", params);
	}
	public void UpdateRefuseCampaign(EgovMap params){
		 update("Admin.UpdateRefuseCampaign", params);
	}
	
	public List<EgovMap> selectCampainApproved(EgovMap params){
		 return selectList("Admin.selectCampainApproved", params);
	}
	public int selectCampainApprovedCount(EgovMap params){
		 return selectOne("Admin.selectCampainApprovedCount", params);
	}

	
	public List<EgovMap> selectCampainRefused(EgovMap params){
		 return selectList("Admin.selectCampainRefused", params);
	}
	public int selectCampainRefusedCount(EgovMap params){
		 return selectOne("Admin.selectCampainRefusedCount", params);
	}
	
	public List<EgovMap> selectCampainProgressed(EgovMap params){
		 return selectList("Admin.selectCampainProgressed", params);
	}
	public int selectCampainProgressedCount(EgovMap params){
		 return selectOne("Admin.selectCampainProgressedCount", params);
	}
	
	public void deleteCampaign(EgovMap params){
		 delete("Admin.deleteCampaign", params);
	}
	
	public List<EgovMap> selectMember(EgovMap params){
		 return selectList("Admin.selectMember", params);
	}
	public int selectMemberCount(EgovMap params){
		 return selectOne("Admin.selectMemberCount", params);
	}
	
	public List<EgovMap> selectAuthMember(EgovMap params){
		 return selectList("Admin.selectAuthMember", params);
	}
	public int selectAuthMemberCount(EgovMap params){
		 return selectOne("Admin.selectAuthMemberCount", params);
	}
	
	public EgovMap selectMemberBoard(EgovMap params){
		return selectOne("Admin.selectMemberBoard", params);
	}
	
	
	public void updateApproveMember(EgovMap params){
		update("Admin.updateApproveMember", params);
	}
	//권한 b로 바꿀시 삭제
	public void deleteApply(EgovMap params){
		delete("Admin.deleteMentorApply", params);
		
		
	}
	
	public void deleteMentorApply(EgovMap params){
		delete("Admin.deleteMentorApply", params);
		delete("Admin.deleteMentorApplyCategory", params);
		delete("Admin.deleteMentorApplyFile", params);
		
	}
	
	
	public void updateApproveNormalMember(EgovMap params){
		update("Admin.updateApproveNormalMember", params);
	}
	//미인증 member 검색시
	public EgovMap selectApplyMemberBoard(EgovMap params){
		return selectOne("Admin.selectApplyMemberBoard", params);
	}
	public List<EgovMap> selectApplyMemberCategory(EgovMap params){
		return selectList("Admin.selectApplyMemberCategory", params);
	}
	public List<EgovMap> selectApplyMemberFile(EgovMap params){
		return selectList("Admin.selectApplyMemberFile", params);
	}
	
	//알람 관리자에서
	public Integer selectAlarmCount(EgovMap params) {
		return selectOne("Admin.selectAlarmCount", params);
	}
	
	public List<EgovMap> selectAlarm(EgovMap params){
		return selectList("Admin.selectAlarm", params);
	}
	//관리자가 알람 답변시
	public void insertAlarmSubmit(EgovMap params) {
		insert("Admin.insertAlarmSubmit", params);
	}
	public void updateAlarmSubmit(EgovMap params) {
		insert("Admin.updateAlarmSubmit", params);
	}
	
	//캠페인 변경시 모든알람
	public void insertAlarmAll(EgovMap params) {
		insert("Admin.insertAlarmAll", params);
	}
	
	public List<EgovMap> selectRecentMessage(EgovMap params){
		return selectList("Admin.selectRecentMessage", params);
	}
	
	
}
