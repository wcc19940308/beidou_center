package com.ctbt.beidou.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ctbt.beidou.base.bo.KeyValue;
import com.ctbt.beidou.base.dao.SysDicMapper;
import com.ctbt.beidou.base.dao.SysRegionMapper;
import com.ctbt.beidou.base.model.SysDic;
import com.ctbt.beidou.base.model.SysRegion;

@Service("dicService")
public class DicServiceImpl implements IDicService{

	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Resource
	private SysDicMapper sysDicMapper;

	@Resource
	private SysRegionMapper sysRegionMapper;
	
	/**
     * 鑾峰緱鎵�鏈夌殑瀛楀吀锛宮ap<dicId,map<key,value>>
     * 
     * @return
     */
	public Map<String, List<KeyValue>> getAllDic() {
		Map<String, List<KeyValue>> allDicMap = new HashMap<String, List<KeyValue>>();
		List<SysDic> list = sysDicMapper.findAllValidity();
		if(list != null && list.size() > 0){
			SysDic sysDic;
			List<KeyValue> dicList;
			for(int k = 0; k < list.size(); k++){
				sysDic = (SysDic) list.get(k);
				dicList = this.loadDic(sysDic);
				if(dicList != null){
					allDicMap.put(sysDic.getDicId().toString(), dicList);
				}
			}
		}

		return allDicMap;
	}

	public List<KeyValue> loadDic(SysDic sysDic) {
		return sysDicMapper.queryDicKeyValueListBySql(sysDic.getDicSql());
	}
	



	/**
     * 鏍规嵁瀛楀吀鍙凤紝杩斿洖涓�涓瓧鍏革紝鐢ㄤ簬閲嶆柊鍔犺浇鏌愪竴涓� 瀛楀吀
     * 
     * @param dicId
     * @return
     */
	public List<KeyValue> loadDic(Integer dicId) {
		return sysDicMapper.queryDicKeyValueListById(dicId);
	}

	@Override
	public List<KeyValue> queryCountryList() {
		return sysRegionMapper.queryCountryList();
	}
	
	@Override
	public List<KeyValue> queryProvinceList(Integer countryId) {
		return sysRegionMapper.queryProvinceList(countryId);
	}

	@Override
	public List<KeyValue> queryCityList(Integer regId) {
		return sysRegionMapper.queryCityList(regId);
	}

	@Override
	public List<KeyValue> queryCityAreaList(Integer regId) {
		return sysRegionMapper.queryCityAreaList(regId);
	}

	@Override
	public List<KeyValue> queryTownList(Integer regId) {
		return sysRegionMapper.queryTownList(regId);
	}

	@Override
	public List<KeyValue> queryVillageList(Integer regId) {
		return sysRegionMapper.queryVillageList(regId);
	}

	@Override
	public List<KeyValue> queryAllProvince( ) {
		// TODO Auto-generated method stub
		return sysRegionMapper.queryAllProvince();
	}

	@Override
	public List<KeyValue> queryAllCity(Integer regId) {
		// TODO Auto-generated method stub
		return sysRegionMapper.queryAllCity(regId);
	}

	@Override
	public List<KeyValue> queryAllCityArea(Integer regId) {
		// TODO Auto-generated method stub
		return sysRegionMapper.queryAllCityArea(regId);
	}

	@Override
	public List<KeyValue> queryAllTown(Integer regId) {
		// TODO Auto-generated method stub
		return sysRegionMapper.queryAllTown(regId);
	}

	@Override
	public List<KeyValue> queryAllVillage(Integer regId) {
		// TODO Auto-generated method stub
		return sysRegionMapper.queryAllVillage(regId);
	}

	@Override
	public int insert(SysRegion record) {
		// TODO Auto-generated method stub
		return sysRegionMapper.insert(record);
	}

	@Override
	public Integer queryMax() {
		// TODO Auto-generated method stub
		return sysRegionMapper.queryMax();
	}
	
}
