package egovframework.sys.cmm.util;

import java.text.MessageFormat;

import egovframework.rte.ptl.mvc.tags.ui.pagination.AbstractPaginationRenderer;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public class PaginationComponent extends AbstractPaginationRenderer {
	
	public PaginationComponent() {
		/*
		 * Page 구역의 기본 형태
		 * ===============================================================================================================================
		<nav class="form-inline mt-4 justify-content-center">
			<ul class="pagination previous">
				<li class="page-item"><a class="page-link" href="#"><i class="icon-arrow-back"></i></a></li>
				<li class="page-item"><a class="page-link" href="#"><i class="icon-arrow-caret-left"></i></a></li>
			</ul>
			<ul class="pagination">
				<li class="page-item active"><a class="page-link" href="#">1</a></li>
				<li class="page-item"><a class="page-link" href="#">2</a></li>
				<li class="page-item"><a class="page-link" href="#">3</a></li>
				<li class="page-item"><a class="page-link" href="#">4</a></li>
				<li class="page-item"><a class="page-link" href="#">5</a></li>
				<li class="page-item"><a class="page-link" href="#">6</a></li>
				<li class="page-item"><a class="page-link" href="#">7</a></li>
				<li class="page-item"><a class="page-link" href="#">8</a></li>
				<li class="page-item"><a class="page-link" href="#">9</a></li>
				<li class="page-item"><a class="page-link" href="#">10</a></li>
			</ul>
			<ul class="pagination next">
				<li class="page-item"><a class="page-link" href="#"><i class="icon-arrow-caret-right"></i></a></li>
				<li class="page-item"><a class="page-link" href="#"><i class="icon-arrow-forward"></i></a></li>
			</ul>
		</nav>
		* ===============================================================================================================================
		*/
		
		
		firstPageLabel = "<ul class=\"pagination previous\"><li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick=\"{0}({1}); return false;\"><i class=\"icon-arrow-back\"></i></a></li>";
		previousPageLabel = "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick=\"{0}({1}); return false;\"><i class=\"icon-arrow-caret-left\"></i></a></li></ul><ul class=\"pagination\">";
		
		currentPageLabel = "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">{0}</a></li>";
		otherPageLabel = "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a></li>";
		
		nextPageLabel = "</ul><ul class=\"pagination previous\"><li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick=\"{0}({1}); return false;\"><i class=\"icon-arrow-caret-right\"></i></a></li>";
		lastPageLabel = "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick=\"{0}({1}); return false;\"><i class=\"icon-arrow-forward\"></i></a></li></ul>";
	}
	
	/**
	 * 처음으로, 이전, 다음, 마지막으로를 pageSize와 totalPageCount 와는
	 * 상관없이 반드시 붙여준다.
	 * 이유는 퍼블리싱 되어 온 페이지 디자인의 반복되는 page에 ul 태그를 붙일 수가 없어 고정적인 요소가 필요함.
	 */
	@Override
	public String renderPagination(PaginationInfo paginationInfo, String jsFunction) {
		
		StringBuffer strBuff = new StringBuffer();

		int firstPageNo = paginationInfo.getFirstPageNo();
		int firstPageNoOnPageList = paginationInfo.getFirstPageNoOnPageList();
		int totalPageCount = paginationInfo.getTotalPageCount();
		int pageSize = paginationInfo.getPageSize();
		int lastPageNoOnPageList = paginationInfo.getLastPageNoOnPageList();
		int currentPageNo = paginationInfo.getCurrentPageNo();
		int lastPageNo = paginationInfo.getLastPageNo();
		
		if (firstPageNoOnPageList > pageSize) {
			strBuff.append(MessageFormat.format(firstPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNo) }));
			strBuff.append(MessageFormat.format(previousPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNoOnPageList - 1) }));
		} else {
			strBuff.append(MessageFormat.format(firstPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNo) }));
			strBuff.append(MessageFormat.format(previousPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNo) }));
		}
		
		for (int i = firstPageNoOnPageList; i <= lastPageNoOnPageList; i++) {
			if (i == currentPageNo) {
				strBuff.append(MessageFormat.format(currentPageLabel, new Object[] { Integer.toString(i) }));
			} else {
				strBuff.append(MessageFormat.format(otherPageLabel, new Object[] { jsFunction, Integer.toString(i), Integer.toString(i) }));
			}
		}
		
		if (lastPageNoOnPageList < totalPageCount) {
			strBuff.append(MessageFormat.format(nextPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNoOnPageList + pageSize) }));
			strBuff.append(MessageFormat.format(lastPageLabel, new Object[] { jsFunction, Integer.toString(lastPageNo) }));
		} else {
			strBuff.append(MessageFormat.format(nextPageLabel, new Object[] { jsFunction, Integer.toString(lastPageNo) }));
			strBuff.append(MessageFormat.format(lastPageLabel, new Object[] { jsFunction, Integer.toString(lastPageNo) }));
		}
		
		return strBuff.toString();
	}
}
