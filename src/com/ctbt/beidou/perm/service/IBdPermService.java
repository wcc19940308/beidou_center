package com.ctbt.beidou.perm.service;

import java.util.List;
import java.util.Map;

import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.model.BdPerm;
import com.ctbt.beidou.base.model.BdRole;

public interface IBdPermService {
	int deleteByPrimaryKey(Integer PermId);

    int insert(BdPerm record);

    int insertSelective(BdPerm record);

    BdRole selectByPrimaryKey(Integer PermId);

    int updateByPrimaryKeySelective(BdPerm record);

    int updateByPrimaryKey(BdPerm record);
    
    ResultView saveBdRoleEdit(BdPerm record);
    
	List<Map<String, Object>> queryRolePermTree(Integer roleId);
	
	List<Map<String, Object>> arrangeRolePermTree(List<Map<String, Object>> rolePermListData);

	String updateRolePermTree(String data,Integer roleId );

	List<Map<String, Object>> queryRolePermData(Integer urId);

	List<BdPerm> selectAllTable();

	List queryRolePermPOJOList(Integer roleId);

	
}
