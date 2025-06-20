package egovframework.sys.main.applyList;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.sys.main.applyList.CampaignApplyListDAO;

@Service("campaignApplyList")
public class CampaignApplyListServiceImpl implements CampaignApplyListService {
  @Resource(name="campaignApplyListDAO")
  private CampaignApplyListDAO campaignApplyListDAO;

  @Override
  public List<EgovMap> selectApprovedCampaignList(EgovMap params) {
    // TODO Auto-generated method stub
    return this.campaignApplyListDAO.selectApprovedCampaignList(params);
  }

  @Override
  public void updateApplyStatus(EgovMap params) {
    this.campaignApplyListDAO.updateApplyStatus(params);
    
  }

  @Override
  public void updateTeacherBalancePlus(EgovMap params) {
    this.campaignApplyListDAO.updateTeacherBalancePlus(params);
  }

  @Override
  public void updateStudentBalanceMinus(EgovMap params) {
    this.campaignApplyListDAO.updateStudentBalanceMinus(params);
  }

  @Override
  public void updateTeacherBalanceMinus(EgovMap params) {
    this.campaignApplyListDAO.updateTeacherBalanceMinus(params);    
  }

  @Override
  public void updateStudentBalancePlus(EgovMap params) {
    this.campaignApplyListDAO.updateStudentBalancePlus(params);    
  }

  @Override
  public void updateStudentConfirm(EgovMap params) {
    this.campaignApplyListDAO.updateStudentConfirm(params);
    this.campaignApplyListDAO.updateRestOfStudent(params);
  }

  @Override
  public void updateCampaign(EgovMap params) {
    this.campaignApplyListDAO.updateCampaign(params);
  }

  @Override
  public List<EgovMap> selectCampaignInfo(EgovMap params) {
	  return this.campaignApplyListDAO.selectCampaignInfo(params);
  }

	@Override
	public int selectApprovedStudentCount(EgovMap params) {
		return this.campaignApplyListDAO.selectApprovedStudentCount(params);
	}

}
