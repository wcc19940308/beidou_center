package com.ctbt.beidou.base.tag;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.commons.lang.StringUtils;

/**
 * 表单列表构造类。
 * 
 * @since 1.1.0
 */

public class FormSelectTag extends FormBaseTag {

	/** 数据存储对象：Map或List */
	private Object data;

	/** 默认值 */
	private String defaultValue;

	/** 允许空项: true, false */
	private String emptyOption;

	/** 多选选项: true, false */
	private String multiple;

	/** 列表显示条数 */
	private String size;

	/** 列表中的id属性读取方法 */
	private String getId;

	/** 列表中的value属性读取方法 */
	private String getValue;

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
		}catch (IOException e){
			e.printStackTrace();
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
			throw new JspTagException("列表数据为空！");
		}
	}

	/**
	 * 构造表单html。
	 * 
	 * @return
	 * @throws Exception
	 */
	private String makeHtml() throws Exception {
		StringBuffer html = new StringBuffer();

		// 组装select
		html.append("<select ").append(this.makeBaseHtml().toString());

		if(StringUtils.isNotEmpty(this.getId())){
			html.append(" id=\"").append(this.getId()).append("\"");
		}

		// multiple
		if("true".equals(this.getMultiple())){
			html.append(" multiple");
		}

		// size
		if(StringUtils.isNotEmpty(this.getSize())){
			html.append(" size=\"").append(this.getSize()).append("\"");
		}

		// 增加页面验证扩展
		if(StringUtils.isNotEmpty(this.getValidator())){
			html.append(" " + this.getValidator());
		}

		html.append(">\n ");

		if(null != this.getEmptyOption()){
			html.append(" <option value=\"\">").append(StringUtils.trimToEmpty(this.getEmptyOption())).append("</option>\n");
		}

		String key = "";
		String value = "";
		List<String[]> dataList = data2List(this.getData());
		for(int k = 0; k < dataList.size(); k++){
			String[] kv = dataList.get(k);
			key = (String) kv[0];
			value = (String) kv[1];

			if(key.equalsIgnoreCase(this.getDefaultValue())){
				html.append(" <option value=\"" + key + "\" selected>" + value + "</option>\n");
			}else{
				html.append(" <option value=\"" + key + "\">" + value + "</option>\n");
			}
		}

		html.append(" </select>\n");

		return html.toString();
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getEmptyOption() {
		return emptyOption;
	}

	public void setEmptyOption(String emptyOption) {
		this.emptyOption = emptyOption;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getGetId() {
		return getId;
	}

	public void setGetId(String getId) {
		this.getId = getId;
	}

	public String getGetValue() {
		return getValue;
	}

	public void setGetValue(String getValue) {
		this.getValue = getValue;
	}

}