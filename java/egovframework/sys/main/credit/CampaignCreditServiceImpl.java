package egovframework.sys.main.credit;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("campaignCredit")
public class CampaignCreditServiceImpl implements CampaignCreditService {
	
	@Resource(name = "campaignCreditDAO")
	private CampaignCreditDAO campaignCreditDAO;
	
	@Override
	public List<EgovMap> selectMemberCredit(EgovMap params) {
		return this.campaignCreditDAO.selectMemberCredit(params);
	}
	
	@Override
	public void updateCreditBalance(EgovMap params) {
		this.campaignCreditDAO.updateCreditBalance(params);
	}
	
	@Override
	public void insertCreditHistory(EgovMap params) {
		this.campaignCreditDAO.insertCreditHistory(params);
	}
	
	@Override
	public List<EgovMap> selectCreditHistory(EgovMap params) {
		return this.campaignCreditDAO.selectCreditHistory(params);
	}
	
}