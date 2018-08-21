package com.ctbt.beidou.bdInterface.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.dao.BdInterfaceLogMapper;
import com.ctbt.beidou.base.model.BdInterfaceLog;

@Transactional
@Service("bdInterfaceLogService")
public class BdInterfaceLogServiceImpl implements BdInterfaceLogService{

	@Resource
	private BdInterfaceLogMapper bdInterfaceLogMapper;

	@Override
	public int insertSelective(BdInterfaceLog record) {
		// TODO Auto-generated method stub
		return bdInterfaceLogMapper.insertSelective(record);
	}
	
	
}
