package com.ctbt.beidou.base.bo;

import java.util.ArrayList;
import java.util.List;

public abstract class TreeNode {
	
	protected Integer id;

	protected String code;

	protected String nodeName;

	protected Integer level;

	protected String url;

	protected String icon;

	protected Integer parent;

	protected String status;

	protected String checked;

	protected Integer listNo;

	protected String validity;

	protected List<TreeNode> childList = new ArrayList<TreeNode>();

	protected String attr1;//预留扩展属性1

	protected String attr2;//预留扩展属性2
	
	public List<TreeNode> getChildList() {
		return childList;
	}

	public void setChildList(List<TreeNode> childList) {
		this.childList = childList;
	}

	public void addChild(TreeNode child) {
		childList.add(child);
	}

	public void removeChild(TreeNode child) {
		childList.remove(child);
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getParent() {
		return this.parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getValidity() {
		return this.validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getChecked() {
		return this.checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
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

	public String toString() {
		return "[" + this.id + "," + this.nodeName + "," + this.level + "," + this.code + "]";
	}

}
