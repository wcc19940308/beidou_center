package com.ctbt.beidou.base.bo;

/**
 * 返回结果对象的封装
 * 
 * @author zhangheng
 * 
 */
public class ReturnVo {

	private String flag;//常用1=true,0=false,其他可自定义

	private String message;

	private Object data;

	public ReturnVo(String flag) {
		this.flag = flag;
	}

	public ReturnVo(String flag, String message) {
		this.flag = flag;
		this.message = message;
	}

	public ReturnVo(String flag, String message, Object data) {
		this.flag = flag;
		this.message = message;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
