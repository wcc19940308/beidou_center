package com.ctbt.beidou.base.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.mysql.cj.Query;

public class PagefootTag extends TagSupport {
	
	private String pageSize;
	
	private String pageNum;
	
	private String recordNum;
	
	private String currentPage;
	/**
	 * 表单元素构造标签库。
	 * 
	 * @throws JspException
	 */
	public int doEndTag() throws JspException {
		try{
			String aHTML = makeHtml();
			// 组装html并输出
			pageContext.getOut().write(aHTML);
		}catch (IOException e){
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}

		return EVAL_PAGE; // continue to eval the later jsp page code.
	}
	
	/**
	 * 构造表单html
	 * 
	 * @return
	 * @throws Exception
	 */
	private String makeHtml() throws Exception {
		StringBuffer html = new StringBuffer();

		HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
		String WebUrl = request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort() +request.getContextPath();
		html.append(" <div class=\"l-panel-bar\"> <div class=\"l-panel-bbar-inner\"> <div class=\"l-bar-group  l-bar-message\">");
		html.append("<span class=\"l-bar-text\">  当前第<span id=\"currentPage\">1</span>页   记录共 <span id=\"recordNum\">0</span> 条</span></div>");
		html.append("<div class=\"l-bar-group l-bar-selectpagesize\"> <select name=\"pageSize\" id =\"pageSize\" onchange=\"pageSizeChange()\">  <option value=\"10\">10</option> <option value=\"20\">20</option> <option value=\"30\">30</option> <option value=\"50\">50</option> <option value=\"100\">100</option> </select></div>");
		html.append("<div class=\"l-bar-group\"> <div class=\"l-bar-button l-bar-btnfirst\">");
		html.append("<span class=\"l-disabled\" onclick=\"queryFirstPage()\"></span></div> ");
		html.append("<div class=\"l-bar-button l-bar-btnprev\"><span class=\"l-disabled\" onclick=\"queryPrePage()\"></span></div> </div>");
		html.append("<div class=\"l-bar-separator\"></div> ");
		html.append("<div class=\"l-bar-group\"><span class=\"pcontrol\"> ");
		html.append("<input type=\"text\" id =\"pageNum\" size=\"4\" value=\""+this.getCurrentPage()+"\" style=\"width:20px\" maxlength=\"100\" onKeyDown=\"queryByPage()\"> / ");
		html.append("<span id=\"sumPageNum\">1</span></span></div> ");
		html.append("<div class=\"l-bar-group\"> <div class=\"l-bar-button l-bar-btnnext\"><span class=\"l-disabled\" onclick=\"queryNextPage()\"></span></div>  ");
		html.append("<div class=\"l-bar-button l-bar-btnlast\"> <span class=\"l-disabled\" onclick=\"queryLastPage()\"></span></div> </div> </div></div>");
		return html.toString();
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(String recordNum) {
		this.recordNum = recordNum;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	
}
