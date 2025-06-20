package egovframework.sys.main.mentorReview;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("campaignMentorReview")
public class CampaignMentorReviewServiceImpl implements CampaignMentorReviewService {
	
	@Resource(name = "campaignMentorReviewDAO")
	private CampaignMentorReviewDAO campaignMentorReviewDAO;
	
	@Override
	public List<EgovMap> selectReviewList(EgovMap params) {
		return this.campaignMentorReviewDAO.selectReviewList(params);
	}

}