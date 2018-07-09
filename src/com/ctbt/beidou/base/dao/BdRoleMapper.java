package com.ctbt.beidou.base.dao;

import java.util.List;
import java.util.Map;

import com.ctbt.beidou.base.model.BdRole;

public interface BdRoleMapper {
	int deleteByPrimaryKey(Integer roleId);

    int insert(BdRole record);

    int insertSelective(BdRole record);

    BdRole selectByPrimaryKey(Integer roleId);
    List<Map<String,Object>> selectByCondition();

    int updateByPrimaryKeySelective(BdRole record);

    int updateByPrimaryKey(BdRole record);
}