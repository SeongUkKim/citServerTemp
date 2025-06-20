package egovframework.sys.sec.user;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface SUserAccessService {
	
	SUserAccessVO getUserInfo(String id) throws Exception;

}