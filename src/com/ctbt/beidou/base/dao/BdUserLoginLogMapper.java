package com.ctbt.beidou.base.dao;

import com.ctbt.beidou.base.model.BdUserLoginLog;


public interface BdUserLoginLogMapper {
	int deleteByPrimaryKey(Integer key);

	int insert(BdUserLoginLog record);

	int insertSelective(BdUserLoginLog record);

	BdUserLoginLog selectByPrimaryKey(Integer key);

	int updateByPrimaryKeySelective(BdUserLoginLog record);

	int updateByPrimaryKey(BdUserLoginLog record);
}