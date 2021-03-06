package com.ctbt.beidou.base.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctbt.beidou.base.model.BdPhoneMsg;

public interface BdPhoneMsgMapper {
    int deleteByPrimaryKey(Integer phoneMsgId);

    int insert(BdPhoneMsg record);

    int insertSelective(BdPhoneMsg record);

    BdPhoneMsg selectByPrimaryKey(Integer phoneMsgId);

    int updateByPrimaryKeySelective(BdPhoneMsg record);

    int updateByPrimaryKey(BdPhoneMsg record);

	List<Map<String, Object>> queryPhonemsg(HashMap<String, Object> parms);

	int insertbBatchSave(List contactList);
	int insertbBatchSave2(List contactList);

	BdPhoneMsg selectByPhone(String mobile);
}