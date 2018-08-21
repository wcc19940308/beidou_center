package com.ctbt.beidou.base.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctbt.beidou.base.bo.BdUserBo;
import com.ctbt.beidou.base.model.BdUser;

public interface BdUserMapper {
	int deleteByPrimaryKey(Integer userId);

	int insert(BdUser record);

	int insertSelective(BdUser record);

	BdUser selectByPrimaryKey(Integer userId);

	int updateByPrimaryKeySelective(BdUser record);

	int updateByPrimaryKey(BdUser record);

	List<?> selectByCondition(BdUser BdUser);

	BdUser selectByModel(Map<String, Object> hashMap);
	
	BdUser selectByPhone(BdUser record);

}