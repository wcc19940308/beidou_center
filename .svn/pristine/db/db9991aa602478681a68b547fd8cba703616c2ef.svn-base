package com.ctbt.beidou.base.tag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.ctbt.beidou.base.bo.KeyValue;

/**
 * 表单列表构造类。
 * 
 * 
 * @since 1.1.0
 */

public abstract class FormBaseTag extends TagSupport {

	/** id */
	protected String id;

	/** 名称 */
	protected String name;

	/** 编辑选项: true, false */
	protected String disabled;

	/** style样式 */
	protected String style;

	/** style样式class */
	protected String styleClass;

	/** javascript onblur 事件 */
	protected String onblur;

	/** javascript onchange 事件 */
	protected String onchange;

	/** javascript onclick 事件 */
	protected String onclick;

	/** javascript ondblclick 事件 */
	protected String ondblclick;

	/** javascript onfocus 事件 */
	protected String onfocus;

	/** 页面验证扩展 * */
	protected String validator;

	/** readonly, true则为plain text的显示方式，false为一般显示方式 */
	protected String readonly;

	/**
	 * 组装基本属性的语句。
	 * 
	 * 
	 * @return 基本属性的语句
	 */
	protected String makeBaseHtml() {
		StringBuffer aHTML = new StringBuffer();

		// name
		if(StringUtils.isNotEmpty(this.name)){
			aHTML.append(" name=\"").append(this.name).append("\"");
		}

		// style
		if(StringUtils.isNotEmpty(this.style)){
			aHTML.append(" style=\"").append(this.style).append("\"");
		}

		// styleClass
		if(StringUtils.isNotEmpty(this.styleClass)){
			aHTML.append(" class=\"").append(this.styleClass).append("\"");
		}

		// disabled
		if("true".equalsIgnoreCase(this.disabled) || "disabled".equalsIgnoreCase(this.disabled)){
			aHTML.append(" disabled ");
		}else{
			// onfocus
			if(StringUtils.isNotEmpty(this.onfocus)){
				aHTML.append(" onfocus=\"").append(this.onfocus).append("\"");
			}

			// onblur
			if(StringUtils.isNotEmpty(this.onblur)){
				aHTML.append(" onblur=\"").append(this.onblur).append("\"");
			}

			// onchange
			if(StringUtils.isNotEmpty(this.onchange)){
				aHTML.append(" onchange=\"").append(this.onchange).append("\"");
			}

			// onclick
			if(StringUtils.isNotEmpty(this.onclick)){
				aHTML.append(" onclick=\"").append(this.onclick).append("\"");
			}

			// ondbclick
			if(StringUtils.isNotEmpty(this.ondblclick)){
				aHTML.append(" ondblclick=\"").append(this.ondblclick).append("\"");
			}
		}

		return aHTML.toString();
	}

	/**
	 * 把一个对象换成Map以便生成HTML串的时候使用。
	 * 
	 * 
	 * @param object
	 *                待转换的对象
	 * @return 转换后的Map
	 * @throws Exception
	 */
	protected List<String[]> data2List(Object object) throws Exception {
		if(object == null) return new ArrayList<String[]>();

		List<String[]> result = new ArrayList<String[]>();
		if(object instanceof List){
			List list = (List) object;
			if(list.size() > 0){
				Object obj = list.get(0);
				if(obj instanceof KeyValue){
					for(int k = 0; k < list.size(); k++){
						KeyValue kv = (KeyValue) list.get(k);
						result.add(new String[] { kv.getKey(), kv.getValue() });
					}
				}else if(obj instanceof String[]){
					return list;
				}
			}
		}else if(object instanceof Map){
			Map map = (Map) object;
			Iterator iter = map.keySet().iterator();
			while (iter.hasNext()){
				String key = (String) iter.next();
				String value = (String) map.get(key);
				result.add(new String[] { key, value });
			}
		}

		return result;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOnblur() {
		return onblur;
	}

	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getOnfocus() {
		return onfocus;
	}

	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOndblclick() {
		return ondblclick;
	}

	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	public String getValidator() {
		return validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}