package com.ctbt.beidou.alarm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctbt.beidou.alarm.service.BdAlarmService;
import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.model.BdMsgAlarm;
import com.ctbt.beidou.base.model.BdMsgChat;
import com.ctbt.beidou.base.model.BdRole;
import com.ctbt.beidou.base.model.BdUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ctbt.beidou.alarm.service.IBdAlarmService;;


@Controller
@RequestMapping("/alarm")
public class AlarmController {

	private Logger logger = LogManager.getLogger(AlarmController.class);
	
	@Resource
	private IBdAlarmService alarmService;
		
	@RequestMapping("/toAlarmList")
	public String toAlarmList(HttpServletRequest request,Model model){

		return "alarm/alarmList";
	}
	
	@RequestMapping(value = "/queryAlarmList", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> queryAlarmList(HttpServletRequest request, BdMsgAlarm model){

		List<Map<String,Object>> list = alarmService.queryAlarmList();
		Map<String,Object> a = list.get(0);
		Object o = a.get("sendTime");
		
		return list;
	}
	

	
	@RequestMapping("/toAlarmEdit")
	
	public String toAlarmEdit(HttpServletRequest request,Model model){

		return "alarm/alarmEdit";
	}
	
	
	@RequestMapping(value = "/saveAlarmEdit",method=RequestMethod.POST)
	@ResponseBody
	public String saveAlarmEdit(HttpServletRequest request, BdMsgAlarm alarm){
		
		System.out.println(alarm.getSendTime());
		System.out.println(alarm.getRecvConfirmTime());
		String rv = alarmService.saveBdMsgAlarmEdit(alarm);
		return rv;
	}
	
	@InitBinder 
	 public void initBinder(WebDataBinder binder) { 
	  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
	  dateFormat.setLenient(true); 
	  binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); 
	 }


	//找到所有人信息，并且跳转到选择人员界面
	@RequestMapping("/toSendMsg")
	public String toSendMsg(HttpServletRequest request,BdMsgChat record,ModelMap retMap){
		return "alarm/alarmSend";
	}	
	//找到所有人的信息构造树形结构
	@RequestMapping(value = "/findAll",method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> findAll(HttpServletRequest request,BdMsgChat record){
			
	List<Map<String, Object>> list = alarmService.findAll(request);
	
	return list;
	}
	
	// 向bd_msg_alarm表插入向船员发送的数据
		@RequestMapping(value = "/toInsertMsg", method = RequestMethod.POST)
		@ResponseBody
		public int toInsertMsg(HttpServletRequest request) {

			String liString = request.getParameter("list");
			List<BdMsgAlarm> list = new ArrayList<>();
			JSONArray jsonArray = JSONArray.fromObject(liString);
			for (int i = 0; i < jsonArray.size(); i++) {
				Object object = jsonArray.get(i);
				JSONObject jsonObject = JSONObject.fromObject(object);
				BdMsgAlarm bdMsgAlarm = (BdMsgAlarm) JSONObject.toBean(jsonObject, BdMsgAlarm.class);
				bdMsgAlarm.setSendTime(new Date());
				list.add(bdMsgAlarm);
				System.out.println(bdMsgAlarm);
			}
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
			int returnInt = alarmService.insertMsg(list);
			System.out.println(returnInt);
			return returnInt;
		}
}
