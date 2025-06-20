package egovframework.sys.main.review;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("campaignReview")
public class CampaignReviewServiceImpl implements CampaignReviewService {
	
	@Resource(name = "campaignReviewDAO")
	private CampaignReviewDAO campaignReviewDAO;
	
	@Override
	public EgovMap selectCampaignMentor(EgovMap params) {
		return this.campaignReviewDAO.selectCampaignMentor(params);
	}
	
	@Override
	public void insertReview(EgovMap params) {
		this.campaignReviewDAO.insertReview(params);
	}
	
	@Override
	public EgovMap selectReviewCheck(EgovMap params) {
		return this.campaignReviewDAO.selectReviewCheck(params);
	}
	
	@Override
	public EgovMap selectUserReview(EgovMap params) {
		return this.campaignReviewDAO.selectUserReview(params);
	}
}