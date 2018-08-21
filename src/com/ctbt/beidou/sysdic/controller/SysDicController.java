package com.ctbt.beidou.sysdic.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctbt.beidou.base.bo.KeyValue;
import com.ctbt.beidou.base.model.SysDic;
import com.ctbt.beidou.base.model.SysDicItem;
import com.ctbt.beidou.base.utils.DicUtil;
import com.ctbt.beidou.sysdic.service.ISysDicItemService;
import com.ctbt.beidou.sysdic.service.ISysDicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/sysdic")
public class SysDicController {

	private Logger logger = LogManager.getLogger(getClass());

	@Resource
	private ISysDicService sysdicservice;
	
	@Resource
	private ISysDicItemService SysDicItemservice;

	@RequestMapping("/toSysdic")
	public String toWelcome(HttpServletRequest request, Model model) {

		return "sysdic/sysdicList";
	}

	@RequestMapping(value = "/selectAll", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject selectAll(HttpServletRequest request, ModelMap retMap) {
		 
			String page=request.getParameter("page");
		    String pageSize=request.getParameter("pageSize");
			PageHelper.startPage(Integer.valueOf(page),Integer.valueOf(pageSize));
			List<SysDic> list = sysdicservice.selectAll();
			PageInfo pages =new PageInfo(list,5);
		    JSONObject obj = new JSONObject();
		    obj.put("Rows", list);
		    obj.put("recordNum",pages.getTotal());
		    obj.put("currentPage",pages.getPageNum());
		    obj.put("sumPageNum",pages.getPages());
		return obj;
	}
	
	@RequestMapping(value = "/selectAllByItem", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject selectAll(HttpServletRequest request, SysDic sysDic, ModelMap retMap) {
		String page=request.getParameter("page");
	    String pageSize=request.getParameter("pageSize");
		PageHelper.startPage(Integer.valueOf(page),Integer.valueOf(pageSize));	
		List<SysDic> list = sysdicservice.selectAllByItem(sysDic);	
		PageInfo pages =new PageInfo(list,5);
	    JSONObject obj = new JSONObject();
	    obj.put("Rows", list);
	    obj.put("recordNum",pages.getTotal());
	    obj.put("currentPage",pages.getPageNum());
	    obj.put("sumPageNum",pages.getPages());
		return obj;
	}
	
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
	public int updatesysDicItem(HttpServletRequest request) {	
		String ds = request.getParameter("ds");
		JSONArray json=JSONArray.fromObject(ds);
		List<SysDicItem> sysList = (List<SysDicItem>) json.toCollection(json, SysDicItem.class);
		Integer dicid=sysList.get(0).getDicId();
		SysDicItem sysDicItem=new SysDicItem();
		sysDicItem.setDicId(dicid);
		List<SysDicItem> list=SysDicItemservice.selectByDicId(sysDicItem);	
		String id = dicid != null ? dicid.toString() : "";
		Map<String, String> mapdata = new HashMap<String, String>();//用于存放带转换后的map文件
		Map<String, String> mapfront = new HashMap<String, String>();//用于存放带转换后的map文件

		int result = 0;
		for(SysDicItem li:list){
			mapdata.put(li.getItemKey(), li.getItemValue()); 
	    }
		for(SysDicItem li:sysList){
			mapfront.put(li.getItemKey(), li.getItemValue()); 
	    }

		for(SysDicItem li:list){
			 for(SysDicItem sysdic:sysList) 
			 {
				 if (sysdic.getItemKey().equals(li.getItemKey())) {
					 if(sysdic.getItemValue().equals(li.getItemValue())&&sysdic.getItemOrder()==li.getItemOrder()&&sysdic.getValidity().equals(li.getValidity())) {
						 result=1;
						 continue; 
					 }
					 else {
						 result=SysDicItemservice.updateByPrimaryKeySelective(sysdic);
					 }
					
				}
				 else if(!(mapdata.containsKey(sysdic.getItemKey()))){		
					 result=SysDicItemservice.insert(sysdic);
					 mapdata.put(sysdic.getItemKey(), sysdic.getItemValue());
					
					
				 }
				 else if(!(mapfront.containsKey(li.getItemKey()))){
					 result=SysDicItemservice.deleteByPrimaryKey(li);
					 mapfront.remove(li.getItemKey());
					
				}
				 else {
					continue;
				}
				 
			 }
			 
			 
	            
	    }
	
		return result;
	}	
}
