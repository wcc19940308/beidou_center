package com.ctbt.beidou.base.model;

public class SysRegion {
    private Integer regId;

    private String regName;

    private Integer parentId;

    private Integer level;

    private Integer countryId;
    private Integer publicId;//通播号
    private String regCode;//行政区划代码
    private Integer orderNo;
    
    public Integer getRegId() {
        return regId;
    }

    public void setRegId(Integer regId) {
        this.regId = regId;
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName == null ? null : regName.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getPublicId() {
		return publicId;
	}

	public void setPublicId(Integer publicId) {
		this.publicId = publicId;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}
    
}