package egovframework.sys.main.mentor;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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


@Service("campaignMentor")
public class CampaignMentorServiceImpl implements CampaignMentorService {

	// TODO mybatis 사용
	@Resource(name = "campaignMentorDAO")
	private CampaignMentorDAO campaignMentorDAO;

	@Override
	public void insertMentorInfo(EgovMap params) {
		
		List<Map<String,Object>> categoryList = new ArrayList<Map<String, Object>>();
		List<String> category = new ArrayList<>();
		
		String selBtn = (String)params.get("category");
		String[] s = selBtn.substring(1, selBtn.length()-1).replace("&quot;", "").split(",");
			
		for(int i=0; i < s.length; i++) {
			Map<String, Object> mapData = new HashMap<String, Object>();
			mapData.put("id", params.get("id"));
			mapData.put("uId", params.get("uId"));
			mapData.put("category", s[i]);
			categoryList.add(i, mapData);
		}
			
		this.campaignMentorDAO.insertMentorInfo(params);
		this.campaignMentorDAO.insertMentorCategory(categoryList);
		this.campaignMentorDAO.updateMentorStatus(params);
	}
	
	@Override
	public void insertMentorFile(List<Map<String, Object>> listFile) {
		this.campaignMentorDAO.insertMentorFile(listFile);
	}
	
	@Override
	public List<EgovMap> selectUserId(EgovMap params) {
		return this.campaignMentorDAO.selectUserId(params);
	}
	
	
}
