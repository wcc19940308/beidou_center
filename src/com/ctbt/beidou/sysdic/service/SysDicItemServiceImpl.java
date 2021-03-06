package com.ctbt.beidou.sysdic.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.dao.SysDicItemMapper;
import com.ctbt.beidou.base.model.SysDicItem;

@Transactional
@Service("sysdicitemService")
public class SysDicItemServiceImpl implements ISysDicItemService {
	@Resource
	private SysDicItemMapper sysdicitemMapper;

	@Override
	public List<SysDicItem> selectByDicId(SysDicItem sysDicItem) {
		// TODO Auto-generated method stub
		return sysdicitemMapper.selectByDicId(sysDicItem);
	}

	@Override
	public List<SysDicItem> selectBykORV(SysDicItem sysDicItem) {
		// TODO Auto-generated method stub
		return sysdicitemMapper.selectBykORV(sysDicItem);
	}

	@Override
	public int insert(SysDicItem record) {
		// TODO Auto-generated method stub
		return sysdicitemMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKeySelective(SysDicItem record) {
		// TODO Auto-generated method stub
		return sysdicitemMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteByPrimaryKey(SysDicItem key) {
		// TODO Auto-generated method stub
		return sysdicitemMapper.deleteByPrimaryKey(key);
	}

	
	

}
