package egovframework.sys.main.applyList;

import java.util.List;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface CampaignApplyListService {

    List<EgovMap> selectApprovedCampaignList(EgovMap params);

    void updateApplyStatus(EgovMap params);

    void updateTeacherBalancePlus(EgovMap params);

    void updateStudentBalanceMinus(EgovMap params);

    void updateTeacherBalanceMinus(EgovMap params);

    void updateStudentBalancePlus(EgovMap params);

    void updateStudentConfirm(EgovMap params);

    void updateCampaign(EgovMap params);

	List<EgovMap> selectCampaignInfo(EgovMap params);

	int selectApprovedStudentCount(EgovMap params);
    
}
