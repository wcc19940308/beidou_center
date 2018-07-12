package com.ctbt.beidou.base.model;

public class BdShip {
    private Integer shipId;

    private String shipNo;

    private String shipName;

    private String shipOwner;

    private String shipOwnerSfz;

    private String shipDesc;

    private Integer orgId;

    private String cardNo1;

    private String serialNo1;

    private String cardNo2;

    private String serialNo2;

    private String mmsi;

    private Float shipLength;

    private Float shipWidth;

    private Float shipWater;

    private String shipType;

    private String shipTypeNew;

    private String shipOwnerTel;

    private Integer shipTons;

    private Integer country;
    private String countryName;

    private Integer province;
    private String provinceName;
    
    private Integer city;
    private String cityName;
    
    private Integer cityArea;
    private String cityAreaName;
    
    private Integer town;
    private String townName;
    
    private Integer village;
    private String villageName;
    
    public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityAreaName() {
		return cityAreaName;
	}

	public void setCityAreaName(String cityAreaName) {
		this.cityAreaName = cityAreaName;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	private String radioCallNo;

    private String typeOther;

    private String validity;

    public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Integer getShipId() {
        return shipId;
    }

    public void setShipId(Integer shipId) {
        this.shipId = shipId;
    }

    public String getShipNo() {
        return shipNo;
    }

    public void setShipNo(String shipNo) {
        this.shipNo = shipNo == null ? null : shipNo.trim();
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName == null ? null : shipName.trim();
    }

    public String getShipOwner() {
        return shipOwner;
    }

    public void setShipOwner(String shipOwner) {
        this.shipOwner = shipOwner == null ? null : shipOwner.trim();
    }

    public String getShipOwnerSfz() {
        return shipOwnerSfz;
    }

    public void setShipOwnerSfz(String shipOwnerSfz) {
        this.shipOwnerSfz = shipOwnerSfz == null ? null : shipOwnerSfz.trim();
    }

    public String getShipDesc() {
        return shipDesc;
    }

    public void setShipDesc(String shipDesc) {
        this.shipDesc = shipDesc == null ? null : shipDesc.trim();
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getCardNo1() {
        return cardNo1;
    }

    public void setCardNo1(String cardNo1) {
        this.cardNo1 = cardNo1 == null ? null : cardNo1.trim();
    }

    public String getSerialNo1() {
        return serialNo1;
    }

    public void setSerialNo1(String serialNo1) {
        this.serialNo1 = serialNo1 == null ? null : serialNo1.trim();
    }

    public String getCardNo2() {
        return cardNo2;
    }

    public void setCardNo2(String cardNo2) {
        this.cardNo2 = cardNo2 == null ? null : cardNo2.trim();
    }

    public String getSerialNo2() {
        return serialNo2;
    }

    public void setSerialNo2(String serialNo2) {
        this.serialNo2 = serialNo2 == null ? null : serialNo2.trim();
    }

    public String getMmsi() {
        return mmsi;
    }

    public void setMmsi(String mmsi) {
        this.mmsi = mmsi == null ? null : mmsi.trim();
    }

    public Float getShipLength() {
        return shipLength;
    }

    public void setShipLength(Float shipLength) {
        this.shipLength = shipLength;
    }

    public Float getShipWidth() {
        return shipWidth;
    }

    public void setShipWidth(Float shipWidth) {
        this.shipWidth = shipWidth;
    }

    public Float getShipWater() {
        return shipWater;
    }

    public void setShipWater(Float shipWater) {
        this.shipWater = shipWater;
    }

    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType == null ? null : shipType.trim();
    }

    public String getShipTypeNew() {
        return shipTypeNew;
    }

    public void setShipTypeNew(String shipTypeNew) {
        this.shipTypeNew = shipTypeNew == null ? null : shipTypeNew.trim();
    }

    public String getShipOwnerTel() {
        return shipOwnerTel;
    }

    public void setShipOwnerTel(String shipOwnerTel) {
        this.shipOwnerTel = shipOwnerTel == null ? null : shipOwnerTel.trim();
    }

    public Integer getShipTons() {
        return shipTons;
    }

    public void setShipTons(Integer shipTons) {
        this.shipTons = shipTons;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getCityArea() {
        return cityArea;
    }

    public void setCityArea(Integer cityArea) {
        this.cityArea = cityArea;
    }

    public Integer getTown() {
        return town;
    }

    public void setTown(Integer town) {
        this.town = town;
    }

    public Integer getVillage() {
        return village;
    }

    public void setVillage(Integer village) {
        this.village = village;
    }

    public String getRadioCallNo() {
        return radioCallNo;
    }

    public void setRadioCallNo(String radioCallNo) {
        this.radioCallNo = radioCallNo == null ? null : radioCallNo.trim();
    }

    public String getTypeOther() {
        return typeOther;
    }

    public void setTypeOther(String typeOther) {
        this.typeOther = typeOther == null ? null : typeOther.trim();
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity == null ? null : validity.trim();
    }
}