package egovframework.sys.sec.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;


@Service("userAccessService")
public class SUserAccessServiceImpl extends EgovAbstractServiceImpl implements SUserAccessService {
	
	@Resource(name = "userAccessDAO")
	private SUserAccessDAO DAO;
	
	@Override
	public SUserAccessVO getUserInfo(String param) throws Exception {
		return this.DAO.getUserInfo(param);
	}
}
