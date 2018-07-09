package com.ctbt.beidou.base.tag;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 表单列表构造类。
 * 
 * @since 1.1.0
 */

public class PageBaseTag extends TagSupport {

	private String type;
	
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
		
		// 组装select
		html.append("<script src=\""+WebUrl+"/js/jquery/jquery-1.9.0.min.js\" type=\"text/javascript\"></script>"+"\r\n");
		html.append("<script src=\""+WebUrl+"/js/ctbt.base.js\" type=\"text/javascript\"></script>"+"\r\n");
		html.append("<link href=\""+WebUrl+"/css/ctbt.base.css\" rel=\"stylesheet\" type=\"text/css\" />"+"\r\n");

		html.append("<script src=\""+WebUrl+"/js/ligerUI/js/core/base.js\" type=\"text/javascript\"></script>"+"\r\n");
		html.append("<script src=\""+WebUrl+"/js/ligerUI/js/ligerui.all.js\" type=\"text/javascript\"></script>"+"\r\n");
		html.append("<link href=\""+WebUrl+"/js/ligerUI/skins/Aqua/css/ligerui-all.css\" rel=\"stylesheet\" type=\"text/css\" />"+"\r\n");
		html.append("<link href=\""+WebUrl+"/js/ligerUI/skins/ligerui-icons.css\" rel=\"stylesheet\" type=\"text/css\">"+"\r\n");
		html.append("<script src=\""+WebUrl+"/js/jquery.cookie.js\"></script>"+"\r\n");
		html.append("<script src=\""+WebUrl+"/js/json2.js\"></script>"+"\r\n");

		return html.toString();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}