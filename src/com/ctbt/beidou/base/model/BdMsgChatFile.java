package com.ctbt.beidou.base.model;

public class BdMsgChatFile {
    private Integer msgId;

    private String msgType;

    private String msgBase64;

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType == null ? null : msgType.trim();
    }

    public String getMsgBase64() {
        return msgBase64;
    }

    public void setMsgBase64(String msgBase64) {
        this.msgBase64 = msgBase64 == null ? null : msgBase64.trim();
    }
}