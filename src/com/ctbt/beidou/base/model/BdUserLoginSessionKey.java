package com.ctbt.beidou.base.model;

public class BdUserLoginSessionKey {
	private Integer userId;

	private String loginDevice;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginDevice() {
		return loginDevice;
	}

	public void setLoginDevice(String loginDevice) {
		this.loginDevice = loginDevice == null ? null : loginDevice.trim();
	}
}