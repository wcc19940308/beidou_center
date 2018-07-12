package com.ctbt.beidou.notice.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ctbt.beidou.base.model.BdMsgNotice;

public interface IBdNoticeService {

	int deleteByPrimaryKey(Integer msgId);

    int insert(BdMsgNotice record);

    int insertSelective(BdMsgNotice record);

    BdMsgNotice selectByPrimaryKey(Integer msgId);
    
    int updateByPrimaryKeySelective(BdMsgNotice record);

    int updateByPrimaryKey(BdMsgNotice record);
    
    //显示所有
    //List<BdMsgNotice> showChatList();
    
    //按条件查询
    List<BdMsgNotice> queryChatList(BdMsgNotice record);
    
    //展示所有人员信息
    List<Map<String, Object>> findAll(HttpServletRequest request);
    
    
    //将发送的信息批量插入
    int insertMsg(List<BdMsgNotice> list);
}
