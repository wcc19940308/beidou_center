package com.ctbt.beidou.base.dao;

import com.ctbt.beidou.base.model.BdUserDetail;

public interface BdUserDetailMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(BdUserDetail record);

    int insertSelective(BdUserDetail record);

    BdUserDetail selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(BdUserDetail record);

    int updateByPrimaryKey(BdUserDetail record);

}