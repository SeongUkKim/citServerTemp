package egovframework.sys.main.credit;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import org.springframework.stereotype.Repository;

@Repository("campaignCreditDAO")
public class CampaignCreditDAO extends EgovAbstractMapper {
		
	public List<EgovMap> selectMemberCredit(EgovMap params) {
		return selectList("Member.selectMemberCredit", params);
	}
	
	public void updateCreditBalance(EgovMap params) {
		update("Member.updateCreditBalance", params);
	}
	
	public void insertCreditHistory(EgovMap params) {
		insert("Member.insertCreditHistory", params);
	}
	
	public List<EgovMap> selectCreditHistory(EgovMap params) {
		return selectList("Member.selectCreditHistory", params);
	}

}