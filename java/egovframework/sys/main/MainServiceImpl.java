package egovframework.sys.main;

import java.util.List;

import egovframework.sys.cmm.util.MenuInfo;
import egovframework.sys.cmm.util.PagingVO;
import egovframework.sys.main.MainService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


@Service("mainService")
public class MainServiceImpl implements MainService {

	// TODO mybatis �ъ��
	@Resource(name = "mainDAO")
	private MainDAO mainDAO;
	
	@Override
	public int countCampaignlistforAUser(String u_id) {
		// TODO Auto-generated method stub
		return this.mainDAO.countCampaignlistforAUser(u_id);
	}
	
	@Override
	public int countCampaignlistforBUser(String u_id) {
		// TODO Auto-generated method stub
		return this.mainDAO.countCampaignlistforBUser(u_id);
	}

	@Override
	public int countCampaignWaitforA(String u_id) {
		// TODO Auto-generated method stub
		return this.mainDAO.countCampaignWaitforA(u_id);
	}

	@Override
	public int countCampaignCollectforA(String u_id) {
		// TODO Auto-generated method stub

		return this.mainDAO.countCampaignCollectforA(u_id);
	}

	@Override
	public int countCampaigningforA(String u_id) {
		// TODO Auto-generated method stub
		return this.mainDAO.countCampaigningforA(u_id);
	}

	@Override
	public int countCampaignendforA(String u_id) {
		// TODO Auto-generated method stub
		return this.mainDAO.countCampaignendforA(u_id);
	}
	
	@Override
	public int countCampaignReservingforB(String u_id) {
		// TODO Auto-generated method stub
		return this.mainDAO.countCampaignReservingforB(u_id);
	}

	@Override
	public int countCampaignChecktforB(String u_id) {
		// TODO Auto-generated method stub

		return this.mainDAO.countCampaignChecktforB(u_id);
	}

	@Override
	public int countCampaigningforB(String u_id) {
		// TODO Auto-generated method stub
		return this.mainDAO.countCampaigningforB(u_id);
	}

	@Override
	public int countCampaignendforB(String u_id) {
		// TODO Auto-generated method stub
		return this.mainDAO.countCampaignendforB(u_id);
	}


	@Override
	public List<EgovMap> selectCampaignBoardforAUser(PagingVO vo, String u_id) {
		
	

		
		return this.mainDAO.selectCampaignBoardforAUser(vo,u_id);
	}
	
	@Override
	public List<EgovMap> selectCampaignBoardforBUser(PagingVO vo, String u_id) {
		
		
		return this.mainDAO.selectCampaignBoardforBUser(vo,u_id);
	}

	
	@Override
	public List<EgovMap> selectWaitBoardforAUser(PagingVO vo, String u_id){
		
	

		
		return this.mainDAO.selectWaitBoardforAUser(vo, u_id);
	}
	
	@Override
	public List<EgovMap> selectCollectBoardforAUser(PagingVO vo, String u_id) {
		
	
		
		return this.mainDAO.selectCollectBoardforAUser(vo, u_id);
	}
	
	@Override
	public List<EgovMap> selectIngBoardforAUser(PagingVO vo, String u_id) {
		
	
		
		return this.mainDAO.selectIngBoardforAUser(vo, u_id);
	}
	
	@Override
	public List<EgovMap> selectEndBoardforAUser(PagingVO vo, String u_id) {
		
	
		
		return this.mainDAO.selectEndBoardforAUser(vo, u_id);
	}
	
	@Override
    public List<EgovMap> selectReservingBoardforBUser(PagingVO vo, String u_id) {
        
     
        return this.mainDAO.selectReservingBoardforBUser(vo, u_id);
    }
    
    @Override
    public List<EgovMap> selectCheckBoardforBUser(PagingVO vo, String u_id) {
        
       
        return this.mainDAO.selectCheckBoardforBUser(vo, u_id);
    }
    
    @Override
    public List<EgovMap> selectIngBoardforBUser(PagingVO vo, String u_id) {
        
     
        
        return this.mainDAO.selectIngBoardforBUser(vo, u_id);
    }
    
    @Override
    public List<EgovMap> selectEndBoardforBUser(PagingVO vo, String u_id) {
        
     
        
        return this.mainDAO.selectEndBoardforBUser(vo, u_id);
    }

	@Override
	public List<EgovMap> selectMainmenu() {
		return this.mainDAO.selectMainmenu();
	}

	@Override
	public List<EgovMap> selectSubmenu() {
		return this.mainDAO.selectSubmenu();
	}

	@Override
	public List<EgovMap> selectImg(String c_id) {
		return this.mainDAO.selectImg(c_id);
	}

	@Override
	public void updatemainmenu(MenuInfo menuinfo) {
		
		this.mainDAO.updatemainmenu(menuinfo);
		
	}
	

	@Override
	public EgovMap selectmainmenuname(String menuname) {
		// TODO Auto-generated method stub
		return this.mainDAO.selectmainmenuname(menuname);
	}

	@Override
	public void insertnewmainmenu(MenuInfo menuinfo) {
		this.mainDAO.insertnewmainmenu(menuinfo);
		
	}

	@Override
	public void updatemainmenuinsub(MenuInfo menuinfo) {
		this.mainDAO.updatemainmenuinsub(menuinfo);
	}

	@Override
	public void deletemainmenu(MenuInfo menuinfo) {
		this.mainDAO.deletemainmenu(menuinfo);
		
	}

	@Override
	public void deletsubmenuinmain(MenuInfo menuinfo) {
		this.mainDAO.deletsubmenuinmain(menuinfo);
	}

	@Override
	public EgovMap selectsubmenuname(String submenuname) {
		return this.mainDAO.selectsubmenuname(submenuname);
	}

	@Override
	public void insertsubmenu(MenuInfo menuinfo) {
		this.mainDAO.insertsubmenu(menuinfo);
		
	}

	@Override
	public void updatesubmenu(MenuInfo menuinfo) {
		this.mainDAO.updatesubmenu(menuinfo);
		
	}

	@Override
	public void deletesubmenu(MenuInfo menuinfo) {
		this.mainDAO.deletesubmenu(menuinfo);
		
	}

	@Override
	public int selectIdByUid(EgovMap params) {
		System.out.println(params);
		return this.mainDAO.selectIdByUid(params);
	}

	@Override
	public void insertInquireInHeader(EgovMap params) {
		this.mainDAO.insertInquireInHeader(params);
	}
	
	@Override
	public int selectUserAlarmCountByHeader(EgovMap params) {
		return this.mainDAO.selectUserAlarmCountByHeader(params);
	}

	@Override
	public int selectUserAlarmCount(EgovMap params) {
		return this.mainDAO.selectUserAlarmCount(params);
	}
	
	@Override
	public List<EgovMap> selectUserAlarmList(EgovMap params) {
		return this.mainDAO.selectUserAlarmList(params);
	}

	@Override
	public EgovMap selectUserAlarmBoard(EgovMap params) {
		return this.mainDAO.selectUserAlarmBoard(params);
	}
	
	@Override
	public void updateReadByUser(EgovMap params) {
		this.mainDAO.updateReadByUser(params);	
	}
	
	@Override
	public void updateReadByUserArrow(EgovMap params) {
		this.mainDAO.updateReadByUserArrow(params);
	}

	
	@Override
	public EgovMap selectUserAlarmBoardByArrow(EgovMap params) {
		return this.mainDAO.selectUserAlarmBoardByArrow(params);
	}

	@Override
	public void updateAlarmStatus4(EgovMap params) {
		this.mainDAO.updateAlarmStatus4(params);
		
	}




	

}
