package egovframework.sys.main.search;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("campaignSearchDAO")
public class CampaignSearchDAO extends EgovAbstractMapper {
	
	public List<EgovMap> selectFilteredKeywordCampaignList(EgovMap params) {
		return selectList("search.selectFilteredKeywordCampaignList", params);
	}
	public int selectKeywordCount(EgovMap params) {
		return selectOne("search.selectKeywordCount", params);
	}
	public List<EgovMap> selectUserLocation(EgovMap params) {
		return selectList("search.selectUserLocation", params);
	}
}
