package com.ctbt.beidou.data;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.ctbt.beidou.alarm.service.BdAlarmService;
import com.ctbt.beidou.alarm.service.IBdAlarmService;
import com.ctbt.beidou.base.model.BdMsgAlarm;
import com.ctbt.beidou.webservices.NewAlarmMesWebServiceProxy;

import net.sf.json.JSONObject;

public class SendToRunnable implements Runnable{
	
	private Logger logger = LogManager.getLogger(getClass());
	
	WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
	
	IBdAlarmService iBdAlarmService = (IBdAlarmService)context.getBean("alarmService");
	
	private boolean alive;
	
	public SendToRunnable() {
		this.alive = true;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	@Override
	public void run() {
		int count = 1;
		String sendMsg=null;
		String result=null;
		BdMsgAlarm newBdAlarm=new BdMsgAlarm();
		Calendar calendar=Calendar.getInstance();
		calendar.set(2015,10,12,11,32,52);  //某个具体的日期之前
	    Date preTime=calendar.getTime();
	    newBdAlarm.setExcludeConfirmTime(preTime);
	    newBdAlarm.setSendTime(preTime);
	    newBdAlarm.setRecvTime(preTime);
	    List<BdMsgAlarm> ms=null;
		try {
			logger.info("-----------SendToWebServiceStart-----------");
			while(alive){
				ms=toSendTo(newBdAlarm);
				if(!(ms.isEmpty())){
					for (BdMsgAlarm bdMsgAlarm:ms) {
						 logger.info("---------------Data has been transmitted:"+count+"----------------");
						 count++;
						  sendMsg=sendToFormat(bdMsgAlarm);
						  result=toWebService(sendMsg);
						 if(bdMsgAlarm.getSendTime()!=null) {
							 newBdAlarm.setSendTime(bdMsgAlarm.getSendTime());
						 	}
						if(bdMsgAlarm.getExcludeConfirmTime()!=null) {
							if(bdMsgAlarm.getExcludeConfirmTime().after(newBdAlarm.getExcludeConfirmTime())) {
							 newBdAlarm.setExcludeConfirmTime(bdMsgAlarm.getExcludeConfirmTime());	}					 
							 }
						if(bdMsgAlarm.getRecvTime()!=null) {
							if(bdMsgAlarm.getRecvTime().after(newBdAlarm.getRecvTime())) {
							 newBdAlarm.setRecvTime(bdMsgAlarm.getRecvTime());}
							}
					}	
				}
				else {
					logger.info("---------No data---------");

				}
				Thread.sleep(5000);
			}
			
		} catch (Exception ex) {
			logger.error(ex);
		}

	}



	public List<BdMsgAlarm> toSendTo(BdMsgAlarm sendTime){
		List<BdMsgAlarm> ms=iBdAlarmService.selectSendToData(sendTime);
		return ms;
	}	
	public String sendToFormat(BdMsgAlarm ms){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		Date nowDate=new Date();
		String send_Time=ms.getSendTime()!=null?df.format(ms.getSendTime()):df.format(nowDate);
		String recv_Time=ms.getRecvTime()!=null?df.format(ms.getRecvTime()):df.format(nowDate);
		String recv_Confirm_Time=ms.getRecvConfirmTime()!=null?df.format(ms.getRecvConfirmTime()):df.format(nowDate);
		String exclude_Confirm_Time=ms.getExcludeConfirmTime()!=null?df.format(ms.getExcludeConfirmTime()):df.format(nowDate);
		int is_Recv=ms.getIsRecv()!=null?Integer.valueOf(ms.getIsRecv()):0;
		int is_Exclude=ms.getIsExclude()!=null?Integer.valueOf(ms.getIsExclude()):0;
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
		String webservicedate=ms2.toString();	
		return webservicedate;
	}	
	
	public String toWebService(String webservice) throws RemoteException{
		String endpoint = "http://115.231.112.227:8088/CTBT/services/NewAlarmMes?wsdl";
		NewAlarmMesWebServiceProxy al=new NewAlarmMesWebServiceProxy(endpoint); 
		String result=al.save(webservice);
		return result;
	}	
	
}
