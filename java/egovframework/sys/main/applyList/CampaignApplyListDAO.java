package egovframework.sys.main.applyList;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("campaignApplyListDAO")
public class CampaignApplyListDAO extends EgovAbstractMapper {

  public List<EgovMap> selectApprovedCampaignList(EgovMap params) {
    // TODO Auto-generated method stub
      return selectList("apply.selectApprovedCampaignList", params);
  }

  public void updateApplyStatus(EgovMap params) {
      update("apply.updateApplyStatus", params);
  }

  public void updateBalance(EgovMap params) {
      update("apply.updateBalance", params);
    
  }

  public void updateTeacherBalancePlus(EgovMap params) {
    update("apply.updateTeacherBalancePlus", params);    
  }

  public void updateStudentBalanceMinus(EgovMap params) {
    update("apply.updateStudentBalanceMinus", params);    
  }

  public void updateTeacherBalanceMinus(EgovMap params) {
    update("apply.updateTeacherBalanceMinus", params);    
  }

  public void updateStudentBalancePlus(EgovMap params) {
    update("apply.updateStudentBalancePlus", params);    
  }

  public void updateStudentConfirm(EgovMap params) {
    update("apply.updateStudentConfirm", params);
  }

  public void updateCampaign(EgovMap params) {
    update("apply.updateCampaign", params);
  }

public void updateRestOfStudent(EgovMap params) {
	update("apply.updateRestOfStudent", params);
}

public List<EgovMap> selectCampaignInfo(EgovMap params) {
	return selectList("apply.selectCampaignInfo", params);
}

public int selectApprovedStudentCount(EgovMap params) {
	return selectOne("apply.selectApprovedStudentCount", params);
}

}
