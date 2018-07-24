package com.ctbt.beidou.datapack.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ctbt.beidou.base.dao.BdDataPackRecvMapper;
import com.ctbt.beidou.base.dao.BdDataPackSendMapper;
import com.ctbt.beidou.base.model.BdDataPackRecv;
import com.ctbt.beidou.base.model.BdDataPackSend;

@Transactional
@Service("datapackService")
public class BdDataPackServiceImpl implements IBdDataPackService{

	@Resource
	private BdDataPackSendMapper bdDataPackSendMapper;
	
	@Resource
	private BdDataPackRecvMapper bdDataPackRecvMapper;
	
	@Override
	public List<BdDataPackSend> queryDataPackSendList(Map<String, Object> record) {
		
		
		
		return bdDataPackSendMapper.selectByCondition(record);
	}

	@Override
	public List<BdDataPackRecv> queryDataPackRecvList(Map<String, Object> record) {
		
		return bdDataPackRecvMapper.selectByCondition(record);
	}

	
}
