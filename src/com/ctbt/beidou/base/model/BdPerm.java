package com.ctbt.beidou.base.model;

public class BdPerm {
    private Integer permId;

    private String parentId;

    private Integer permLevel;

    private String permName;

    private String permType;
    
    private String permIcon;
  
	private String permUrl;
	
	private String permScript;//保存点击事件

    private String validity;
    
    private Integer orderNo;

    public String getPermIcon() {
		return permIcon;
	}

	public void setPermIcon(String permIcon) {
		this.permIcon = permIcon;
	}

	public Integer getPermId() {
		return permId;
	}

	public void setPermId(Integer permId) {
		this.permId = permId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getPermLevel() {
		return permLevel;
	}

	public void setPermLevel(Integer permLevel) {
		this.permLevel = permLevel;
	}

	public String getPermName() {
		return permName;
	}

	public void setPermName(String permName) {
		this.permName = permName;
	}

	public String getPermType() {
		return permType;
	}

	public void setPermType(String permType) {
		this.permType = permType;
	}

	public String getPermUrl() {
		return permUrl;
	}

	public void setPermUrl(String permUrl) {
		this.permUrl = permUrl;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	  
	public String getPermScript() {
		return permScript;
	}

	public void setPermScript(String permScript) {
		this.permScript = permScript;
	}
	

   
}