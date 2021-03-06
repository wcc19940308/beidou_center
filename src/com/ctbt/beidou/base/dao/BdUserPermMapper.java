package com.ctbt.beidou.base.dao;

import java.util.List;
import java.util.Map;

import com.ctbt.beidou.base.model.BdUserPermKey;

public interface BdUserPermMapper {
    int deleteByPrimaryKey(BdUserPermKey key);

    int insert(BdUserPermKey record);

    int insertSelective(BdUserPermKey record);

	List<Map<String, Object>> selectByroleId(Integer roleId);

	List<BdUserPermKey> selectByroleIdPOJO(Integer urId);
}