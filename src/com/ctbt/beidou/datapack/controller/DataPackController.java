package com.ctbt.beidou.datapack.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctbt.beidou.base.model.BdDataPackRecv;
import com.ctbt.beidou.base.model.BdDataPackSend;
import com.ctbt.beidou.base.model.BdMsgChat;
import com.ctbt.beidou.datapack.service.IBdDataPackService;


@Controller
@RequestMapping("/datapack")
public class DataPackController {

	private Logger logger = LogManager.getLogger(getClass());
	
	@Resource
	private IBdDataPackService bdDataPackService;
	
	//发送的包
	@RequestMapping("/toDataPackSendList")
	public String toDataPackInList(HttpServletRequest request,Model model){

	  return "datapack/dataPackSendList";
	}
	
	//收到的包
	@RequestMapping("/toDataPackRecvList")
	public String toDataPackOutList(HttpServletRequest request,Model model){

	  return "datapack/dataPackRecvList";
	}
	//发送列表
	@RequestMapping(value="/queryDataPackSendList",method = RequestMethod.POST)
	@ResponseBody
	public List<BdDataPackSend> queryDataPackSendList(HttpServletRequest request){
		
		Map<String, Object> record = new HashMap<>();
		//开始时间
		if(request.getParameter("beginTime").equals("")) {
			record.put("beginTime", null);
		}else {
			record.put("beginTime", request.getParameter("beginTime"));
		}
		//结束时间
		if(request.getParameter("endTime").equals("")) {
			record.put("endTime", null);
		}else {
			record.put("endTime", request.getParameter("endTime"));
		}
		
		if(request.getParameter("masterFrom").equals("")) {
			record.put("masterFrom", null);
		}else {
			record.put("masterFrom", request.getParameter("masterFrom"));
		}
		
		if(request.getParameter("masterTo").equals("")) {
			record.put("masterTo", null);
		}else {
			record.put("masterTo", request.getParameter("masterTo"));
		}
		
		if(request.getParameter("pkgFrom").equals("")) {
			record.put("pkgFrom", null);
		}else {
			record.put("pkgFrom", request.getParameter("pkgFrom"));
		}
		
		if(request.getParameter("pkgTo").equals("")) {
			record.put("pkgTo", null);
		}else {
			record.put("pkgTo", request.getParameter("pkgTo"));
		}
		
		if(request.getParameter("phoneFrom").equals("")) {
			record.put("phoneFrom", null);
		}else {
			record.put("phoneFrom", request.getParameter("phoneFrom"));
		}
		
		if(request.getParameter("phoneTo").equals("")) {
			record.put("phoneTo", null);
		}else {
			record.put("phoneTo", request.getParameter("phoneTo"));
		}
			
		List<BdDataPackSend> list = bdDataPackService.queryDataPackSendList(record);
		
		return list;
	}
	
	//接收列表
		@RequestMapping(value="/queryDataPackRecvList",method = RequestMethod.POST)
		@ResponseBody
		public List<BdDataPackRecv> queryDataPackRecvList(HttpServletRequest request){
			
			Map<String, Object> record = new HashMap<>();
			//开始时间
			if(request.getParameter("beginTime").equals("")) {
				record.put("beginTime", null);
			}else {
				record.put("beginTime", request.getParameter("beginTime"));
			}
			//结束时间
			if(request.getParameter("endTime").equals("")) {
				record.put("endTime", null);
			}else {
				record.put("endTime", request.getParameter("endTime"));
			}
			
			if(request.getParameter("masterFrom").equals("")) {
				record.put("masterFrom", null);
			}else {
				record.put("masterFrom", request.getParameter("masterFrom"));
			}
			
			if(request.getParameter("masterTo").equals("")) {
				record.put("masterTo", null);
			}else {
				record.put("masterTo", request.getParameter("masterTo"));
			}
			
			if(request.getParameter("pkgFrom").equals("")) {
				record.put("pkgFrom", null);
			}else {
				record.put("pkgFrom", request.getParameter("pkgFrom"));
			}
			
			if(request.getParameter("pkgTo").equals("")) {
				record.put("pkgTo", null);
			}else {
				record.put("pkgTo", request.getParameter("pkgTo"));
			}
			
			if(request.getParameter("phoneFrom").equals("")) {
				record.put("phoneFrom", null);
			}else {
				record.put("phoneFrom", request.getParameter("phoneFrom"));
			}
			
			if(request.getParameter("phoneTo").equals("")) {
				record.put("phoneTo", null);
			}else {
				record.put("phoneTo", request.getParameter("phoneTo"));
			}
				
			List<BdDataPackRecv> list = bdDataPackService.queryDataPackRecvList(record);
			
			return list;
		}
	
}
