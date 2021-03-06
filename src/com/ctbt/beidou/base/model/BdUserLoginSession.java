package com.ctbt.beidou.base.model;

import java.util.Date;

public class BdUserLoginSession extends BdUserLoginSessionKey {
	private String netType;

	private String deviceId;

	private Date loginTime;
	
	private String sessionId;

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType == null ? null : netType.trim();
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId == null ? null : deviceId.trim();
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}