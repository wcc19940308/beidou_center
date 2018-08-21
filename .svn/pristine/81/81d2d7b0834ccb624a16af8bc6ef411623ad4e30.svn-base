package com.ctbt.beidou.bdInterface.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.dao.BdInterfaceUserMapper;
import com.ctbt.beidou.base.model.BdInterfaceUser;

@Transactional 
@Service("bdInterfaceUserService")
public class BdInterfaceUserServiceImpl implements BdInterfaceUserService{

	@Resource 
	private BdInterfaceUserMapper bdInterfaceUserMapper;

	@Override
	public BdInterfaceUser selectByPrimaryKey(BdInterfaceUser record) {
		// TODO Auto-generated method stub
		return bdInterfaceUserMapper.selectByPrimaryKey(record);
	}

	@Override
	public BdInterfaceUser selectByUserId(String record) {
		// TODO Auto-generated method stub
		return bdInterfaceUserMapper.selectByUserId(record);
	}

	@Override
	public int updateByPrimaryKeySelective(BdInterfaceUser record) {
		// TODO Auto-generated method stub
		return bdInterfaceUserMapper.updateByPrimaryKeySelective(record);
	}
	
	
	
}
