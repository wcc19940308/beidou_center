package com.ctbt.beidou.base.dao;

import java.util.List;
import java.util.Map;

public interface BdAppMapper {
	//查询报警，天气，通知，聊天的所有信息
	 List<Map<String, Object>> findAll(Map<String, Object> params);
}
