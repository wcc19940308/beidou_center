package com.ctbt.beidou.alarm.controller;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctbt.beidou.alarm.service.IBdAlarmService;
import com.ctbt.beidou.base.model.BdMsgAlarm;
import com.ctbt.beidou.base.model.BdMsgChat;
import com.ctbt.beidou.base.model.BdShip;
import com.ctbt.beidou.webservices.NewAlarmMesWebServiceProxy;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


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
	public JSONObject queryAlarmList(HttpServletRequest request){

		Map<String, Object> record = new HashMap<>();
		// 发送开始时间
		if (("").equals(request.getParameter("beginTime1"))) {
			record.put("beginTime1", null);
		} else {
			record.put("beginTime1", request.getParameter("beginTime1"));
		}
		// 发送结束时间
		if (("").equals(request.getParameter("endTime1"))) {
			record.put("endTime1", null);
		} else {
			record.put("endTime1", request.getParameter("endTime1"));
		}
		
		// 接收开始时间
		if (("").equals(request.getParameter("beginTime2"))) {
			record.put("beginTime2", null);
		} else {
			record.put("beginTime2", request.getParameter("beginTime2"));
		}
		// 接收结束时间
		if (("").equals(request.getParameter("endTime2"))) {
			record.put("endTime2", null);
		} else {
			record.put("endTime2", request.getParameter("endTime2"));
		}
		//发送者
		if (("").equals(request.getParameter("msgFrom"))) {
			record.put("msgFrom", null);
		} else {
			record.put("msgFrom", request.getParameter("msgFrom"));
		}
		//接收者
		if (("").equals(request.getParameter("msgTo"))) {
			record.put("msgTo", null);
		} else {
			record.put("msgTo", request.getParameter("msgTo"));
		}		
		//报警类型
		if (("").equals(request.getParameter("msgType"))) {
			record.put("msgType", null);
		} else {
			record.put("msgType", request.getParameter("msgType"));
		}
		String page=request.getParameter("page");
	    String pageSize=request.getParameter("pageSize");
		PageHelper.startPage(Integer.valueOf(page),Integer.valueOf(pageSize));
		List<BdMsgAlarm> list = alarmService.queryAlarmList(record);
		PageInfo pages =new PageInfo(list,5);
	    JSONObject obj = new JSONObject();
	    obj.put("Rows", list);
	    obj.put("recordNum",pages.getTotal());
	    obj.put("currentPage",pages.getPageNum());
	    obj.put("sumPageNum",pages.getPages());
	    
		return obj;
	}	
	@RequestMapping("/toAlarmEdit")
	
	public String toAlarmEdit(HttpServletRequest request,Model model){

		return "alarm/alarmEdit";
	}
	
	
	@RequestMapping(value = "/saveAlarmEdit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAlarmEdit(HttpServletRequest request){
				
		Map<String, Object> returnMap = alarmService.saveBdMsgAlarmEdit(request);
		return returnMap;
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
		@RequestMapping("/toWebService")
		@ResponseBody
		public String toWebService(HttpServletRequest request,Integer msg_id) throws RemoteException{	
			BdMsgAlarm ms=alarmService.selectByPrimaryKey(msg_id);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
			Date nowDate=new Date();
			String send_Time=ms.getSendTime()!=null?df.format(ms.getSendTime()):df.format(nowDate);
			String recv_Time=ms.getRecvTime()!=null?df.format(ms.getRecvTime()):df.format(nowDate);
			String recv_Confirm_Time=ms.getRecvConfirmTime()!=null?df.format(ms.getRecvConfirmTime()):df.format(nowDate);
			String exclude_Confirm_Time=ms.getExcludeConfirmTime()!=null?df.format(ms.getExcludeConfirmTime()):df.format(nowDate);
			int is_Recv=ms.getIsRecv()!=null?Integer.valueOf(ms.getIsRecv()):0;
			int is_Exclude=ms.getIsExclude()!=null?Integer.valueOf(ms.getIsExclude()):0;
			String endpoint = "http://115.231.112.227:8088/CTBT/services/NewAlarmMes?wsdl";
			NewAlarmMesWebServiceProxy al=new NewAlarmMesWebServiceProxy(endpoint); 
			JSONObject ms2 = new JSONObject();
			ms2.put("id", ms.getMsgId());
			ms2.put("send_Time", send_Time);
			ms2.put("recv_Time", recv_Time);
			ms2.put("msg_Type", ms.getMsgType());
			ms2.put("msg_Txt", ms.getMsgTxt());
			ms2.put("msg_From", ms.getMsgFrom());
			ms2.put("msg_To", ms.getMsgTo());
			ms2.put("is_Recv", is_Recv);
			ms2.put("recv_Confirm_Time", recv_Confirm_Time);
			ms2.put("is_Exclude", is_Exclude);
			ms2.put("exclude_Confirm_Time",exclude_Confirm_Time);
			String webservice=ms2.toString();
			//String alarmMese = "{\"id\":5,\"send_Time\":\"2018-07-06 08:48:15\",\"recv_Time\":\"2018-07-26 10:01:44\",\"msg_Type\":\"1\",\"msg_Txt\":\"14123\",\"msg_From\":\"1111111\",\"msg_To\":\"14123\",\"is_Recv\":1,\"recv_Confirm_Time\":\"2018-07-12 10:02:00\",\"is_Exclude\":1,\"exclude_Confirm_Time\":\"2018-07-12 10:02:00\"}";
			String result=al.save(webservice);
			return result;
		}	
}
