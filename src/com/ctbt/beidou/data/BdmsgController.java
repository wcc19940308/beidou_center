package com.ctbt.beidou.data;

import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctbt.beidou.alarm.service.IBdAlarmService;
import com.ctbt.beidou.base.model.BdInterfaceLog;
import com.ctbt.beidou.base.model.BdInterfaceUser;
import com.ctbt.beidou.base.model.BdMsgAlarm;
import com.ctbt.beidou.base.model.BdMsgChat;
import com.ctbt.beidou.base.model.BdMsgNotice;
import com.ctbt.beidou.base.model.BdMsgType;
import com.ctbt.beidou.base.model.BdMsgWeather;
import com.ctbt.beidou.bdInterface.service.BdInterfaceLogService;
import com.ctbt.beidou.bdInterface.service.BdInterfaceUserService;
import com.ctbt.beidou.chat.service.IBdChatService;
import com.ctbt.beidou.notice.service.IBdNoticeService;
import com.ctbt.beidou.weather.service.IBdWeatherService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/bdInterface")
public class BdmsgController {

	private Logger logger = LogManager.getLogger(getClass());

	@Resource
	private IBdNoticeService noticeService;

	@Resource
	private IBdChatService chatServie;

	@Resource
	private IBdAlarmService alarmService;
	
	@Resource
	private IBdWeatherService weatherService;
	
	@Resource
	private BdInterfaceLogService bdInterfaceLogService;
	
	@Resource 
	private BdInterfaceUserService bdInterfaceUserService;
	
	public static Map<String, Date> loginStamp = new HashMap<String, Date>();
	
	@PostMapping(value="/login")
	@ResponseBody
	public String bdInterfaceLogin(HttpServletRequest request,BdInterfaceUser bdInterfaceUser) {
		BdInterfaceUser bduser=bdInterfaceUserService.selectByPrimaryKey(bdInterfaceUser);
		Date loginDate=new Date();
		if(bduser!=null) {
//			不是初次登录
			if (bduser.getLastLoginTime()!=null) {
				long between=Math.abs(bduser.getLastLoginTime().getTime()-loginDate.getTime())/60000;//除以1000是为了转换成秒
//				登录30分钟超时
				if (between>30){
					String user_id=String.valueOf((int)(Math.random()*9+1)*1000);
					BdInterfaceUser bduserId=bdInterfaceUserService.selectByUserId(user_id);
					while(bduserId!=null) {
						user_id=String.valueOf((int)(Math.random()*9+1)*1000);
						bduserId=bdInterfaceUserService.selectByUserId(user_id);
					}
					loginStamp.put(user_id,new Date());
					bdInterfaceUser.setLastLoginTime(loginDate);
					bdInterfaceUser.setUserId(user_id);
					int temp=bdInterfaceUserService.updateByPrimaryKeySelective(bdInterfaceUser);
					return user_id;
			}
//				30分钟未超时
			else {
					loginStamp.put(bduser.getUserId(),new Date());
					return bduser.getUserId();
				}
			}
//		初次登录
			else {
				String user_id=String.valueOf((int)(Math.random()*9+1)*1000);
				BdInterfaceUser bduserId=bdInterfaceUserService.selectByUserId(user_id);
				while(bduserId!=null) {
					user_id=String.valueOf((int)(Math.random()*9+1)*1000);
					bduserId=bdInterfaceUserService.selectByUserId(user_id);
				}
				loginStamp.put(user_id,new Date());
				bdInterfaceUser.setLastLoginTime(loginDate);
				bdInterfaceUser.setUserId(user_id);
				int temp=bdInterfaceUserService.updateByPrimaryKeySelective(bdInterfaceUser);
				return user_id;
			}
		}
//		账号密码错误
		else {
			return "F0000000";	
		}
	}

	@PostMapping(value="/msg")
	@ResponseBody
	public String bdInterfaceMsg(HttpServletRequest request,BdMsgType msgs)  {
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("sequence",msgs.getSequence());
		jsonReturn.put("description","");
		Date loginDate=new Date();
		try {
		if (loginStamp.containsKey(msgs.getSequence().substring(0,4))) {
			if (Math.abs(loginStamp.get(msgs.getSequence().substring(0,4)).getTime()-loginDate.getTime())/60000<30) {
		BdInterfaceUser bduserId=bdInterfaceUserService.selectByUserId(msgs.getSequence().substring(0,4));
		String request_type=msgs.getRequest_type();
		String args=msgs.getArgs();
		if (args==null|| request_type==null || args.equals("")) {
			jsonReturn.put("receipt_code","F0000007");
			return jsonReturn.toString();
		}
//		聊天信息
		else if(request_type.equals("9") ||request_type.equals("10") || request_type.equals("11") ||request_type.equals("12")) {
			String liString="["+args+"]";
//			String liString=args;
			List<BdMsgChat> msgChat=new ArrayList<>();
			JSONArray jsonObjects = JSONArray.fromObject(liString);
			for (int i = 0; i < jsonObjects.size(); i++) {				
				Object object = jsonObjects.get(i);
				JSONObject jsonObject = JSONObject.fromObject(object);
				BdMsgChat bdMsgNotice = (BdMsgChat) JSONObject.toBean(jsonObject, BdMsgChat.class);
				if(bdMsgNotice.getMsgType()==null) {
					bdMsgNotice.setMsgType("1");
				}
				msgChat.add(bdMsgNotice);
			}
			int a=chatServie.insertMsg(msgChat);
			if (a>0) {
				for(BdMsgChat chat:msgChat) {
					BdInterfaceLog bdInterfaceLog=new BdInterfaceLog();
					bdInterfaceLog.setLogTime(new Date());
					bdInterfaceLog.setUserName(bduserId.getUserName());
					bdInterfaceLog.setRequestSequence(msgs.getSequence());
					bdInterfaceLog.setTerminalCode(msgs.getTerminal_code());
					bdInterfaceLog.setTerminalType(msgs.getTerminal_type());
					bdInterfaceLog.setRequestType(msgs.getRequest_type());
					bdInterfaceLog.setBusinessId(String.valueOf(chat.getMsgId()));
					bdInterfaceLog.setReceiptCode("0");
					bdInterfaceLog.setDescription("无");
					bdInterfaceLogService.insertSelective(bdInterfaceLog);
				}
				jsonReturn.put("receipt_code","0");
				return jsonReturn.toString();
			} else {
				for(BdMsgChat chat:msgChat) {
					BdInterfaceLog bdInterfaceLog=new BdInterfaceLog();
					bdInterfaceLog.setLogTime(new Date());
					bdInterfaceLog.setUserName(bduserId.getUserName());
					bdInterfaceLog.setRequestSequence(msgs.getSequence());
					bdInterfaceLog.setTerminalCode(msgs.getTerminal_code());
					bdInterfaceLog.setTerminalType(msgs.getTerminal_type());
					bdInterfaceLog.setRequestType(msgs.getRequest_type());
					bdInterfaceLog.setBusinessId(String.valueOf(chat.getMsgId()));
					bdInterfaceLog.setReceiptCode("F0000007");
					bdInterfaceLog.setDescription("无");
					bdInterfaceLogService.insertSelective(bdInterfaceLog);
				}
				jsonReturn.put("receipt_code","F0000007");
				return jsonReturn.toString();
			}
		}
//		报警
		else if (request_type.equals("19")) {
			String liString="["+args+"]";
//			String liString=args;
			List<BdMsgAlarm> msgChat=new ArrayList<>();
			JSONArray jsonObjects = JSONArray.fromObject(liString);
			for (int i = 0; i < jsonObjects.size(); i++) {				
					Object object = jsonObjects.get(i);
					JSONObject jsonObject = JSONObject.fromObject(object);
					BdMsgAlarm bdMsgNotice = (BdMsgAlarm) JSONObject.toBean(jsonObject, BdMsgAlarm.class);
					msgChat.add(bdMsgNotice);
			}
			int a=alarmService.insertMsg(msgChat);
			if (a>0) {
				for(BdMsgAlarm chat:msgChat) {
					BdInterfaceLog bdInterfaceLog=new BdInterfaceLog();
					bdInterfaceLog.setLogTime(new Date());
					bdInterfaceLog.setUserName(bduserId.getUserName());
					bdInterfaceLog.setRequestSequence(msgs.getSequence());
					bdInterfaceLog.setTerminalCode(msgs.getTerminal_code());
					bdInterfaceLog.setTerminalType(msgs.getTerminal_type());
					bdInterfaceLog.setRequestType(msgs.getRequest_type());
					bdInterfaceLog.setBusinessId(String.valueOf(chat.getMsgId()));
					bdInterfaceLog.setReceiptCode("0");
					bdInterfaceLog.setDescription("无");
					bdInterfaceLogService.insertSelective(bdInterfaceLog);
				}
				jsonReturn.put("receipt_code","0");
				return jsonReturn.toString();
			}
			else {
				for(BdMsgAlarm chat:msgChat) {
					BdInterfaceLog bdInterfaceLog=new BdInterfaceLog();
					bdInterfaceLog.setLogTime(new Date());
					bdInterfaceLog.setUserName(bduserId.getUserName());
					bdInterfaceLog.setRequestSequence(msgs.getSequence());
					bdInterfaceLog.setTerminalCode(msgs.getTerminal_code());
					bdInterfaceLog.setTerminalType(msgs.getTerminal_type());
					bdInterfaceLog.setRequestType(msgs.getRequest_type());
					bdInterfaceLog.setBusinessId(String.valueOf(chat.getMsgId()));
					bdInterfaceLog.setReceiptCode("F0000007");
					bdInterfaceLog.setDescription("无");
					bdInterfaceLogService.insertSelective(bdInterfaceLog);
				}
				jsonReturn.put("receipt_code","F0000007");
				return jsonReturn.toString();
			}		
		}
//		通知
		else if (request_type.equals("101")) {
			String liString="["+args+"]";
//			String liString=args;
			List<BdMsgNotice> msgChat=new ArrayList<>();
			JSONArray jsonObjects = JSONArray.fromObject(liString);
			for (int i = 0; i < jsonObjects.size(); i++) {				
					Object object = jsonObjects.get(i);
					JSONObject jsonObject = JSONObject.fromObject(object);
					BdMsgNotice bdMsgNotice = (BdMsgNotice) JSONObject.toBean(jsonObject, BdMsgNotice.class);
					msgChat.add(bdMsgNotice);
			}
			int a=noticeService.insertMsg(msgChat);
			if (a>0) {
				for(BdMsgNotice chat:msgChat) {
					BdInterfaceLog bdInterfaceLog=new BdInterfaceLog();
					bdInterfaceLog.setLogTime(new Date());
					bdInterfaceLog.setUserName(bduserId.getUserName());
					bdInterfaceLog.setRequestSequence(msgs.getSequence());
					bdInterfaceLog.setTerminalCode(msgs.getTerminal_code());
					bdInterfaceLog.setTerminalType(msgs.getTerminal_type());
					bdInterfaceLog.setRequestType(msgs.getRequest_type());
					bdInterfaceLog.setBusinessId(String.valueOf(chat.getMsgId()));
					bdInterfaceLog.setReceiptCode("0");
					bdInterfaceLog.setDescription("无");
					bdInterfaceLogService.insertSelective(bdInterfaceLog);
				}
				jsonReturn.put("receipt_code","0");
				return jsonReturn.toString();
			}
			else {
				for(BdMsgNotice chat:msgChat) {
					BdInterfaceLog bdInterfaceLog=new BdInterfaceLog();
					bdInterfaceLog.setLogTime(new Date());
					bdInterfaceLog.setUserName(bduserId.getUserName());
					bdInterfaceLog.setRequestSequence(msgs.getSequence());
					bdInterfaceLog.setTerminalCode(msgs.getTerminal_code());
					bdInterfaceLog.setTerminalType(msgs.getTerminal_type());
					bdInterfaceLog.setRequestType(msgs.getRequest_type());
					bdInterfaceLog.setBusinessId(String.valueOf(chat.getMsgId()));
					bdInterfaceLog.setReceiptCode("F0000007");
					bdInterfaceLog.setDescription("无");
					bdInterfaceLogService.insertSelective(bdInterfaceLog);
				}
				jsonReturn.put("receipt_code","F0000007");
				return jsonReturn.toString();
			}	
		}
//		天气
		else if (request_type.equals("102")) {
			String liString="["+args+"]";
//			String liString=args;
			List<BdMsgWeather> msgChat=new ArrayList<>();
			JSONArray jsonObjects = JSONArray.fromObject(liString);
			for (int i = 0; i < jsonObjects.size(); i++) {				
					Object object = jsonObjects.get(i);
					JSONObject jsonObject = JSONObject.fromObject(object);
					BdMsgWeather bdMsgNotice = (BdMsgWeather) JSONObject.toBean(jsonObject, BdMsgWeather.class);
					msgChat.add(bdMsgNotice);
			}
			int a=weatherService.insertMsg(msgChat);
			if (a==1) {
				for(BdMsgWeather chat:msgChat) {
					BdInterfaceLog bdInterfaceLog=new BdInterfaceLog();
					bdInterfaceLog.setLogTime(new Date());
					bdInterfaceLog.setUserName(bduserId.getUserName());
					bdInterfaceLog.setRequestSequence(msgs.getSequence());
					bdInterfaceLog.setTerminalCode(msgs.getTerminal_code());
					bdInterfaceLog.setTerminalType(msgs.getTerminal_type());
					bdInterfaceLog.setRequestType(msgs.getRequest_type());
					bdInterfaceLog.setBusinessId(String.valueOf(chat.getMsgId()));
					bdInterfaceLog.setReceiptCode("0");
					bdInterfaceLog.setDescription("无");
					bdInterfaceLogService.insertSelective(bdInterfaceLog);
				}

				jsonReturn.put("receipt_code","0");
				return jsonReturn.toString();
			}
			else {
				for(BdMsgWeather chat:msgChat) {
					BdInterfaceLog bdInterfaceLog=new BdInterfaceLog();
					bdInterfaceLog.setLogTime(new Date());
					bdInterfaceLog.setUserName(bduserId.getUserName());
					bdInterfaceLog.setRequestSequence(msgs.getSequence());
					bdInterfaceLog.setTerminalCode(msgs.getTerminal_code());
					bdInterfaceLog.setTerminalType(msgs.getTerminal_type());
					bdInterfaceLog.setRequestType(msgs.getRequest_type());
					bdInterfaceLog.setBusinessId(String.valueOf(chat.getMsgId()));
					bdInterfaceLog.setReceiptCode("F0000007");
					bdInterfaceLog.setDescription("无");
					bdInterfaceLogService.insertSelective(bdInterfaceLog);
				}
				jsonReturn.put("receipt_code","F0000007");
				return jsonReturn.toString();
			}			
		}
		else {
			jsonReturn.put("receipt_code","F0000003");
			return jsonReturn.toString();
		}
	}
		else {
			jsonReturn.put("receipt_code","F0000004");
			return jsonReturn.toString();
			}
		
		}
		else {
			jsonReturn.put("receipt_code","F0000004");
			return jsonReturn.toString();
		}		
		
		
		} catch (Exception e) {
			jsonReturn.put("receipt_code","F0000016");
			return jsonReturn.toString();
		}
		
	
	}

	
}
