package com.ctbt.beidou.base.dao;

import com.ctbt.beidou.base.model.BdUserPermKey;

public interface BdUserPermMapper {
    int deleteByPrimaryKey(BdUserPermKey key);

    int insert(BdUserPermKey record);

    int insertSelective(BdUserPermKey record);
}