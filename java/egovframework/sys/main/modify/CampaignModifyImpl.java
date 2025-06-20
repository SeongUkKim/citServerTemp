package egovframework.sys.main.modify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.sys.main.create.CampaignCreateDAO;

import javax.annotation.Resource;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("campaignModify")
public class CampaignModifyImpl implements CampaignModifyService{
	// TODO mybatis 사용
	@Resource(name = "campaignModifyDAO")
	private CampaignModifyDAO campaignModifyDAO;

	@Override
	public List<EgovMap> selectMentorCategory(EgovMap params) {
		return this.campaignModifyDAO.selectMentorCategory(params);
	}

	@Override
	public List<EgovMap> selectCampaignInfo(EgovMap params) {
		return this.campaignModifyDAO.selectCampaignInfo(params);
	}

	@Override
	public List<EgovMap> selectCampaignCategory(EgovMap params) {
		return this.campaignModifyDAO.selectCampaignCategory(params);
	}

	@Override
	public List<EgovMap> selectCampaignCalender(EgovMap params) {
		return this.campaignModifyDAO.selectCampaignCalender(params);
	}

	@Override
	public List<EgovMap> selectCampaignImg(EgovMap params) {
		return this.campaignModifyDAO.selectCampaignImg(params);
	}

	@Override
	public List<EgovMap> selectCampaignLocation(EgovMap params) {
		return this.campaignModifyDAO.selectCampaignLocation(params);
	}

	@Override
	public List<EgovMap> selectCampaignFile(EgovMap params) {
		return this.campaignModifyDAO.selectCampaignFile(params);
	}

	@Override
	public void insertCampaignImg(List<Map<String, Object>> listFileInfo) {
		this.campaignModifyDAO.insertCampaignImg(listFileInfo);
	}

	@Override
	public void updateCampaignInfo(EgovMap params) {
		this.campaignModifyDAO.updateCampaignInfo(params);
		
		//category data 가공
	    String selBtn = (String) params.get("selBtn");
	    String[] s = selBtn.substring(1,selBtn.length()-1).replace("&quot;", "").split(",");
	    
//	    List<String> category = new ArrayList<>();
	    List<EgovMap> category = new ArrayList<>();
	    
//	    for(String i : s){
//	    	category.add(i);
//	    }
	    
	    for(String i : s){
	    	EgovMap item = new EgovMap();
	    	item.put("cId", params.get("cId"));
	    	item.put("category", i);
	    	category.add(item);
	    }
	    System.out.println("이거슨 카테고리"+category);
	    System.out.println("category: " + category);
	    this.campaignModifyDAO.deleteCampaignCategory(params);
		this.campaignModifyDAO.insertCampainCategory(category);
	}

	@Override
	public void deleteCampaignImg(EgovMap params) {
		this.campaignModifyDAO.deleteCampaignImg(params);
	}

	@Override
	public void deleteCampaignExistFile(EgovMap params) {
		this.campaignModifyDAO.deleteCampaignExistFile(params);
	}

	@Override
	public void insertCampaignFile(List<Map<String, Object>> listFileInfo) {
		this.campaignModifyDAO.insertCampaignFile(listFileInfo);
		
	}

	@Override
	public void insertCampaignCalender(List<Map<String, String>> calenderParams) {
		this.campaignModifyDAO.insertCampaignCalender(calenderParams);
	}

	@Override
	public void deleteCampaignCalender(EgovMap params) {
		this.campaignModifyDAO.deleteCampaignCalender(params);
	}
}
