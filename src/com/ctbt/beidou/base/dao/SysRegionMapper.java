package com.ctbt.beidou.base.dao;

import java.util.List;

import com.ctbt.beidou.base.bo.KeyValue;
import com.ctbt.beidou.base.model.SysRegion;

public interface SysRegionMapper {
    int deleteByPrimaryKey(Integer regId);
    Integer queryMax();
    int insert(SysRegion record);
    
    int insertSelective(SysRegion record);

    SysRegion selectByPrimaryKey(Integer regId);

    int updateByPrimaryKeySelective(SysRegion record);

    int updateByPrimaryKey(SysRegion record);
    
    List<KeyValue> queryCountryList();
    List<KeyValue> queryAllCity(Integer regId);
    List<KeyValue> queryAllProvince();
    List<KeyValue> queryAllCityArea(Integer regId);
	List<KeyValue> queryAllTown(Integer regId);
	List<KeyValue> queryAllVillage(Integer regId);
	
	List<KeyValue> queryProvinceList(Integer countryId);
	List<KeyValue> queryCityList(Integer regId);
	List<KeyValue> queryCityAreaList(Integer regId);
	List<KeyValue> queryTownList(Integer regId);
	List<KeyValue> queryVillageList(Integer regId);
}