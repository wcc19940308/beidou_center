package com.ctbt.beidou.base.model;

import java.util.Date;

public class BdDataPackRecv {
    private Long pkgId;

    private String fromSiteNo;

    private Long fromPkgId;

    private Long msgId;

    private String pkgCmd;

    private String pkgType;

    private String pkgMsg;

    private Integer masterFrom;

    private Integer pkgFrom;

    private String phoneFrom;

    private Integer masterTo;

    private Integer pkgTo;

    private String phoneTo;

    private Date recvTime;

    private Long recvTimestamp;

	public Long getPkgId() {
		return pkgId;
	}

	public void setPkgId(Long pkgId) {
		this.pkgId = pkgId;
	}

	public String getFromSiteNo() {
		return fromSiteNo;
	}

	public void setFromSiteNo(String fromSiteNo) {
		this.fromSiteNo = fromSiteNo;
	}

	public Long getFromPkgId() {
		return fromPkgId;
	}

	public void setFromPkgId(Long fromPkgId) {
		this.fromPkgId = fromPkgId;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getPkgCmd() {
		return pkgCmd;
	}

	public void setPkgCmd(String pkgCmd) {
		this.pkgCmd = pkgCmd;
	}

	public String getPkgType() {
		return pkgType;
	}

	public void setPkgType(String pkgType) {
		this.pkgType = pkgType;
	}

	public String getPkgMsg() {
		return pkgMsg;
	}

	public void setPkgMsg(String pkgMsg) {
		this.pkgMsg = pkgMsg;
	}

	public Integer getMasterFrom() {
		return masterFrom;
	}

	public void setMasterFrom(Integer masterFrom) {
		this.masterFrom = masterFrom;
	}

	public Integer getPkgFrom() {
		return pkgFrom;
	}

	public void setPkgFrom(Integer pkgFrom) {
		this.pkgFrom = pkgFrom;
	}

	public String getPhoneFrom() {
		return phoneFrom;
	}

	public void setPhoneFrom(String phoneFrom) {
		this.phoneFrom = phoneFrom;
	}

	public Integer getMasterTo() {
		return masterTo;
	}

	public void setMasterTo(Integer masterTo) {
		this.masterTo = masterTo;
	}

	public Integer getPkgTo() {
		return pkgTo;
	}

	public void setPkgTo(Integer pkgTo) {
		this.pkgTo = pkgTo;
	}

	public String getPhoneTo() {
		return phoneTo;
	}

	public void setPhoneTo(String phoneTo) {
		this.phoneTo = phoneTo;
	}

	public Date getRecvTime() {
		return recvTime;
	}

	public void setRecvTime(Date recvTime) {
		this.recvTime = recvTime;
	}

	public Long getRecvTimestamp() {
		return recvTimestamp;
	}

	public void setRecvTimestamp(Long recvTimestamp) {
		this.recvTimestamp = recvTimestamp;
	}

}
