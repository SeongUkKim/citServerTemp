package egovframework.sys.member;

import java.util.List;

import egovframework.sys.main.MainService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


@Service("memberService")
public class MemberServiceImpl implements MemberService {

	// TODO mybatis 사용
	@Resource(name = "memberDAO")
	private MemberDAO memberDAO;
	
	@Override
	public int idConfirm(EgovMap params) {
		return this.memberDAO.idConfirm(params);
	}
	
	@Override
	public int insertMemberJoin(EgovMap params) {
		return memberDAO.insertMemberJoin(params);
	}
	
	@Override
	public void insertAccessLog(EgovMap AccessLog) {
		this.memberDAO.insertAccessLog(AccessLog);
	}

    @Override
    public List<EgovMap> selectMemberNo(EgovMap params) {
        return this.memberDAO.selectMemberNo(params);
    }

    @Override
    public int pwchkConfirm(EgovMap params) {
        return this.memberDAO.pwchkConfirm(params);
    }
  
   

    @Override
    public void updateMemberRwPw(EgovMap params) {
        this.memberDAO.updateMemberRwPw(params);
    }

    @Override
    public String selectMemberEmail(EgovMap params) {
        return this.memberDAO.selectMemberEmail(params);
    }

    @Override
    public String selectBeforePassword(EgovMap params) {
        return this.memberDAO.selectBeforePassword(params);
    }

    @Override
    public void updateMemberpwChange(EgovMap params) {
        this.memberDAO.updateMemberpwChange(params);
    }

    @Override
    public void updateMemberModify(EgovMap params) {
      this.memberDAO.updateMemberModify(params);
      
    }

    @Override
    public int passwordHistory(EgovMap params) {
      return this.memberDAO.passwordHistory(params);
    }
}
