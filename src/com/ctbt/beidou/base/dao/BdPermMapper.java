package com.ctbt.beidou.base.dao;

import com.ctbt.beidou.base.model.BdPerm;

public interface BdPermMapper {
    int deleteByPrimaryKey(Integer permId);

    int insert(BdPerm record);

    int insertSelective(BdPerm record);

    BdPerm selectByPrimaryKey(Integer permId);

    int updateByPrimaryKeySelective(BdPerm record);

    int updateByPrimaryKey(BdPerm record);
}