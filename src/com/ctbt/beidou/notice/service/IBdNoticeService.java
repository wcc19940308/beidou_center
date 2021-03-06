package com.ctbt.beidou.notice.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.model.BdMsgNotice;

public interface IBdNoticeService {

	int deleteByPrimaryKey(Integer msgId);

    int insert(BdMsgNotice record);

    int insertSelective(BdMsgNotice record);

    BdMsgNotice selectByPrimaryKey(Integer msgId);
    
    int updateByPrimaryKeySelective(BdMsgNotice record);

    int updateByPrimaryKey(BdMsgNotice record);
        
    //按条件查询
    List<BdMsgNotice> queryNoticeList(Map<String, Object> record);

    //将发送的信息批量插入
    int insertMsg(List<BdMsgNotice> list);
    
    ResultView saveSendNotice(Map<String, Object> param);
}
