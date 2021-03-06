package com.ctbt.beidou.base.model;

public class BdDevice  {
	
	private Integer msid;
	
	private String siteNo;
	
	private Integer masterCardNo;
	
	private Integer slaveCardNo;
	
    private String masterName;

    private String masterSerialNo;

    private String slaveSerialNo;
    
    private Integer publicId;

	public Integer getPublicId() {
		return publicId;
	}

	public void setPublicId(Integer publicId) {
		this.publicId = publicId;
	}

	public String getSiteNo() {
		return siteNo;
	}

	public void setSiteNo(String siteNo) {
		this.siteNo = siteNo;
	}



	public Integer getMsid() {
		return msid;
	}

	public void setMsid(Integer msid) {
		this.msid = msid;
	}




    public Integer getMasterCardNo() {
		return masterCardNo;
	}

	public void setMasterCardNo(Integer masterCardNo) {
		this.masterCardNo = masterCardNo;
	}

	public Integer getSlaveCardNo() {
		return slaveCardNo;
	}

	public void setSlaveCardNo(Integer slaveCardNo) {
		this.slaveCardNo = slaveCardNo;
	}

	public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName == null ? null : masterName.trim();
    }

    public String getMasterSerialNo() {
        return masterSerialNo;
    }

    public void setMasterSerialNo(String masterSerialNo) {
        this.masterSerialNo = masterSerialNo == null ? null : masterSerialNo.trim();
    }

    public String getSlaveSerialNo() {
        return slaveSerialNo;
    }

    public void setSlaveSerialNo(String slaveSerialNo) {
        this.slaveSerialNo = slaveSerialNo == null ? null : slaveSerialNo.trim();
    }
}