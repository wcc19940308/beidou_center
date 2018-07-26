package com.ctbt.beidou.base.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctbt.beidou.base.model.BdPerm;
import com.ctbt.beidou.base.model.SysRegion;

public interface BdPermMapper {
    int deleteByPrimaryKey(Integer permId);

    int insert(BdPerm record);
    
    int insertSelective(BdPerm record);

    BdPerm selectByPrimaryKey(Integer permId);

    int updateByPrimaryKeySelective(BdPerm record);

    int updateByPrimaryKey(BdPerm record);
    
    List<BdPerm> queryRolePermTree(Integer permId);

	List<Map<String, Object>> queryRolePermTreeByRoleid(Integer roleId);
}