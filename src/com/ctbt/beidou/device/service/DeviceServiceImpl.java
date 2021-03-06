package com.ctbt.beidou.device.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.dao.BdDataSiteMapper;
import com.ctbt.beidou.base.dao.BdDeviceMapper;
import com.ctbt.beidou.base.model.BdDataSite;
import com.ctbt.beidou.base.model.BdDevice;

@Transactional
@Service("deviceService")
public class DeviceServiceImpl implements IDeviceService{
	
	@Resource
	private BdDeviceMapper bdDeviceMapper;

	@Resource
	private BdDataSiteMapper bdDataSiteMapper;
	
	@Override
	public BdDevice selectByPrimaryKey(BdDevice bdDevice) {
		// TODO Auto-generated method stub
		return bdDeviceMapper.selectByPrimaryKey(bdDevice);
	}

	@Override
	public List<BdDevice> selectAll() {
		// TODO Auto-generated method stub
		return bdDeviceMapper.selectAll();
	}

	@Override
	public List<BdDevice> selectByItem(BdDevice bdDevice) {
		// TODO Auto-generated method stub
		return bdDeviceMapper.selectByItem(bdDevice);
	}

	@Override
	public int insert(BdDevice bdDevice) {
		// TODO Auto-generated method stub
		return bdDeviceMapper.insert(bdDevice);
	}

	@Override
	public int deleteByPrimaryKey(BdDevice bdDevice) {
		// TODO Auto-generated method stub
		return bdDeviceMapper.deleteByPrimaryKey(bdDevice);
	}

	@Override
	public int updateByPrimaryKeySelective(BdDevice bdDevice) {
		// TODO Auto-generated method stub
		return bdDeviceMapper.updateByPrimaryKeySelective(bdDevice);
	}

	@Override
	public List<BdDataSite> selectAllDataSite(){
		return bdDataSiteMapper.selectAll();
	}


}
