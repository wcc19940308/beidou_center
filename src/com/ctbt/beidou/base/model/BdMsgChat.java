package com.ctbt.beidou.base.model;

import java.util.Date;

public class BdMsgChat {
    private Integer msgId;

    private Date sendTime;

    private Date recvTime;
    
    private String msgType;

    public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	private String msgTxt;

    private String msgFrom;

    private String fromPhone;

    private String msgTo;

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

    public String getMsgTxt() {
        return msgTxt;
    }

    public void setMsgTxt(String msgTxt) {
        this.msgTxt = msgTxt == null ? null : msgTxt.trim();
    }

    public String getMsgFrom() {
        return msgFrom;
    }

    public void setMsgFrom(String msgFrom) {
        this.msgFrom = msgFrom == null ? null : msgFrom.trim();
    }

    public String getFromPhone() {
        return fromPhone;
    }

    public void setFromPhone(String fromPhone) {
        this.fromPhone = fromPhone == null ? null : fromPhone.trim();
    }

    public String getMsgTo() {
        return msgTo;
    }

    public void setMsgTo(String msgTo) {
        this.msgTo = msgTo == null ? null : msgTo.trim();
    }

    public String getToPhone() {
        return toPhone;
    }

    public void setToPhone(String toPhone) {
        this.toPhone = toPhone == null ? null : toPhone.trim();
    }

    public String getIsRecv() {
        return isRecv;
    }

    public void setIsRecv(String isRecv) {
        this.isRecv = isRecv == null ? null : isRecv.trim();
    }

    public Date getRecvConfirmTime() {
        return recvConfirmTime;
    }

    public void setRecvConfirmTime(Date recvConfirmTime) {
        this.recvConfirmTime = recvConfirmTime;
    }
}