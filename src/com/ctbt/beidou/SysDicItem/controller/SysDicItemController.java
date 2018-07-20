package com.ctbt.beidou.SysDicItem.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctbt.beidou.SysDicItem.service.ISysDicItemService;
import com.ctbt.beidou.base.model.SysDicItem;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/sysDicItem")
public class SysDicItemController {

	private Logger logger = LogManager.getLogger(getClass());

	@Resource
	private ISysDicItemService SysDicItemservice;

	
	@RequestMapping(value = "/tosysDicItemE")
	public String toShipEdit(HttpServletRequest request,SysDicItem sysDicItem, ModelMap retMap) {
		List<SysDicItem> sysDicItems=SysDicItemservice.selectByDicId(sysDicItem);	
		JSONArray jsonArray2 = JSONArray.fromObject(sysDicItems);
		retMap.addAttribute("sysDicItems", sysDicItem.getDicId());
		retMap.addAttribute("sysDic", jsonArray2);
		return "sysdic/sysdicEdit";
	}
	
	@RequestMapping(value = "/tosysDicItemR")
	@ResponseBody
	public JSONArray tosysDicItemR(HttpServletRequest request,SysDicItem sysDicItem) {
		List<SysDicItem> sysDicItems=SysDicItemservice.selectByDicId(sysDicItem);	
		JSONArray jsonArray2 = JSONArray.fromObject(sysDicItems);
		
		return jsonArray2;
	}


	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public int insert(HttpServletRequest request, SysDicItem sysDicItem) {
		List<SysDicItem> sysdic=SysDicItemservice.selectBykORV(sysDicItem);
		int result=0;
		if(sysdic.size()==0) {
			 result=SysDicItemservice.insert(sysDicItem);
		}		
			return result;
	}

	@RequestMapping(value = "/updatesysDicItem", method = RequestMethod.POST)
	@ResponseBody
	public int updateShip(HttpServletRequest request, SysDicItem sysDicItem) {
		
		int result=SysDicItemservice.updateByPrimaryKeySelective(sysDicItem);
			return result;
	}

}
