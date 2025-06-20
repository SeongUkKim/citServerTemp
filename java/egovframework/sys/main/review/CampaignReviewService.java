package egovframework.sys.main.review;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;


public interface CampaignReviewService {
	
	void insertReview(EgovMap params);
	
	public EgovMap selectCampaignMentor(EgovMap params);
	
	public EgovMap selectReviewCheck(EgovMap params);
	
	public EgovMap selectUserReview(EgovMap params);
}
