package com.ctbt.beidou.alarm.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.dao.BdMsgAlarmMapper;
import com.ctbt.beidou.base.model.BdMsgAlarm;

@Transactional
@Service("alarmService")
public class BdAlarmService implements IBdAlarmService{
	@Resource
	private BdMsgAlarmMapper bdMsgAlarmMapper;
	
	@Override
	public ResultView saveBdMsgAlarmEdit(BdMsgAlarm record) {



			record.setSendTime(new Date());
			bdMsgAlarmMapper.insert(record);
		

		
		return new ResultView("1","",record);
	}
	
	@Override
	public List<Map<String,Object>> queryAlarmList(){
		List<Map<String,Object>> list = bdMsgAlarmMapper.selectByCondition();
		return list;
	}

}
