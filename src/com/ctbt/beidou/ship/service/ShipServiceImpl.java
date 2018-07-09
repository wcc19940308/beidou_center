package com.ctbt.beidou.ship.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ctbt.beidou.base.dao.BdShipMapper;
import com.ctbt.beidou.base.model.BdShip;

@Service("shipService")
public class ShipServiceImpl implements IShipService {
	
	@Resource
	private BdShipMapper bdShipMapper;
	




	@Override
	public int insert(BdShip record) {
		// TODO Auto-generated method stub
		return bdShipMapper.insert(record);
	}

	@Override
	public int insertSelective(BdShip record) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int updateByPrimaryKey(BdShip record) {
		// TODO Auto-generated method stub
		return bdShipMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<BdShip> selectAll(Map map) {
		// TODO Auto-generated method stub
		return bdShipMapper.selectAll(map);
	}

	@Override
	public List<BdShip> selectByitem(BdShip record) {
		// TODO Auto-generated method stub
		return  bdShipMapper.selectByitem(record);
	}

	@Override
	public int deleteByPrimaryKey(BdShip record) {
		// TODO Auto-generated method stub
		return bdShipMapper.deleteByPrimaryKey(record);
	}

	@Override
	public BdShip selectByPrimaryKey(BdShip record) {
		// TODO Auto-generated method stub
		return bdShipMapper.selectByPrimaryKey(record);
	}
	
	
}
