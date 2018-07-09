package com.ctbt.beidou.base.dao;

import com.ctbt.beidou.base.model.BdFishRecord;

public interface BdFishRecordMapper {
    int deleteByPrimaryKey(Integer recordId);

    int insert(BdFishRecord record);

    int insertSelective(BdFishRecord record);

    BdFishRecord selectByPrimaryKey(Integer recordId);

    int updateByPrimaryKeySelective(BdFishRecord record);

    int updateByPrimaryKey(BdFishRecord record);
}