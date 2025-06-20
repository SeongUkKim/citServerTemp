package egovframework.sys.main.review;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import org.springframework.stereotype.Repository;


@Repository("campaignReviewDAO")
public class CampaignReviewDAO extends EgovAbstractMapper {
	
	public EgovMap selectCampaignMentor(EgovMap params) {
		return selectOne("campain.selectCampaignMentor", params);
	}
	
	public void insertReview(EgovMap params) {
		insert("Member.insertReview", params);
	}
	
	public EgovMap selectReviewCheck(EgovMap params) {
		return selectOne("Member.selectReviewCheck", params);
	}
	
	public EgovMap selectUserReview(EgovMap params) {
		return selectOne("Member.selectUserReview", params);
	}
}
