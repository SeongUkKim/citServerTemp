package egovframework.sys.member;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;


public interface MemberService {

	int idConfirm(EgovMap params);
	
	int insertMemberJoin(EgovMap params);
	
	void insertAccessLog(EgovMap AccessLog);

	List<EgovMap> selectMemberNo(EgovMap params);

	int pwchkConfirm(EgovMap params);



    void updateMemberRwPw(EgovMap params);

    String selectMemberEmail(EgovMap params);

    String selectBeforePassword(EgovMap params);

    void updateMemberpwChange(EgovMap params);

    void updateMemberModify(EgovMap params);

    int passwordHistory(EgovMap params);
}
