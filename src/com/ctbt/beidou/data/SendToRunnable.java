package com.ctbt.beidou.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.ctbt.beidou.alarm.service.IBdAlarmService;
import com.ctbt.beidou.base.model.BdMsgAlarm;
import com.ctbt.beidou.base.model.SysDicItem;
import com.ctbt.beidou.base.utils.DicUtil;
import com.ctbt.beidou.sysdic.service.ISysDicItemService;
import com.ctbt.beidou.webservices.NewAlarmMesWebServiceProxy;

import net.sf.json.JSONObject;

public class SendToRunnable implements Runnable{
	
	private Logger logger = LogManager.getLogger(getClass());
	
	WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
	
	IBdAlarmService iBdAlarmService = (IBdAlarmService)context.getBean("alarmService");
	
	ISysDicItemService SysDicItemservice = (ISysDicItemService)context.getBean("sysdicitemService");

	
	DicUtil dc=new DicUtil();
	

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
		String endpoint = "http://115.231.112.227:8088/CTBT/services/NewAlarmMes?wsdl";
		NewAlarmMesWebServiceProxy al=new NewAlarmMesWebServiceProxy(endpoint); 
		BdMsgAlarm newBdAlarm=new BdMsgAlarm();
		SysDicItem sysDicItemE=new SysDicItem();
		SysDicItem sysDicItemS=new SysDicItem();
		SysDicItem sysDicItemR=new SysDicItem();
		sysDicItemE.setDicId(1);
		sysDicItemE.setItemKey("1");
		sysDicItemS.setDicId(1);
		sysDicItemS.setItemKey("2");
		sysDicItemR.setDicId(1);
		sysDicItemR.setItemKey("3");
		String datStringE=dc.getItemValue("1","1");
		String datStringS=dc.getItemValue("1","2");
		String datStringR=dc.getItemValue("1","3");
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDateE=new Date();
		Date startDateS=new Date();
		Date startDateR=new Date();
		try {
			 startDateE=sdf.parse(datStringE);
			 startDateS=sdf.parse(datStringS);
			 startDateR=sdf.parse(datStringR);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    newBdAlarm.setExcludeConfirmTime(startDateE);
	    newBdAlarm.setSendTime(startDateS);
	    newBdAlarm.setRecvTime(startDateR);
	    List<BdMsgAlarm> ms=null;
		try {
			logger.info("-----------SendToWebServiceStart-----------");
			while(alive){
				ms=toSendTo(newBdAlarm);
				if(!(ms.isEmpty())){
					for (BdMsgAlarm bdMsgAlarm:ms) {
						 logger.info("---------------Data has been transmitted:"+(count++)+"----------------");
						 
						  sendMsg=sendToFormat(bdMsgAlarm);	  
						  result=al.save(sendMsg);
						 if(bdMsgAlarm.getSendTime()!=null) {
							 sysDicItemS.setItemValue(sdf.format(bdMsgAlarm.getSendTime()));
							 SysDicItemservice.updateByPrimaryKeySelective(sysDicItemS);
							 newBdAlarm.setSendTime(bdMsgAlarm.getSendTime());
						 	}
						if(bdMsgAlarm.getExcludeConfirmTime()!=null) {
							if(bdMsgAlarm.getExcludeConfirmTime().after(newBdAlarm.getExcludeConfirmTime())) {
								 sysDicItemE.setItemValue(sdf.format(bdMsgAlarm.getExcludeConfirmTime()));
								SysDicItemservice.updateByPrimaryKeySelective(sysDicItemE);
								newBdAlarm.setExcludeConfirmTime(bdMsgAlarm.getExcludeConfirmTime());	}					 
							 }
						if(bdMsgAlarm.getRecvTime()!=null) {
							if(bdMsgAlarm.getRecvTime().after(newBdAlarm.getRecvTime())) {
								 sysDicItemR.setItemValue(sdf.format(bdMsgAlarm.getRecvTime()));
								SysDicItemservice.updateByPrimaryKeySelective(sysDicItemR);
							 newBdAlarm.setRecvTime(bdMsgAlarm.getRecvTime());}
							}
					}	
				}
				else {
					logger.info("---------No data " +(count-1)+"---------");

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
	

	
}
