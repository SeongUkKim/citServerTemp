package egovframework.sys.main.mentorReview;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface CampaignMentorReviewService {
	public List<EgovMap> selectReviewList(EgovMap params);
}