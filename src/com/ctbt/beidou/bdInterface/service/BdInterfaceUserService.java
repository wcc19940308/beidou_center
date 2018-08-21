package com.ctbt.beidou.bdInterface.service;

import com.ctbt.beidou.base.model.BdInterfaceUser;

public interface BdInterfaceUserService {

    BdInterfaceUser selectByPrimaryKey(BdInterfaceUser record);

    BdInterfaceUser selectByUserId(String record);

    int updateByPrimaryKeySelective(BdInterfaceUser record);
	
}
