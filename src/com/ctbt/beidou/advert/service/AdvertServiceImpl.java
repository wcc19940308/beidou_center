package com.ctbt.beidou.advert.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.dao.BdAdvertMapper;
import com.ctbt.beidou.base.model.BdAdvert;
import com.ctbt.beidou.base.model.BdMsgChat;

@Transactional
@Service("advService")
public class AdvertServiceImpl implements IAdvertService{
	
	@Resource
	private BdAdvertMapper bdAdvertMapper;
	


	@Override
	public List<BdAdvert> selectAll() {
		// TODO Auto-generated method stub
		return bdAdvertMapper.selectAll();
	}

	@Override
	public List<BdAdvert> selectByItem(BdAdvert record) {
		// TODO Auto-generated method stub
		return bdAdvertMapper.selectByItem(record);
	}

	@Override
	public BdAdvert selectByPrimaryKey(Integer advId) {
		// TODO Auto-generated method stub
		return bdAdvertMapper.selectByPrimaryKey(advId);
	}

	@Override
	public int deleteByPrimaryKey(Integer msgId) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int insert(BdAdvert record) {
		// TODO Auto-generated method stub
		return bdAdvertMapper.insert(record);
	}

	@Override
	public int insertSelective(BdAdvert record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(BdAdvert record) {
		// TODO Auto-generated method stub
		return bdAdvertMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyValidity(BdAdvert record) {
		// TODO Auto-generated method stub
		return bdAdvertMapper.updateByPrimaryKeyValidity(record);
	}


	
	

}
