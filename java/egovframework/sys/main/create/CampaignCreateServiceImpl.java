package egovframework.sys.main.create;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;

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


@Service("campaignCreate")
public class CampaignCreateServiceImpl implements CampaignCreateService {

	// TODO mybatis 사용
	@Resource(name = "campaignCreateDAO")
	private CampaignCreateDAO campaignCreateDAO;

	@Override
	public void insertCampaign(EgovMap params) {
		
		//category data 가공
	    String selBtn = (String) params.get("selBtn");
	    String[] s = selBtn.substring(1,selBtn.length()-1).replace("&quot;", "").split(",");
	    
	    List<String> category = new ArrayList<>();
	    
	    for(String i : s){
	    	category.add(i);
	    }
	    
	    this.campaignCreateDAO.insertCampainCreate(params);
	    this.campaignCreateDAO.insertCampainCategory(category);
	}
	
	@Override
	public List<EgovMap> selectCampaign(EgovMap params) {
		return this.campaignCreateDAO.selectCampainCreate(params);
	}

	@Override
	public void insertFile(List<Map<String, Object>> listFileInfo) {
		if(listFileInfo.size() != 0) {
			this.campaignCreateDAO.insertFile(listFileInfo);
		}
	}

	@Override
	public void insertCampaignCalender(List<Map<String, String>> list) {
		if(list.size() != 0) {
			this.campaignCreateDAO.insertCampaignCalender(list);
		}
	}

	@Override
	public void insertCampaignImg(List<Map<String, Object>> listImg) {
		if(listImg.size() != 0) {
			this.campaignCreateDAO.insertImg(listImg);
		}
	}

	@Override
	public int campaignCount(EgovMap params) {
		return this.campaignCreateDAO.campaignCount(params);
	}

	@Override
	public List<EgovMap> selectMentorCategory(EgovMap params) {
		return this.campaignCreateDAO.selectMentorCategory(params);
	}
	
	
}
