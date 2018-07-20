package com.ctbt.beidou.base.dao;

import java.util.List;
import java.util.Map;

import com.ctbt.beidou.base.model.BdMsgAlarm;


public interface BdMsgAlarmMapper {
    int deleteByPrimaryKey(Integer msgId);

    int insert(BdMsgAlarm record);

    int insertSelective(BdMsgAlarm record);

    BdMsgAlarm selectByPrimaryKey(Integer msgId);
    List<Map<String,Object>> selectByCondition();

    int updateByPrimaryKeySelective(BdMsgAlarm record);

    int updateByPrimaryKey(BdMsgAlarm record);
  //查询所有人的信息
    List<Map<String, Object>> findAll();
  //查询搜索后的人的信息
    List<Map<String, Object>> searchInfo(String text);
    
    //查询to框中显示的人的信息(根据电话号码查找)
    List<Map<String, Object>> findByPhone(String[] phones);
      
    //将发送的信息批量插入到表中
    int toInsertMsg(List<BdMsgAlarm> list);
}