package egovframework.sys.admin;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;


public interface AdminService {
	
	List<EgovMap> selectAccessLogList(EgovMap params);
	
	List<EgovMap> selectMemberInfoList(EgovMap params);
	
	int selectCountAccessLogList(EgovMap params);
	
	List<EgovMap> selectCalculList(EgovMap params);
	
	int selectCampainCount(EgovMap params);
	
	EgovMap selectCampaignBoard(EgovMap params);
	
	EgovMap selectUserInfoByCid(EgovMap params);
	
	void UpdateApproveCampaign(EgovMap params);
	
	void UpdateRefuseCampaign(EgovMap params);
	
	List<EgovMap> selectCampainApproved(EgovMap params);
	
	int selectCampainApprovedCount(EgovMap params);
	

	List<EgovMap> selectCampainRefused(EgovMap params);
	
	int selectCampainRefusedCount(EgovMap params);
	
	List<EgovMap> selectCampainProgressed(EgovMap params);
	
	int selectCampainProgressedCount(EgovMap params);
	
	void deleteCampaign(EgovMap params);
	
	List<EgovMap> selectMember(EgovMap params);
	
	int selectMemberCount(EgovMap params);
	
	List<EgovMap> selectAuthMember(EgovMap params);
	
	int selectAuthMemberCount(EgovMap params);
	
	EgovMap selectMemberBoard(EgovMap params);
	
	void updateApproveMember(EgovMap params);
	
	void updateApproveNormalMember(EgovMap params);
	
	//미인증 member 검색시
	public EgovMap selectApplyMemberBoard(EgovMap params);

	//알람 관리자
	public Integer selectAlarmCount(EgovMap params);
	
	public List<EgovMap> selectAlarm(EgovMap params);
	
	//관리자 알람답변시
	public void insertAlarmSubmit(EgovMap params);
	
	public void updateAlarmSubmit(EgovMap params);
	
	//캠페인 알람 모두
	public void insertAlarmAll(EgovMap params);
	
	//사용자 알람 삭제시 status 4
	public void updateAlarmStatus4(EgovMap params);
	
	public List<EgovMap> selectRecentMessage(EgovMap params);
	
	
}
