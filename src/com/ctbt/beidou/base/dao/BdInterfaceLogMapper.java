package com.ctbt.beidou.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ctbt.beidou.base.model.BdInterfaceLog;

public interface BdInterfaceLogMapper {
  
    int insertSelective(BdInterfaceLog record);

}