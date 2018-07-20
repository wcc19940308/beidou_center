package com.ctbt.beidou.datapack.service;

import java.util.List;
import java.util.Map;

import com.ctbt.beidou.base.model.BdDataPackRecv;
import com.ctbt.beidou.base.model.BdDataPackSend;


public interface IBdDataPackService {

	List<BdDataPackSend> queryDataPackSendList(Map<String, Object> record);
	
	List<BdDataPackRecv> queryDataPackRecvList(Map<String, Object> record);
}
