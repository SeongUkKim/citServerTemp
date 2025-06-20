package egovframework.sys.main.search;

import java.util.List;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface CampaignSearchService {
	List<EgovMap> selectFilteredKeywordCampaignList(EgovMap params);

	int selectKeywordCount(EgovMap params);

	List<EgovMap> selectUserLocation(EgovMap params);
	
}
