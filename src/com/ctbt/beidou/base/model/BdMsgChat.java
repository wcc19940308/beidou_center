package com.ctbt.beidou.base.model;

import java.util.Date;

public class BdMsgChat {
    private Integer msgId;

    private Date sendTime;

    private Date recvTime;
    
    private String msgType;

	private String msgTxt;

    private Integer msgFrom;

    private String fromPhone;

    private Integer msgTo;

    private String toPhone;

    private String isRecv;

    private Date recvConfirmTime;

	public Integer getMsgId() {
		return msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getRecvTime() {
		return recvTime;
	}

	public void setRecvTime(Date recvTime) {
		this.recvTime = recvTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgTxt() {
		return msgTxt;
	}

	public void setMsgTxt(String msgTxt) {
		this.msgTxt = msgTxt;
	}

	public Integer getMsgFrom() {
		return msgFrom;
	}

	public void setMsgFrom(Integer msgFrom) {
		this.msgFrom = msgFrom;
	}

	public String getFromPhone() {
		return fromPhone;
	}

	public void setFromPhone(String fromPhone) {
		this.fromPhone = fromPhone;
	}

	public Integer getMsgTo() {
		return msgTo;
	}

	public void setMsgTo(Integer msgTo) {
		this.msgTo = msgTo;
	}

	public String getToPhone() {
		return toPhone;
	}

	public void setToPhone(String toPhone) {
		this.toPhone = toPhone;
	}

	public String getIsRecv() {
		return isRecv;
	}

	public void setIsRecv(String isRecv) {
		this.isRecv = isRecv;
	}

	public Date getRecvConfirmTime() {
		return recvConfirmTime;
	}

	public void setRecvConfirmTime(Date recvConfirmTime) {
		this.recvConfirmTime = recvConfirmTime;
	}

}