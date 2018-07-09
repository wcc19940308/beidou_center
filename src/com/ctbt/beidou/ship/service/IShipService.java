package com.ctbt.beidou.ship.service;

import java.util.List;
import java.util.Map;

import com.ctbt.beidou.base.model.BdShip;

public interface IShipService {
	public List<BdShip> selectAll(Map map);
	
	public List<BdShip> selectByitem(BdShip record);
	
	public int deleteByPrimaryKey(BdShip record);

	public int insert(BdShip record);
	
	public int insertSelective(BdShip record);

	public  BdShip selectByPrimaryKey(BdShip record);


	public int updateByPrimaryKey(BdShip record);
}
