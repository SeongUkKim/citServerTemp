package egovframework.sys.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.sys.cmm.util.MenuInfo;
import egovframework.sys.cmm.util.PagingVO;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository("mainDAO")
public class MainDAO extends EgovAbstractMapper {

	public int countCampaignlistforAUser(String u_id) {
		return selectOne("main.countCampaignlistforAUser", u_id);
	}

	public int countCampaignlistforBUser(String u_id) {
		return selectOne("main.countCampaignlistforBUser", u_id);
	}

	public int countCampaignWaitforA(String u_id) {
		return selectOne("main.countCampaignWaitforA", u_id);
	}

	public int countCampaignCollectforA(String u_id) {

		return selectOne("main.countCampaignCollectforA", u_id);
	}

	public int countCampaigningforA(String u_id) {
		return selectOne("main.countCampaigningforA", u_id);
	}

	public int countCampaignendforA(String u_id) {
		return selectOne("main.countCampaignendforA", u_id);
	}

	public int countCampaignReservingforB(String u_id) {
		return selectOne("main.countCampaignReservingforB", u_id);
	}

	public int countCampaignChecktforB(String u_id) {

		return selectOne("main.countCampaignChecktforB", u_id);
	}

	public int countCampaigningforB(String u_id) {
		return selectOne("main.countCampaigningforB", u_id);
	}

	public int countCampaignendforB(String u_id) {
		return selectOne("main.countCampaignendforB", u_id);
	}


	public List<EgovMap> selectCampaignBoardforAUser(@RequestParam("Paging") PagingVO vo,
			@RequestParam("id") String u_id) {

		int start = vo.getStart();
		int end = vo.getEnd();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("start", start);
		map.put("end", end);
		map.put("u_id", u_id);

		return selectList("main.selectCampaignBoardforAUser", map);
	}

	public List<EgovMap> selectCampaignBoardforBUser(@RequestParam("Paging") PagingVO vo,
			@RequestParam("id") String u_id) {

		int start = vo.getStart();
		int end = vo.getEnd();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("start", start);
		map.put("end", end);
		map.put("u_id", u_id);

		return selectList("main.selectCampaignBoardforBUser", map);
	}

	public List<EgovMap> selectWaitBoardforAUser(@RequestParam("Paging") PagingVO vo, @RequestParam("id") String u_id) {

		int start = vo.getStart();
		int end = vo.getEnd();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("start", start);
		map.put("end", end);
		map.put("u_id", u_id);

		return selectList("main.selectWaitBoardforAUser", map);
	}

	public List<EgovMap> selectCollectBoardforAUser(@RequestParam("Paging") PagingVO vo,
			@RequestParam("id") String u_id) {

		int start = vo.getStart();
		int end = vo.getEnd();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("start", start);
		map.put("end", end);
		map.put("u_id", u_id);

		return selectList("main.selectCollectBoardforAUser", map);
	}

	public List<EgovMap> selectIngBoardforAUser(@RequestParam("Paging") PagingVO vo, @RequestParam("id") String u_id) {

		int start = vo.getStart();
		int end = vo.getEnd();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("start", start);
		map.put("end", end);
		map.put("u_id", u_id);

		return selectList("main.selectIngBoardforAUser", map);
	}

	public List<EgovMap> selectEndBoardforAUser(@RequestParam("Paging") PagingVO vo, @RequestParam("id") String u_id) {

		int start = vo.getStart();
		int end = vo.getEnd();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("start", start);
		map.put("end", end);
		map.put("u_id", u_id);

		return selectList("main.selectEndBoardforAUser", map);
	}

	public List<EgovMap> selectReservingBoardforBUser(@RequestParam("Paging") PagingVO vo,
			@RequestParam("id") String u_id) {

		int start = vo.getStart();
		int end = vo.getEnd();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("start", start);
		map.put("end", end);
		map.put("u_id", u_id);

		return selectList("main.selectReservingBoardforBUser", map);
	}

	public List<EgovMap> selectCheckBoardforBUser(@RequestParam("Paging") PagingVO vo,
			@RequestParam("id") String u_id) {

		int start = vo.getStart();
		int end = vo.getEnd();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("start", start);
		map.put("end", end);
		map.put("u_id", u_id);

		return selectList("main.selectCheckBoardforBUser", map);
	}

	public List<EgovMap> selectIngBoardforBUser(@RequestParam("Paging") PagingVO vo, @RequestParam("id") String u_id) {

		int start = vo.getStart();
		int end = vo.getEnd();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("start", start);
		map.put("end", end);
		map.put("u_id", u_id);

		return selectList("main.selectIngBoardforBUser", map);
	}

	public List<EgovMap> selectEndBoardforBUser(@RequestParam("Paging") PagingVO vo, @RequestParam("id") String u_id) {

		int start = vo.getStart();
		int end = vo.getEnd();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("start", start);
		map.put("end", end);
		map.put("u_id", u_id);

		return selectList("main.selectEndBoardforBUser", map);
	}

	public List<EgovMap> selectMainmenu() {

		return selectList("main.selectMainmenu");
	}

	public List<EgovMap> selectSubmenu() {

		return selectList("main.selectSubmenu");
	}

	public List<EgovMap> selectImg(String c_id) {

		return selectList("main.selectImg");
	}

	public EgovMap selectmainmenuname(String menuname) {

		return selectOne("main.selectmainmenuname", menuname);

	}

	public EgovMap selectsubmenuname(String submenuname) {

		return selectOne("main.selectsubmenuname", submenuname);

	}

	public void insertnewmainmenu(MenuInfo menuinfo) {

		String newname = menuinfo.getNewname();
		String icon = menuinfo.getIcon();
		int id = menuinfo.getId();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("newname", newname);
		map.put("id", id);
		map.put("icon", icon);

		insert("main.insertnewmainmenu", map);

	}

	public void updatemainmenuinsub(MenuInfo menuinfo) {

		String mainmenu = menuinfo.getMainmenu();
		String newname = menuinfo.getNewname();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("editname", mainmenu);
		map.put("newname", newname);

		update("main.updatemainmenuinsub", map);

	}

	public void deletsubmenuinmain(MenuInfo menuinfo) {

		String mainmenu = menuinfo.getMainmenu();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("editname", mainmenu);

		delete("main.deletsubmenuinmain", map);

	}

	public void deletemainmenu(MenuInfo menuinfo) {

		String mainmenu = menuinfo.getMainmenu();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("editname", mainmenu);

		delete("main.deletemainmenu", map);

	}

	public void updatemainmenu(MenuInfo menuinfo) {

		String mainmenu = menuinfo.getMainmenu();
		String newname = menuinfo.getNewname();
		String icon = menuinfo.getIcon();
		int id = menuinfo.getId();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("editname", mainmenu);
		map.put("newname", newname);
		map.put("id", id);
		map.put("icon", icon);

		update("main.updatemainmenu", map);

	}

	public void updatesubmenu(MenuInfo menuinfo) {

		String mainmenu = menuinfo.getMainmenu();
		String submainmenu = menuinfo.getSubmainmenu();
		String newname = menuinfo.getNewname();
		String icon = menuinfo.getIcon();
		int id = menuinfo.getId();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("editname", mainmenu);
		map.put("m_name", submainmenu);
		map.put("newname", newname);
		map.put("id", id);
		map.put("icon", icon);

		update("main.updatesubmenu", map);

	}

	public void deletesubmenu(MenuInfo menuinfo) {

		String submenu = menuinfo.getSubmenu();
		
	
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		System.out.println(submenu);

		map.put("editname", submenu);

		delete("main.deletesubmenu", map);

	}

	public void insertsubmenu(MenuInfo menuinfo) {

		String newname = menuinfo.getNewname();
		String icon = menuinfo.getIcon();
		String submainmenu = menuinfo.getSubmainmenu();
		int id = menuinfo.getId();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("newname", newname);
		map.put("id", id);
		map.put("icon", icon);
		map.put("m_name", submainmenu);

		insert("main.insertsubmenu", map);

	}

	public int selectIdByUid(EgovMap params) {
		
		return selectOne("main.selectIdByUid", params);
	}
	
	public void insertInquireInHeader(EgovMap params) {
		
		insert("main.insertInquireInHeader", params);
	}
	
	public int selectUserAlarmCountByHeader(EgovMap params) {
		return selectOne("main.selectUserAlarmCountByHeader", params);
	}
	
	public int selectUserAlarmCount(EgovMap params) {
		return selectOne("main.selectUserAlarmCount", params);
	}
	
	public List<EgovMap> selectUserAlarmList(EgovMap params){
		
		return selectList("main.selectUserAlarmList", params);
	}
	
	public EgovMap selectUserAlarmBoard(EgovMap params) {
		
		return selectOne("main.selectUserAlarmBoard", params);
	}
	
	public void updateReadByUser(EgovMap params) {
		
		update("main.updateReadByUser", params);
	}
	
	public void updateReadByUserArrow(EgovMap params) {
		update("main.updateReadByUserArrow", params);
	}
	
	public EgovMap selectUserAlarmBoardByArrow(EgovMap params) {
		return selectOne("main.selectUserAlarmBoardByArrow", params);
	}
	
	//사용자 알람 삭제시 status 4
	public void updateAlarmStatus4(EgovMap params) {
		update("main.updateAlarmStatus4", params);
	}
	
}
