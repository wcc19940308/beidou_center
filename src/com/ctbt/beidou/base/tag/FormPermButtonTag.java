package com.ctbt.beidou.base.tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

import com.ctbt.beidou.base.LoginSessionManager;
import com.ctbt.beidou.base.model.BdPerm;
import com.ctbt.beidou.base.model.BdUserPermKey;
import com.ctbt.beidou.base.utils.DicUtil;
//每个页面修改查询删除选项
public class FormPermButtonTag extends TagSupport{
	private String value;
	private String ids;
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
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
		List<BdUserPermKey> userRoleList = getRoleMenuList();//该用户拥有的所有权限
		HashMap<String, Object> userRoleMap = new HashMap<>();
		for(BdUserPermKey userRole:userRoleList) {
			userRoleMap.put(userRole.getPermId().toString(), userRole);
			
		}
		List<BdPerm> BdPermList = DicUtil.AllpermList;//数据库中所有菜单项的权限列表
		
		List<Integer> menuIdList = new ArrayList<Integer>();//前端获取到的ctbt需要的权限
		CollectionUtils.collect(new ArrayList(Arrays.asList(ids.split(","))), new Transformer() {
		   @Override
		   public Object transform(Object o) {
		      return Integer.valueOf(o.toString());
		   }
		}, menuIdList);
		
		aHTML.append("toptoolbar = $(\"#toptoolbar\").ligerToolBar({\r\n" + 
				"								items: [");
		for (int i=0;i<menuIdList.size();i++) {
			if (i!=0) {
				aHTML.append("{line: true},");
			}
			BdPerm bdPerm=null;
			
			if (userRoleMap.containsKey(menuIdList.get(i).toString())) {
				for (BdPerm temp : BdPermList) {
					if ((int)menuIdList.get(i)==(int)temp.getPermId()) {
						bdPerm = temp;
						break;
					}
				}
				System.out.println(menuIdList.get(i));
				aHTML.append("{text: '"+bdPerm.getPermName()+"',click: "+bdPerm.getPermScript()+",icon: '"+bdPerm.getPermIcon()+"'},");
				continue;
			}
			
		}
		aHTML.append("]});");
		

		return aHTML.toString();
	}
	private List<BdUserPermKey> getRoleMenuList() {
		HttpSession session =  pageContext.getSession(); 
		HttpSession thisUserSession = (HttpSession)LoginSessionManager.getInstance().sessionMap.get(session.getId());
		List<BdUserPermKey> userRoleList= (List<BdUserPermKey>) thisUserSession.getAttribute("userRolePOJO");
		return userRoleList;

	}
}
