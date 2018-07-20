package com.ctbt.beidou.phonemsg.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.model.BdPhoneMsg;
import com.ctbt.beidou.base.model.BdRole;

public interface IPhonemsgService {
	int deleteByPrimaryKey(Integer roleId);

    int insert(BdPhoneMsg record);

    int insertSelective(BdPhoneMsg record);

    BdRole selectByPrimaryKey(Integer BdPhoneMsgId);

    int updateByPrimaryKeySelective(BdPhoneMsg record);

    int updateByPrimaryKey(BdPhoneMsg record);
    
    ResultView saveBdRoleEdit(BdPhoneMsg record);
    
    List<Map<String,Object>> queryPhonemsgList(BdPhoneMsg record,Date sendTimeStart,Date sendTimeEnd);



	int sendMessage(List<BdPhoneMsg> contactList);
}
