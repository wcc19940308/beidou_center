package com.ctbt.beidou.base.bo;

import java.util.ArrayList;
import java.util.List;

import com.ctbt.beidou.base.CommValue;

/**
 * 对结果集合的封装，用于 页面上 的分页显示
 * 
 */
public class Page {

	private int pageSize = CommValue.DEFAULT_PAGE_SIZE; // 每页的记录数

	private int recordCount = 0; // 总的记录数

	private int pageCount = 0; // 总的页数

	private int pageNo = 1; // 当前页号

	private List resultList = new ArrayList();

	/**
	     * 构造方法
	     * 
	     * @param resultList
	     *                已经分好页的结果集
	     * @param recordCount
	     *                总的记录数
	     * @param currentPageNo
	     *                当前页号
	     * @param pageSize
	     *                每页的记录数
	     */
	public Page(List resultList, int recordCount, int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.recordCount = recordCount;
		this.resultList = resultList;
		// 这种构造方式要计算总的页数
		// 设置总的页数
		int n = this.recordCount % this.pageSize;
		if(this.recordCount <= this.pageSize){
			this.pageCount = 1;
		}else if(n == 0){
			this.pageCount = this.recordCount / this.pageSize;
		}else{
			this.pageCount = (this.recordCount / this.pageSize) + 1;
		}

		// 设置当前页号
		if(this.pageNo < 0){
			this.pageNo = 1; // 当前页号 小于0时，默认到第1页
		}else if(this.pageNo > this.pageCount){
			this.pageNo = this.pageCount;// 当前页号 大于总的页数时，默认到最后一页
		}
	}

	public static Page makePage(List allList, int currentPageNo) {
		return Page.makePage(allList, currentPageNo, CommValue.DEFAULT_PAGE_SIZE);
	}

	/**
	     * 
	     * @param allList
	     * @param currentPageNo
	     * @param pageSize
	     */
	public static Page makePage(List allList, int currentPageNo, int pageSize) {
		if(null == allList) return null;

		int recordCount = allList.size(); // 总的记录数

		List resultList = new ArrayList();
		for(int i = pageSize * (currentPageNo - 1); i < pageSize * currentPageNo && i < recordCount; i++){
			resultList.add(allList.get(i));
		}

		return new Page(resultList, recordCount, currentPageNo, pageSize);
	}

	/** 获得总的记录数* */
	public int getRecordCount() {
		return this.recordCount;
	}

	/** 获得总的页数* */
	public int getPageCount() {
		return this.pageCount;
	}

	/** 获得当前的页号* */
	public int getPageNo() {
		return this.pageNo;
	}

	/** 获得页面的结果集* */
	public List getResultList() {
		return this.resultList;
	}

	/** 获得页面的结果集* */
	public int getPageSize() {
		return this.pageSize;
	}
}
