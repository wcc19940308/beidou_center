package com.ctbt.beidou.alarm.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.model.BdMsgAlarm;
import com.ctbt.beidou.base.model.BdMsgChat;

public interface IBdAlarmService {
	

	String saveBdMsgAlarmEdit(BdMsgAlarm record);
	List<BdMsgAlarm> queryAlarmList(Map<String, Object> record);
	List<Map<String, Object>> findAll(HttpServletRequest request);
	int insertMsg(List<BdMsgAlarm> list);
	BdMsgAlarm selectByPrimaryKey(Integer msgId);
	List<BdMsgAlarm> selectSendToData(BdMsgAlarm record);

}
