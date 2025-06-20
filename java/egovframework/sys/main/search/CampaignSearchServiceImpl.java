package egovframework.sys.main.search;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("campaignSearch")
public class CampaignSearchServiceImpl implements CampaignSearchService {
	
	@Resource(name="campaignSearchDAO")
	private CampaignSearchDAO campaignSearchDAO;

	@Override
	public List<EgovMap> selectFilteredKeywordCampaignList(EgovMap params) {
		if(StringUtils.isEmpty(params.get("statusCheck"))){
			params.put("statusCheck", "");
		}
		return this.campaignSearchDAO.selectFilteredKeywordCampaignList(params);
	}

	@Override
	public int selectKeywordCount(EgovMap params) {
		if(StringUtils.isEmpty(params.get("statusCheck"))){
			params.put("statusCheck", "");
		}
		return this.campaignSearchDAO.selectKeywordCount(params);
	}

	@Override
	public List<EgovMap> selectUserLocation(EgovMap params) {
		return this.campaignSearchDAO.selectUserLocation(params);
	}
}
