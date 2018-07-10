package com.ctbt.beidou.alarm.service;

import java.util.List;
import java.util.Map;

import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.model.BdMsgAlarm;

public interface IBdAlarmService {
	

	ResultView saveBdMsgAlarmEdit(BdMsgAlarm record);
	List<Map<String,Object>> queryAlarmList();
}
