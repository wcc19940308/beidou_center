package com.ctbt.beidou.base.tag;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.commons.lang.StringUtils;

/**
 * 生成一排单选按钮
 */

public class FormRadiosTag extends FormBaseTag {

	/** Map 或者 List<KeyValue> */
	private Object data;

	/** 默认值 */
	private String defaultValue;

	/**
	 * 表单元素构造标签库。
	 * 
	 * @throws JspException
	 */
	public int doEndTag() throws JspException {
		// 进行基本校验
		checkAttributes();

		try{
			String aHTML = makeHtml();

			// 组装html并输出

			pageContext.getOut().write(aHTML);
		}catch (Exception e){
			e.printStackTrace();
		}

		return EVAL_PAGE; // continue to eval the later jsp page code.
	}

	/**
	 * 进行基本校验。
	 * 
	 * @throws JspException
	 */
	private void checkAttributes() throws JspException {
		// 校验对象名称
		if(StringUtils.isEmpty(this.getName())){
			throw new JspTagException("表单对象名称为空！");
		}

		// 校验数据存储对象
		if(data == null){
			throw new JspTagException("数据对象为空！");
		}

		if(data instanceof Map){
			//
		}else if(data instanceof List){
			//
		}else{
			throw new JspTagException("数据对象类型不合法，请使用Map！");
		}
	}

	/**
	 * 构造多个radio的html代码。
	 * @return
	 * @throws Exception
	 */
	private String makeHtml() throws Exception {
		StringBuffer aHTML = new StringBuffer();
		
		this.style = (this.style == null) ? "" : this.style.trim();
		if(this.style.indexOf("margin") == -1){
			this.style += "margin:0px;";
		}
		if(this.style.indexOf("padding") == -1){
			this.style += "padding:0px;";
		}
		if(this.style.indexOf("vertical-align") == -1){
			this.style += "vertical-align:middle;";
		}
		
		// 组装radios
		String key = "";
		String value = "";
		List<String[]> dataList = data2List(this.getData());
		for(int k = 0; k < dataList.size(); k++){
			String[] kv = dataList.get(k);
			key = (String) kv[0];
			value = (String) kv[1];
			String id = this.getId() + key;

			aHTML.append("<input type=\"radio\" ");
			aHTML.append("id=\"" + id + "\" ");
			aHTML.append("value=\"" + key + "\" ");

			if(key.equalsIgnoreCase(this.getDefaultValue())){
				aHTML.append("checked=\"checked\" ");
			}

			aHTML.append(this.makeBaseHtml());
			//增加页面验证扩展
			if(StringUtils.isNotEmpty(this.getValidator())){
				aHTML.append(" " + this.getValidator());
			}

			aHTML.append("/><label for=\"" + id + "\" style='vertical-align:middle;'>" + value + "</label>\n");
		}

		return aHTML.toString();
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}