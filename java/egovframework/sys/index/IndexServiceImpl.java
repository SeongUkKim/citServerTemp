package egovframework.sys.index;

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


@Service("indexService")
public class IndexServiceImpl implements IndexService {

	// TODO mybatis 사용
	@Resource(name = "indexDAO")
	private IndexDAO indexDAO;

	@Override
	public void insertInquireIndex(EgovMap params) {
		this.indexDAO.insertInquireIndex(params);
		
	}

	
	
}
