package com.ctbt.beidou.chat.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.model.BdMsgChat;
import com.ctbt.beidou.base.model.BdMsgChatDTO;

public interface IBdChatService {
	int deleteByPrimaryKey(Integer msgId);

    int insert(BdMsgChat record);

    int insertSelective(BdMsgChat record);

    BdMsgChat selectByPrimaryKey(Integer msgId);
    
    int updateByPrimaryKeySelective(BdMsgChat record);

    int updateByPrimaryKey(BdMsgChat record);
    
    //显示所有
    List<BdMsgChat> showChatList();
    
    //按条件查询
    List<BdMsgChat> queryChatList(BdMsgChat record);
    
    //展示所有人员信息
    List<Map<String, Object>> findAll(HttpServletRequest request);
    
    
    //将发送的信息批量插入
    int toInsertMsg(List<BdMsgChat> list);
    
}
