package egovframework.sys.sec.user;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;


@Repository("userAccessDAO")
public class SUserAccessDAO extends EgovAbstractMapper {
	
	protected SUserAccessVO getUserInfo(String param) throws Exception {
		return selectOne("Member.selectMemberLogin", param);
	}
}
