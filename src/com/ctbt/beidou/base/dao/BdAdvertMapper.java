package com.ctbt.beidou.base.dao;

import com.ctbt.beidou.base.model.BdAdvert;

public interface BdAdvertMapper {
    int deleteByPrimaryKey(Integer advId);

    int insert(BdAdvert record);

    int insertSelective(BdAdvert record);

    BdAdvert selectByPrimaryKey(Integer advId);

    int updateByPrimaryKeySelective(BdAdvert record);

    int updateByPrimaryKeyWithBLOBs(BdAdvert record);

    int updateByPrimaryKey(BdAdvert record);
}