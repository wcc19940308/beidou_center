package com.ctbt.beidou.base.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.commons.lang.StringUtils;

import com.ctbt.beidou.base.utils.StrUtil;

/**
 * 生成勾选框，根据value=1|0, 自动勾或不勾
 */

public class FormCheckBoxTag extends FormBaseTag {

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
		if(StringUtils.isEmpty(this.getName())){
			throw new JspTagException("表单对象名称为空！");
		}

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
     * 构造checkBox的html代码。
     * 
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
		
		aHTML.append("<input type=\"checkbox\" name=\"").append(this.getName() + "\"");

		if(StringUtils.isNotEmpty(this.getId())){
			aHTML.append(" id=\"").append(this.getId()).append("\"");
		}

		//value=1|0
		String value = StringUtils.trimToEmpty(this.getValue());
		if("1".equals(StrUtil.trim(value))){
			aHTML.append(" value=\"").append(value + "\"");
			aHTML.append(" checked=\"checked\"");
		}else{
			value = "0";
			aHTML.append(" value=\"").append(value + "\"");
			//	    aHTML.append(" checked");
		}

//		if("true".equalsIgnoreCase(this.getDisabled())){
//			aHTML.append(" disabled=\"true\"");
//		}
		
		//追加 默认点击事件
		this.setOnclick(StringUtils.trimToEmpty(this.getOnclick())+";this.value=(this.checked ? '1' : '0');");

		aHTML.append(this.makeBaseHtml());
		//增加页面验证扩展
		if(StringUtils.isNotEmpty(this.getValidator())){
			aHTML.append(" " + this.getValidator());
		}

		aHTML.append(" />\n");

		return aHTML.toString();
	}
}