package com.ctbt.beidou.base.dao;

import java.util.List;
import java.util.Map;

import com.ctbt.beidou.base.model.BdMsgChat;

public interface BdMsgChatMapper {
    int deleteByPrimaryKey(Integer msgId);

    int insert(BdMsgChat record);

    int insertSelective(BdMsgChat record);

    BdMsgChat selectByPrimaryKey(Integer msgId);

    int updateByPrimaryKeySelective(BdMsgChat record);

    int updateByPrimaryKey(BdMsgChat record);
    
    //查询所有chat
    List<BdMsgChat> selectAll();
    
    //按条件查询chat
    List<BdMsgChat> selectByCondition(BdMsgChat record);
    
    //查询所有人的信息
    List<Map<String, Object>> findAll();
    
    //将发送的信息批量插入到表中
    int toInsertMsg(List<BdMsgChat> list);
    
    //查找某条语音信息
    String findVoice(int msgId);
    
  
}