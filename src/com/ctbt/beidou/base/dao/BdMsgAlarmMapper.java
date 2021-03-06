package com.ctbt.beidou.base.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ctbt.beidou.base.model.BdMsgAlarm;


public interface BdMsgAlarmMapper {
    int deleteByPrimaryKey(Integer msgId);

    int insert(BdMsgAlarm record);

//    app插入消息
    int insertSelective(Map<String, Object> map);
    
    BdMsgAlarm selectByPrimaryKey(Integer msgId);
    List<BdMsgAlarm> selectByCondition(Map<String, Object> record);

    int updateByPrimaryKeySelective(BdMsgAlarm record);

    int updateByPrimaryKey(BdMsgAlarm record);
  //查询所有人的信息
    List<Map<String, Object>> findAll();
  //查询搜索后的人的信息
    List<Map<String, Object>> searchInfo(String text);
    
    //查询to框中显示的人的信息(根据电话号码查找)
    List<Map<String, Object>> findByPhone(String[] phones);
    
    List<BdMsgAlarm> selectSendToData(BdMsgAlarm record);

    //将发送的信息批量插入到表中
    int toInsertMsg(List<BdMsgAlarm> list);
    
    //入站数据包处理解除报警
    int updateAlarmRelease(BdMsgAlarm alarm);
    
    // 确认排除报警信息
    int updateExcludeConfirm(BdMsgAlarm record);
    
    // 确认接收报警消息
    int updateRecvConfirm(BdMsgAlarm record);
}