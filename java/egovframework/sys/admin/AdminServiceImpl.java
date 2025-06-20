package egovframework.sys.admin;

import java.util.List;

import egovframework.sys.main.MainService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


@Service("adminService")
public class AdminServiceImpl implements AdminService {

	// TODO mybatis 사용
	@Resource(name = "adminDAO")
	private AdminDAO adminDAO;
	
	@Override
	public List<EgovMap> selectAccessLogList(EgovMap params) {
		return this.adminDAO.selectAccessLogList(params);
	}
	
	@Override
	public List<EgovMap> selectMemberInfoList(EgovMap params) {
		return this.adminDAO.selectMemberInfoList(params);
	}
	
	@Override
	public int selectCountAccessLogList(EgovMap params) {
		return this.adminDAO.selectCountAccessLogList(params);
	}
	
	@Override
	public List<EgovMap> selectCalculList(EgovMap params) {
		return this.adminDAO.selectCalculList(params);
	}

	@Override
	public int selectCampainCount(EgovMap params) {
		return this.adminDAO.selectCampainCount(params);
	}

	@Override
	public EgovMap selectCampaignBoard(EgovMap params) {
		
		EgovMap Board = this.adminDAO.selectCampaignBoard(params);
		List<EgovMap> File = this.adminDAO.selectCampaignFile(params);
		List<EgovMap> calender = this.adminDAO.selectCampaignCalender(params);
		List<EgovMap> category = this.adminDAO.selectCampaignCategory(params);
		EgovMap img = this.adminDAO.selectCampaignImg(params);
		
		Board.put("file", File);
		Board.put("calender", calender);
		Board.put("category", category);
		Board.put("img", img);
		
		return Board;
	}
	
	@Override
	public EgovMap selectUserInfoByCid(EgovMap params) {
		return this.adminDAO.selectUserInfoByCid(params);
	}
	
	@Override
	public void UpdateApproveCampaign(EgovMap params) {
		//승인함
		this.adminDAO.UpdateApproveCampaign(params);
		
		//알람
		//EgovMap userInfo = this.adminDAO.selectUserInfoByCid(params);
		EgovMap campaignInfo = this.adminDAO.selectCampaignByCid(params);
		
		params.put("email", campaignInfo.get("uId"));
		params.put("title", "Proker 캠페인 알림");
		params.put("context", "[" + campaignInfo.get("cNm") + "]" + " 회원님의 캠페인이 승인되었습니다.");

		this.adminDAO.insertAlarmAll(params);
	}
	
	@Override
	public void UpdateRefuseCampaign(EgovMap params) {
		//거절함
		this.adminDAO.UpdateRefuseCampaign(params);
		
		//알람
		EgovMap campaignInfo = this.adminDAO.selectCampaignByCid(params);
		
		params.put("email", campaignInfo.get("uId"));
		params.put("title", "Proker 캠페인 알림");
		params.put("context", "[" + campaignInfo.get("cNm") + "]" + " 회원님의 캠페인이 거절되었습니다.");

		System.out.println(params);
		
		this.adminDAO.insertAlarmAll(params);
	}
	
	@Override
	public List<EgovMap> selectCampainApproved(EgovMap params) {
		return this.adminDAO.selectCampainApproved(params);
	}

	@Override
	public int selectCampainApprovedCount(EgovMap params) {
		return this.adminDAO.selectCampainApprovedCount(params);
	}

	@Override
	public List<EgovMap> selectCampainRefused(EgovMap params) {
		return this.adminDAO.selectCampainRefused(params);
	}

	@Override
	public int selectCampainRefusedCount(EgovMap params) {
		
		return this.adminDAO.selectCampainRefusedCount(params);
	}

	@Override
	public List<EgovMap> selectCampainProgressed(EgovMap params) {
		
		return this.adminDAO.selectCampainProgressed(params);
	}

	@Override
	public int selectCampainProgressedCount(EgovMap params) {
		
		return this.adminDAO.selectCampainProgressedCount(params);
	}

	@Override
	public void deleteCampaign(EgovMap params) {
		this.adminDAO.deleteCampaign(params);
		
	}
	
	@Override
	public List<EgovMap> selectMember(EgovMap params) {
		
		return this.adminDAO.selectMember(params);
	}

	@Override
	public int selectMemberCount(EgovMap params) {
		
		return this.adminDAO.selectMemberCount(params);
	}

	@Override
	public List<EgovMap> selectAuthMember(EgovMap params) {
		
		return this.adminDAO.selectAuthMember(params);
	}

	@Override
	public int selectAuthMemberCount(EgovMap params) {
		
		return this.adminDAO.selectAuthMemberCount(params);
	}
	
	@Override
	public EgovMap selectMemberBoard(EgovMap params) {
		
		EgovMap Board = this.adminDAO.selectMemberBoard(params);
		List<EgovMap> category = this.adminDAO.selectApplyMemberCategory(params);
		List<EgovMap> file = this.adminDAO.selectApplyMemberFile(params);
		
		Board.put("file", file);
		Board.put("category", category);
		
		return Board;
	}

	//인증된 사용자 변경시 mentor_apply삭제
	@Override
	public void updateApproveMember(EgovMap params) {
		this.adminDAO.updateApproveMember(params);
		
		//this.adminDAO.deleteApply(params);
	}

	@Override
	public void updateApproveNormalMember(EgovMap params) {
		this.adminDAO.updateApproveNormalMember(params);
		this.adminDAO.deleteMentorApply(params);
		
	}

	@Override
	public EgovMap selectApplyMemberBoard(EgovMap params) {
		EgovMap Board = this.adminDAO.selectApplyMemberBoard(params);
		List<EgovMap> category = this.adminDAO.selectApplyMemberCategory(params);
		List<EgovMap> file = this.adminDAO.selectApplyMemberFile(params);
		
		Board.put("file", file);
		Board.put("category", category);
		
		return Board;
	}
	
	@Override
	public Integer selectAlarmCount(EgovMap params) {
		return this.adminDAO.selectAlarmCount(params);
	}
	

	@Override
	public List<EgovMap> selectAlarm(EgovMap params) {
		return this.adminDAO.selectAlarm(params);
	}

	@Override
	public void insertAlarmSubmit(EgovMap params) {
		this.adminDAO.insertAlarmSubmit(params);
		
	}

	@Override
	public void updateAlarmSubmit(EgovMap params) {
		this.adminDAO.updateAlarmSubmit(params);	
	}

	@Override
	public void insertAlarmAll(EgovMap params) {
		this.adminDAO.insertAlarmAll(params);
		
	}

	@Override
	public void updateAlarmStatus4(EgovMap params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EgovMap> selectRecentMessage(EgovMap params) {
		return this.adminDAO.selectRecentMessage(params);
	}
	
	
}
