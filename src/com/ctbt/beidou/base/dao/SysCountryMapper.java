package com.ctbt.beidou.base.dao;

import com.ctbt.beidou.base.model.SysCountry;

public interface SysCountryMapper {
    int deleteByPrimaryKey(Integer countryId);

    int insert(SysCountry record);

    int insertSelective(SysCountry record);

    SysCountry selectByPrimaryKey(Integer countryId);

    int updateByPrimaryKeySelective(SysCountry record);

    int updateByPrimaryKey(SysCountry record);
}