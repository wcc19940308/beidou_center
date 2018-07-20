package com.ctbt.beidou.phonemsg.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.dao.BdPhoneMsgMapper;
import com.ctbt.beidou.base.dao.BdRoleMapper;
import com.ctbt.beidou.base.dao.BdUserMapper;
import com.ctbt.beidou.base.model.BdPhoneMsg;
import com.ctbt.beidou.base.model.BdRole;
import com.ctbt.beidou.base.model.BdUser;

@Transactional
@Service("phonemsgService")
public class PhonemsgServiceImpl implements IPhonemsgService {
	
	@Resource
	private BdPhoneMsgMapper bdPhoneMsgMapper;
	
	@Override
	public List<Map<String,Object>> queryPhonemsgList(BdPhoneMsg record,Date sendTimeStart,Date sendTimeEnd){
	
		try {
			HashMap<String, Object> parms = new HashMap<>();
			parms.put("phone", record.getPhone());
			parms.put("msgType", record.getMsgType());
			parms.put("sendTimeStart", sendTimeStart);
			parms.put("sendTimeEnd", sendTimeEnd);
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
	public int sendMessage(List<BdPhoneMsg> contactList) {
		// TODO 自动生成的方法存根
		int flag = 0;
		if (contactList.get(0).getMsgTxt()!=null&&contactList.get(0).getMsgTxt()!="") {
			bdPhoneMsgMapper.insertbBatchSave(contactList);
			flag = 1;
		}
		if (contactList.get(0).getMsgCode()!=null) {
			bdPhoneMsgMapper.insertbBatchSave2(contactList);
			flag = 1;
		}
	
		return flag;
	}
}
