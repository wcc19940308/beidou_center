package com.ctbt.beidou.app.controller;

import java.nio.channels.ScatteringByteChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctbt.beidou.app.service.IBdAppService;
import com.ctbt.beidou.base.bo.KeyValue;
import com.ctbt.beidou.base.utils.DicUtil;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

@Controller
@RequestMapping("/app")
public class AppController {

	private Logger logger = LogManager.getLogger(getClass());
	
	@Resource
	private IBdAppService appServie;
		
	
	//条件查询
	@RequestMapping(value="/new_msgs",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryNewMsgs(HttpServletRequest request) {
		String phone = request.getParameter("phone");
		String recv_time = request.getParameter("recv_time");
		Map<String, Object> params = new HashMap<>();
		params.put("phone", phone);
		params.put("recv_time", recv_time);
//		params.put("phone", "131888");
//		params.put("recv_time", "2018-07-16 14:18:06");
		List<Map<String, Object>> list = appServie.findAll(params);
		Map<String,Object> map = new HashMap<>();
		map.put("code", "1");
		map.put("list", list);
		return map;
	}
	
	//APP 获取角色列表
	@RequestMapping(value="/roleList", method = RequestMethod.POST)
	@ResponseBody
	public List<KeyValue> roleList(Map<String, Object> params) {
		List<KeyValue> kvList = DicUtil.getDic("101");
		return kvList;
	}
}
