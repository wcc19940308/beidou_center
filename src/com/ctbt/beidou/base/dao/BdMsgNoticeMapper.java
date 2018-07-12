package com.ctbt.beidou.base.dao;

import java.util.List;
import java.util.Map;

import com.ctbt.beidou.base.model.BdMsgNotice;
import com.ctbt.beidou.base.model.BdMsgNotice;

public interface BdMsgNoticeMapper {
	int deleteByPrimaryKey(Integer msgId);

    int insert(BdMsgNotice record);

    int insertSelective(BdMsgNotice record);

    BdMsgNotice selectByPrimaryKey(Integer msgId);

    int updateByPrimaryKeySelective(BdMsgNotice record);

    int updateByPrimaryKey(BdMsgNotice record);
    
    //查询所有chat
    List<BdMsgNotice> selectAll();
    
    //按条件查询chat
    List<BdMsgNotice> selectByCondition(BdMsgNotice record);
    
    //查询所有人的信息
    List<Map<String, Object>> findAll();
    
    //查询搜索后的人的信息
    List<Map<String, Object>> searchInfo(String text);
    
    //查询to框中显示的人的信息(根据电话号码查找)
    List<Map<String, Object>> findByPhone(String[] phones);
      
    //将发送的信息批量插入到表中
    int toInsertMsg(List<BdMsgNotice> list);
    
    //查找某条语音信息
    String findVoice(int msgId);
}