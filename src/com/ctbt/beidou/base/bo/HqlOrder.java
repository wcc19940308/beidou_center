package com.ctbt.beidou.base.bo;

/**
 * 
 * @author zhangheng
 *
 */
public class HqlOrder {
	private String property;

	private String order;//DESC,ASC

	public HqlOrder(String property, String order) {
		this.property = property;
		this.order = order;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
}
