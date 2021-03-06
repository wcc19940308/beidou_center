package com.ctbt.beidou.base.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
    List<BdMsgChat> selectByCondition(Map<String, Object> record);
    
    //查询所有人的信息
    List<Map<String, Object>> findAll();
    
    //查询搜索后的人的信息
    List<Map<String, Object>> searchInfo(String text);
    
    //查询to框中显示的人的信息(根据电话号码查找)
    List<Map<String, Object>> findByPhone(String[] phones);
      
    //将发送的信息批量插入到表中
    int toInsertMsg(List<BdMsgChat> list);
    
    //将船员给船员发送的信息插入
    int insertChatWithPhone(Map<String, Object> map);
    
    //判断消息类型，如果是图片或者语音则还要插入到bd_msg_chat_file中
    int insertChatFile(Map<String, Object> map);
    
    //查找某条语音信息
    String findVoice(int msgId);
    
  
}