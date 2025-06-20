package egovframework.sys.main.mentor;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;


public interface CampaignMentorService {
	
	void insertMentorInfo(EgovMap params);
	
	void insertMentorFile(List<Map<String,Object>> listFile);
	
	public List<EgovMap> selectUserId(EgovMap params);
}
