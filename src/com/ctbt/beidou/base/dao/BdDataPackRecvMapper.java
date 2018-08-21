package com.ctbt.beidou.base.dao;

import java.util.List;
import java.util.Map;

import com.ctbt.beidou.base.model.BdDataPackRecv;
import com.ctbt.beidou.base.model.BdRecvPackage;

public interface BdDataPackRecvMapper {
	
	List<BdDataPackRecv> selectByCondition(Map<String, Object> record);
	
	int insert(BdRecvPackage record);
}
