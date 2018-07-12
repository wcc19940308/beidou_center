package com.ctbt.beidou.base.model;

import java.util.Date;

public class BdAdvert {
    private Integer advId;

    private String advTitle;

    private String advType;

    private String advText;

    private Date advStart;

    private Date advEnd;

    private Integer orderNo;
    


	private Integer advTime;

    
    private String validity;

    private String advBase64;

    
    public Integer getAdvTime() {
		return advTime;
	}

	public void setAdvTime(Integer advTime) {
		this.advTime = advTime;
	}
    public Integer getAdvId() {
        return advId;
    }

    public void setAdvId(Integer advId) {
        this.advId = advId;
    }

    public String getAdvTitle() {
        return advTitle;
    }

    public void setAdvTitle(String advTitle) {
        this.advTitle = advTitle == null ? null : advTitle.trim();
    }

    public String getAdvType() {
        return advType;
    }

    public void setAdvType(String advType) {
        this.advType = advType == null ? null : advType.trim();
    }

    public String getAdvText() {
        return advText;
    }

    public void setAdvText(String advText) {
        this.advText = advText == null ? null : advText.trim();
    }

    public Date getAdvStart() {
        return advStart;
    }

    public void setAdvStart(Date advStart) {
        this.advStart = advStart;
    }

    public Date getAdvEnd() {
        return advEnd;
    }

    public void setAdvEnd(Date advEnd) {
        this.advEnd = advEnd;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity == null ? null : validity.trim();
    }

    public String getAdvBase64() {
        return advBase64;
    }

    public void setAdvBase64(String advBase64) {
        this.advBase64 = advBase64 == null ? null : advBase64.trim();
    }
}