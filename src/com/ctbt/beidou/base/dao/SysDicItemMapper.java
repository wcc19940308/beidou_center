package com.ctbt.beidou.base.dao;

import java.util.List;

import com.ctbt.beidou.base.model.SysDicItem;
import com.ctbt.beidou.base.model.SysDicItemKey;

public interface SysDicItemMapper {
    int deleteByPrimaryKey(SysDicItem key);

    int insert(SysDicItem record);

    
    SysDicItem selectByPrimaryKey(SysDicItemKey key);

    int updateByPrimaryKey(SysDicItem record);
    
    List<SysDicItem> selectByDicId(SysDicItem sysDicItem);
    List<SysDicItem>  selectBykORV(SysDicItem sysDicItem);
    int insertSelective(SysDicItem record);
    int updateByPrimaryKeySelective(SysDicItem record);

    
    
}