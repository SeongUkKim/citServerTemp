package egovframework.sys.main;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.sys.cmm.util.MenuInfo;
import egovframework.sys.cmm.util.PagingVO;


public interface MainService {
		
	int countCampaignlistforAUser(String u_id);

	int countCampaignlistforBUser(String u_id);

	int countCampaignWaitforA(String u_id);

	int countCampaignCollectforA(String u_id);

	int countCampaigningforA(String u_id);
	
	int countCampaignendforA(String u_id);
	
	int countCampaignReservingforB(String u_id);

	int countCampaignChecktforB(String u_id);

	int countCampaigningforB(String u_id);
	
	int countCampaignendforB(String u_id);
	
	
	
	List<EgovMap> selectCampaignBoardforAUser(PagingVO vo, String u_id);
	
	List<EgovMap> selectCampaignBoardforBUser(PagingVO vo, String u_id);
	

	List<EgovMap> selectWaitBoardforAUser(PagingVO vo, String u_id);

	List<EgovMap> selectCollectBoardforAUser(PagingVO vo, String u_id);
	
	List<EgovMap> selectIngBoardforAUser(PagingVO vo, String u_id);
	
	List<EgovMap> selectEndBoardforAUser(PagingVO vo, String u_id);

	
	List<EgovMap> selectReservingBoardforBUser(PagingVO vo, String u_id);

    List<EgovMap> selectCheckBoardforBUser(PagingVO vo, String u_id);
    
    List<EgovMap> selectIngBoardforBUser(PagingVO vo, String u_id);
    
    List<EgovMap> selectEndBoardforBUser(PagingVO vo, String u_id);
    
    List<EgovMap> selectMainmenu();
    
    List<EgovMap> selectSubmenu();
    
    List<EgovMap> selectImg(String c_id);
    
    void insertnewmainmenu(MenuInfo menuinfo);
    
    void updatemainmenuinsub(MenuInfo menuinfo);
    
    void deletsubmenuinmain(MenuInfo menuinfo);

    void deletemainmenu(MenuInfo menuinfo);

    void updatemainmenu(MenuInfo menuinfo);
    
    EgovMap selectmainmenuname(String menuname);
    
    EgovMap selectsubmenuname(String submenuname);
    
    void insertsubmenu(MenuInfo menuinfo);
    
    void updatesubmenu(MenuInfo menuinfo);
    
    void deletesubmenu(MenuInfo menuinfo);

    //문의하기
    int selectIdByUid(EgovMap params);

    void insertInquireInHeader(EgovMap params);
    
    int selectUserAlarmCountByHeader(EgovMap params);

    int selectUserAlarmCount(EgovMap params);
    
    List<EgovMap> selectUserAlarmList(EgovMap params);
    
    EgovMap selectUserAlarmBoard(EgovMap params);
    
    void updateReadByUser(EgovMap params);
    
    void updateReadByUserArrow(EgovMap params);
    
    EgovMap selectUserAlarmBoardByArrow(EgovMap params);
    
    void updateAlarmStatus4(EgovMap params);
}
