package com.ctbt.beidou.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.dao.BdAppMapper;
import com.ctbt.beidou.base.dao.BdMsgChatMapper;
import com.ctbt.beidou.base.utils.DateUtil;

@Transactional
@Service("appService")
public class BdAppServiceImpl implements IBdAppService{
	
	@Resource
	private BdAppMapper bdAppMapper;

	@Override
	public List<Map<String, Object>> findAll(Map<String, Object> params) {
		
		List<Map<String, Object>> list = bdAppMapper.findAll(params);
		System.out.println(list.size());
		List<Map<String, Object>> returnList = new ArrayList<>();
		for(int i=0; i<list.size(); i++) {
			if(!list.get(i).containsKey("from_phone")) {
				list.get(i).put("from_phone", null);
			}
			list.get(i).put("recv_time", 
					DateUtil.date2String((Date) list.get(i).get("recv_time"),DateUtil.FORMAT_DATETIME));
		}		
		for(int i=0; i<list.size(); i++) {
			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("from", list.get(i).get("msg_from"));
			returnMap.put("from_phone", list.get(i).get("from_phone"));
			returnMap.put("to", list.get(i).get("msg_to"));
			returnMap.put("to_phone", list.get(i).get("to_phone"));
			returnMap.put("type", list.get(i).get("type"));
			returnMap.put("body", list.get(i).get("msg_txt"));
			returnMap.put("is_read", list.get(i).get("is_recv"));
			returnMap.put("recv_time", list.get(i).get("recv_time"));
			returnMap.put("msgid", list.get(i).get("msg_id"));
			
			returnList.add(returnMap);
		}
							
		return returnList;
	}
	
}
