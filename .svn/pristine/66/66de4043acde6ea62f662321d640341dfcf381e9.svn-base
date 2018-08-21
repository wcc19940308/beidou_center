package com.ctbt.beidou.base.tag;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequest;

import com.ctbt.beidou.base.LoginSessionManager;
import com.ctbt.beidou.base.model.BdPerm;
import com.ctbt.beidou.base.utils.DicUtil;
import com.ctbt.beidou.base.utils.StrUtil;

public class FormMenuTag extends TagSupport{

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
     * 表单元素构造标签库。
     * 
     * @throws JspException
     */
	public int doEndTag() throws JspException {
		// 校验对象名称

		try{
			String html = makeHtml();

			// 组装html并输出
			pageContext.getOut().write(html);
		}catch (Exception e){
			e.printStackTrace();
		}

		return EVAL_PAGE; // continue to eval the later jsp page code.
	}

	/**
     * 构造menu的html代码。
     * 
     * @return
     * @throws Exception
     */
	private String makeHtml() throws Exception {
		StringBuffer aHTML = new StringBuffer();
		HttpServletRequest httpServletRequest = (HttpServletRequest) pageContext.getRequest();
		String WebUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort()
		+ httpServletRequest.getContextPath();
		List<Map<String, Object>> userRoleList = getRoleMenuList();//该用户拥有的所有权限
		List<BdPerm> BdPermList = DicUtil.permList;//数据库中所有菜单项的权限列表
		ServletRequest request = pageContext.getRequest(); 
		for (BdPerm bdPerm : BdPermList) {
			for (Map bdPermInMap : userRoleList) {
				if (bdPerm.getPermId()==bdPermInMap.get("perm_id")) { 
					aHTML.append("<div style=\" height:20px;\"></div>\r\n" + 
							"	            <div style=\"text-align: center;\">\r\n" + 
							"	            	<img alt=\"\" src=\""+WebUrl+ "/images/icons/"+bdPerm.getPermIcon()+"\" onclick=\"javascript:f_addTab('"+bdPerm.getPermId()+"','"+bdPerm.getPermName()+"','"+WebUrl+bdPerm.getPermUrl()+"')\" width=\"80\" height=\"80\" style=\"cursor: pointer;\">\r\n" + 
							"	            	<div>"+bdPerm.getPermName()+"</div>\r\n" + 
							"	            </div>");
					continue;
				}
			}
		}

		return aHTML.toString();
	}
	private List<Map<String, Object>> getRoleMenuList() {
			HttpSession session =  pageContext.getSession(); 
		
//		System.out.println(WebUrl+"-----------------------------");
		System.out.println(session.getId());
		HttpSession thisUserSession = (HttpSession)LoginSessionManager.getInstance().sessionMap.get(session.getId());
		List<Map<String, Object>> userRoleList= (List<Map<String, Object>>) thisUserSession.getAttribute("userRole");
		return userRoleList;

	}
}
