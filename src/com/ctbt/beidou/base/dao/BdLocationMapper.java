package com.ctbt.beidou.base.dao;

import com.ctbt.beidou.base.model.BdLocation;

public interface BdLocationMapper {
    int insert(BdLocation record);

    int insertSelective(BdLocation record);
}