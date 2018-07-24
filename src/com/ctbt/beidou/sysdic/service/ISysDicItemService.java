package com.ctbt.beidou.sysdic.service;

import java.util.List;

import com.ctbt.beidou.base.model.SysDicItem;

public interface ISysDicItemService {
    
    List<SysDicItem> selectByDicId(SysDicItem sysDicItem);
    List<SysDicItem>  selectBykORV(SysDicItem sysDicItem);
    int insert(SysDicItem record);
    int updateByPrimaryKeySelective(SysDicItem record);

}
