package com.ctbt.beidou.role.service;

import java.util.List;
import java.util.Map;

import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.model.BdRole;

public interface IBdRoleService {
	int deleteByPrimaryKey(Integer roleId);

    int insert(BdRole record);

    int insertSelective(BdRole record);

    BdRole selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(BdRole record);

    int updateByPrimaryKey(BdRole record);
    
    ResultView saveBdRoleEdit(BdRole record);
    List<Map<String,Object>> queryRoleList();
}
