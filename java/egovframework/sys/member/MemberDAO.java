package egovframework.sys.member;

import java.util.List;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import org.springframework.stereotype.Repository;


@Repository("memberDAO")
public class MemberDAO extends EgovAbstractMapper {
	
	public int idConfirm(EgovMap params) {
		return selectOne("Member.idConfirm", params);	
	}
	
	public int insertMemberJoin(EgovMap params) {
		return insert("Member.insertMemberJoin", params);
	}
	
	public void insertAccessLog(EgovMap AccessLog) {
		insert("Member.insertAccessLog", AccessLog);
	}

    public List<EgovMap> selectMemberNo(EgovMap params) {
        return selectList("Member.selectMemberNo", params);
    }

    public int pwchkConfirm(EgovMap params) {
        return selectOne("Member.pwchkConfirm", params);
    }
  
   
    public void updateMemberRwPw(EgovMap params) {
        update("Member.updateMemberRwPw", params);
    }

    public String selectMemberEmail(EgovMap params) {
        return selectOne("Member.selectMemberEmail", params);
    }

    public String selectBeforePassword(EgovMap params) {
      return selectOne("Member.selectBeforePassword", params);
    }

    public void updateMemberpwChange(EgovMap params) {
        update("Member.updateMemberpwChange", params);
    }

    public void updateMemberModify(EgovMap params) {
        update("Member.updateMemberModify", params);
    }
    
    public int passwordHistory(EgovMap params) {
      return selectOne("Member.passwordHistory", params);
    }
}
