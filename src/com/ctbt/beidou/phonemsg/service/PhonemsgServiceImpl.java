package com.ctbt.beidou.phonemsg.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.dao.BdPhoneMsgMapper;
import com.ctbt.beidou.base.dao.BdRoleMapper;
import com.ctbt.beidou.base.dao.BdUserMapper;
import com.ctbt.beidou.base.model.BdPhoneMsg;
import com.ctbt.beidou.base.model.BdRole;
import com.ctbt.beidou.base.model.BdUser;
import com.ctbt.beidou.base.utils.NumberUtil;
import com.mascloud.model.MoModel;
import com.mascloud.model.StatusReportModel;
import com.mascloud.model.SubmitReportModel;
import com.mascloud.sdkclient.Client;

@Transactional
@Service("phonemsgService")
public class PhonemsgServiceImpl implements IPhonemsgService {
	
	@Resource
	private BdPhoneMsgMapper bdPhoneMsgMapper;
	
	@Override
	public List<Map<String,Object>> queryPhonemsgList(BdPhoneMsg record,String sendTimeStart,String sendTimeEnd){
	
		try {
			HashMap<String, Object> parms = new HashMap<>();
			parms.put("phone", record.getPhone());
			parms.put("msgType", record.getMsgType());
			if (sendTimeStart=="") {
				parms.put("sendTimeStart", null);
			}else {
				parms.put("sendTimeStart", sendTimeStart);
			}
			if (sendTimeEnd=="") {
				parms.put("sendTimeEnd", null);
			}else {
				parms.put("sendTimeEnd", sendTimeEnd);
			}
			List<Map<String,Object>> PhoneMsglist = bdPhoneMsgMapper.queryPhonemsg(parms);
			Iterator<?> it = PhoneMsglist.iterator();
			while(it.hasNext()) {
			  //格式化时间	
				HashMap<String, Object> next = (HashMap<String, Object>) it.next();
				if(next.get("send_time")!=null) {
					next.put("send_time", next.get("send_time").toString());
				}
			}
			return PhoneMsglist;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
//		Date time = new java.sql.Date(new java.util.Date().getTime());
//		record.setSendTime(time);

	}

	@Override
	public int insert(BdPhoneMsg record) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public int insertSelective(BdPhoneMsg record) {
		// TODO 自动生成的方法存根
		record.setMsgType("2");
		record.setPhone("10089");
		record.setMsgTxt("2222");
		Date time = new java.sql.Date(new java.util.Date().getTime());
		record.setSendTime(time);
		bdPhoneMsgMapper.insertSelective(record);
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(BdPhoneMsg record) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public int updateByPrimaryKey(BdPhoneMsg record) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public ResultView saveBdRoleEdit(BdPhoneMsg record) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public int deleteByPrimaryKey(Integer roleId) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public BdRole selectByPrimaryKey(Integer BdPhoneMsgId) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public int sendMessageToDB(List<BdPhoneMsg> contactList) {
		// TODO 将短信存到数据库
		
		int flag = 0;
		if (contactList.get(0).getMsgTxt()!=null&&contactList.get(0).getMsgTxt()!="") {
			bdPhoneMsgMapper.insertbBatchSave(contactList);
			//保存非验证短信
			flag = 1;
		}
		if (contactList.get(0).getMsgCode()!=null) {
			bdPhoneMsgMapper.insertbBatchSave2(contactList);
			//保存验证码短信
			flag = 1;
		}
	
		return flag;
	}
	
	@Override
	public int sendVerifySMS(String mobile) {
		// TODO 像服务器发送验证短信
		
		  Client client = Client.getInstance();
	      boolean isLoggedin = client.login("http://112.35.4.197:15000", "ctbtms", "ctbtms+88", "浙江同博科技发展有限公司");
	           if (isLoggedin) {
	            System.out.println("Login successed");
	            if (sendSMStoPhoe(mobile)>0) {return 1;}
	            else {return 0;}
	        } else {
	            System.out.println("Login failed");
	            return 0;
	        }
		
			}
	
	@Override
	public int sendSMStoPhoe(String mobile) {
		// TODO 调用移动服务器向手机发送短信
		Client client = Client.getInstance();
        int randomNum_six = (int)((Math.random()*9+1)*100000);
		String[] parms = {Integer.valueOf(randomNum_six).toString()};
        String UUID = NumberUtil.getUUID();
        String[] mobiles = {mobile};
		int sendResult = client.sendTSMS( mobiles, "5b053a9675234628858a5a90b10f5e4d", parms, "1", 1, "JCLDVtmIP", UUID );
		if( sendResult == 1 ) {
			System.out.println( "Successfully send " + mobiles.length + " SMS(s)" );
			
			List<BdPhoneMsg> SMSList = new ArrayList<>();
			BdPhoneMsg bdPhoneMsg = new BdPhoneMsg();
			bdPhoneMsg.setMsgCode(Integer.valueOf(parms[0]));
			bdPhoneMsg.setPhone(mobile);
			bdPhoneMsg.setSendUUID(UUID);
			bdPhoneMsg.setMsgType("1");//验证短信
			bdPhoneMsg.setSendFlag("0");//0表示未验证是否发送成功
			bdPhoneMsg.setSendTime(new Date());
			SMSList.add(bdPhoneMsg);
			sendMessageToDB(SMSList);
			return sendResult;
		} else {
			System.out.println( "Failed to send " + mobiles.length + " SMS(s)" );
		
		}
//		while(true) {
//			//get submit report
//			List<SubmitReportModel> list  = client.getSubmitReport();
//			if(list.size( )>0){
//				System.out.println(JSONArray.toJSON( list.get( 0 ) ));
//			}
//			//get report
//			List<StatusReportModel> StatusReportlist = client.getReport();
//			if(StatusReportlist.size( )>0){
//				System.out.println(JSONArray.toJSON( StatusReportlist.get( 0 ) ));
//			}
//			return 1;
//		}
		return sendResult;
	}
	
	@Override
	public boolean isExistVerityCode(String mobile,String verityCode) {
		// TODO 服务器中是否存在某条号码的某条短信
		if (verityCode!=null) {
			BdPhoneMsg bdPhoneMsg = bdPhoneMsgMapper.selectByPhone(mobile);
			Date nowDate = new Date();
			long time =  (nowDate.getTime()-bdPhoneMsg.getSendTime().getTime());
			int timeMinute =(int)time/(1000*60);
			if ( timeMinute > 10) {
				return false;
			}
			String codeInDB = bdPhoneMsg.getMsgCode().toString();
			if (codeInDB.equals(verityCode)) {
				return true;
			}else {
				return false;
			}
			
		}else {
			return false;
		}
		
		
		  
		
	}
}
