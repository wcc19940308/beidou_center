package com.ctbt.beidou.base.dao;

import com.ctbt.beidou.base.model.BdMsgAlarm;

public interface BdMsgAlarmMapper {
    int deleteByPrimaryKey(Integer msgId);

    int insert(BdMsgAlarm record);

    int insertSelective(BdMsgAlarm record);

    BdMsgAlarm selectByPrimaryKey(Integer msgId);

    int updateByPrimaryKeySelective(BdMsgAlarm record);

    int updateByPrimaryKey(BdMsgAlarm record);
}