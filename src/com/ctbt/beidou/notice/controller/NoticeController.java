package com.ctbt.beidou.notice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.model.BdMsgChat;
import com.ctbt.beidou.base.model.BdMsgNotice;
import com.ctbt.beidou.notice.service.IBdNoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	private Logger logger = LogManager.getLogger(getClass());

	@Resource
	private IBdNoticeService noticeService;

	// 条件查询
	@RequestMapping(value = "/queryNoticeList", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject queryNoticeList(HttpServletRequest request) {

		Map<String, Object> record = new HashMap<>();
		// 开始时间
		if (("").equals(request.getParameter("beginTime"))) {
			record.put("beginTime", null);
		} else {
			record.put("beginTime", request.getParameter("beginTime"));
		}
		// 结束时间
		if (("").equals(request.getParameter("endTime"))) {
			record.put("endTime", null);
		} else {
			record.put("endTime", request.getParameter("endTime"));
		}
		
		if (("").equals(request.getParameter("cardNo"))) {
			record.put("cardNo", null);
		} else {
			record.put("cardNo", request.getParameter("cardNo"));
		}
		String page=request.getParameter("page");
	    String pageSize=request.getParameter("pageSize");
		PageHelper.startPage(Integer.valueOf(page),Integer.valueOf(pageSize));
		List<BdMsgNotice> list = noticeService.queryNoticeList(record);
		PageInfo pages =new PageInfo(list,5);
	    JSONObject obj = new JSONObject();
	    obj.put("Rows", list);
	    obj.put("recordNum",pages.getTotal());
	    obj.put("currentPage",pages.getPageNum());
	    obj.put("sumPageNum",pages.getPages());
		
		return obj;
	}

	@RequestMapping("/toNoticeList")
	public String toNoticeList(HttpServletRequest request, Model model) {

		return "notice/noticeList";
	}

	@RequestMapping("/toNoticeEdit")
	public String toNoticeEdit(HttpServletRequest request, Model model) {

		return "notice/noticeEdit";
	}

	// 显示from,to的页面信息
	@RequestMapping(value = "/showFromTo")
	public String showFromTo(HttpServletRequest request, ModelMap retMap) {
		return "notice/noticeEdit";
	}

	// 向bd_msg_notice表插入向船员发送的数据
	@RequestMapping(value = "/sendNotice", method = RequestMethod.POST)
	@ResponseBody
	public ResultView sendNotice(HttpServletRequest request, @RequestParam Map<String, Object> param) {
		
		ResultView rv = noticeService.saveSendNotice(param);

		return rv;
	}

	// 找到所有人信息，并且跳转到选择人员界面
	@RequestMapping("/toSendMsg")
	public String toSendMsg(HttpServletRequest request, BdMsgNotice record, ModelMap retMap) {
		return "notice/noticeSend";
	}

}
