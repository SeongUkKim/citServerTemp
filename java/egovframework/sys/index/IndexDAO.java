package egovframework.sys.index;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import org.springframework.stereotype.Repository;


@Repository("indexDAO")
public class IndexDAO extends EgovAbstractMapper {

	void insertInquireIndex(EgovMap params) {
		
		
		insert("Index.insertInquireIndex", params);
	}
	
}
