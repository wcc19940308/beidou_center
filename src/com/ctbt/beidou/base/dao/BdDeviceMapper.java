package com.ctbt.beidou.base.dao;

import java.util.List;

import com.ctbt.beidou.base.model.BdDevice;

public interface BdDeviceMapper {
	
	BdDevice selectByPrimaryKey(BdDevice bdDevice);
	
	List<BdDevice> selectAll();
	
	List<BdDevice> selectByItem(BdDevice bdDevice);
	
    int insert(BdDevice bdDevice);

    int deleteByPrimaryKey(BdDevice bdDevice);

    int updateByPrimaryKeySelective(BdDevice bdDevice);

  
}