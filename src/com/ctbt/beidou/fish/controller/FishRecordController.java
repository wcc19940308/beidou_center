package com.ctbt.beidou.fish.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ctbt.beidou.base.model.BdFishRecord;
import com.ctbt.beidou.base.model.BdFishRecordDetail;
import com.ctbt.beidou.base.utils.DateUtil;
import com.ctbt.beidou.fish.service.IFishService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/fish")
public class FishRecordController {
	@Resource
	private IFishService fishService;
	
	@RequestMapping("/toFishLogList")
	public String toFishLogList(HttpServletRequest request, Model model,ModelMap retMap) {
		return "fish/fishLogList";
	}
	
	@RequestMapping("/toFishLogDetail")
	public String toFishLogDetail(HttpServletRequest request, Model model) {
		return "fish/fishlogDetail";
	}
	
	@RequestMapping("/queryFishLogList")
	@ResponseBody
	public JSONObject queryFishLogList(HttpServletRequest request,BdFishRecord bdFishRecord,BdFishRecordDetail bdFishRecordDetail,Model model){
		//传入开始结束日期，渔船ID，鱼类
		//判断日期是否为空
		try {
			Date sendTimeStart=null;
			Date sendTimeEnd = null;
			if(request.getParameter("sendTimeStart")!=null) {
				sendTimeStart = DateUtil.string2Date(request.getParameter("sendTimeStart"), "yyyy-MM-dd");

			}
			if(request.getParameter("sendTimeEnd")!=null) {
				sendTimeEnd = DateUtil.string2Date(request.getParameter("sendTimeEnd"), "yyyy-MM-dd");
			}
			String page=request.getParameter("page");
			String pageSize=request.getParameter("pageSize");
			PageHelper.startPage(Integer.valueOf(page),Integer.valueOf(pageSize));
			List list = fishService.getFishRecord(bdFishRecord,bdFishRecordDetail,sendTimeStart,sendTimeEnd);
			PageInfo pages =new PageInfo(list,5);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Row", list);
			jsonObject.put("recordNum",pages.getTotal());
			jsonObject.put("currentPage",pages.getPageNum());
			jsonObject.put("sumPageNum",pages.getPages());
			return jsonObject;
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
	}
	
	@RequestMapping("/queryFishLogListByKey")
	@ResponseBody
	public List queryFishLogListByKey(HttpServletRequest request,BdFishRecord bdFishRecord,Model model){
		//通过ID查询
		//判断日期是否为空
		try {
			List list = fishService.getFishLogListByKey(bdFishRecord.getRecordId());
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/queryFishDetailList")
	@ResponseBody
	public List queryFishDetailList(HttpServletRequest request,BdFishRecordDetail bdFishRecordDetail,Model model){
		//通过ID查询
		//判断日期是否为空
		try {
			List list = fishService.getFishRecordDetail(bdFishRecordDetail.getRecordId());
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("/addFishLogList")//app接口
	@ResponseBody
	public String addFishLogList(HttpServletRequest request,BdFishRecord bdFishRecord,BdFishRecordDetail bdFishRecordDetail,Model model){
		java.util.Date aa = DateUtil.string2Date(request.getParameter("recordDate"), "yyyy-MM-dd");
		bdFishRecord.setRecordDate(aa);
		com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(request.getParameter("fishData"));
		
		fishService.insertFishRecord(bdFishRecord);
		//如果有这条主要记录，就不添加，直接添加detail
		return "success";
	}
}
