package egovframework.sys.main.mentorReview;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import org.springframework.stereotype.Repository;


@Repository("campaignMentorReviewDAO")
public class CampaignMentorReviewDAO extends EgovAbstractMapper {
	
	public List<EgovMap> selectReviewList(EgovMap params) {
		return selectList("Member.selectReviewList", params);
	}
	
}
