package com.ctbt.beidou.base.dao;

import com.ctbt.beidou.base.model.BdUserLoginSession;
import com.ctbt.beidou.base.model.BdUserLoginSessionKey;

public interface BdUserLoginSessionMapper {
    int deleteByPrimaryKey(BdUserLoginSessionKey key);

    int insert(BdUserLoginSession record);

    int insertSelective(BdUserLoginSession record);

    BdUserLoginSession selectByPrimaryKey(BdUserLoginSessionKey key);

    int updateByPrimaryKeySelective(BdUserLoginSession record);

    int updateByPrimaryKey(BdUserLoginSession record);
}