package com.ctbt.beidou.base.bo;


/**
 * Page 查询对象
 */

public class PageQueryer implements java.io.Serializable {
	
	protected Integer pageNo;
	
	protected Integer pageSize;
	
	protected boolean isAdmin = false;

	public Integer getPageNo() {
		return pageNo == null ? 1 : pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize == null ? 10 : pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}