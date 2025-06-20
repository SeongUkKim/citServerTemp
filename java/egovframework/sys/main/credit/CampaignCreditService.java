package egovframework.sys.main.credit;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface CampaignCreditService {
	
	List<EgovMap> selectMemberCredit(EgovMap params);
	
	void updateCreditBalance(EgovMap params);
	
	void insertCreditHistory(EgovMap params);
	
	List<EgovMap> selectCreditHistory(EgovMap params);
}