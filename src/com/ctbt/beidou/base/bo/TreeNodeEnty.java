package com.ctbt.beidou.base.bo;

public class TreeNodeEnty extends TreeNode {

	public TreeNodeEnty() {

	}

	public TreeNodeEnty(Integer id, String code, String nodeName, Integer level, Integer parent) {
		this.id = id;
		this.code = code;
		this.nodeName = nodeName;
		this.level = level;
		this.parent = parent;
	}

	public TreeNodeEnty(Integer id, String code, String nodeName, Integer level, String url, String icon, Integer parent, String status, String checked, Integer listNo, String validity, String attr1, String attr2) {
		this.id = id;
		this.code = code;
		this.nodeName = nodeName;
		this.level = level;
		this.url = url;
		this.icon = icon;
		this.parent = parent;
		this.status = status;
		this.checked = checked;
		this.listNo = listNo;
		this.validity = validity;
		this.attr1 = attr1;
		this.attr2 = attr2;
	}

	@Override
	public String getChecked() {
		return this.checked;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public Integer getLevel() {
		return this.level;
	}

	@Override
	public Integer getListNo() {
		return this.listNo;
	}

	@Override
	public String getNodeName() {
		return this.nodeName;
	}

	@Override
	public Integer getParent() {
		return this.parent;
	}

	@Override
	public String getStatus() {
		return this.status;
	}

	@Override
	public String getValidity() {
		return this.validity;
	}

	@Override
	public void setChecked(String checked) {
		this.checked = checked;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	@Override
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Override
	public void setParent(Integer parent) {
		this.parent = parent;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public void setValidity(String validity) {
		this.validity = validity;
	}

	@Override
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

}
