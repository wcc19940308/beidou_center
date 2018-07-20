package com.ctbt.beidou.sysdic.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.dao.SysDicMapper;
import com.ctbt.beidou.base.model.SysDic;

@Transactional
@Service("sysdicService")
public class SysDicServiceImpl implements ISysDicService {
	@Resource
	private SysDicMapper sysdicmapper;

	@Override
	public List<SysDic> selectAll(){
		// TODO Auto-generated method stub
		return sysdicmapper.selectAll();
	}

	@Override
	public List<SysDic> selectAllByItem(SysDic sysDic) {
		// TODO Auto-generated method stub
		return sysdicmapper.selectAllByItem(sysDic);
	}
	

}
