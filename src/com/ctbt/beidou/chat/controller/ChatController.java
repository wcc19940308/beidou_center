package com.ctbt.beidou.chat.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.validator.Var;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ctbt.beidou.base.model.BdMsgAlarm;
import com.ctbt.beidou.base.model.BdMsgChat;
import com.ctbt.beidou.chat.service.IBdChatService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xerces.internal.util.EncodingMap;

import net.sf.json.JSONArray;



@Controller
@RequestMapping("/chat")
public class ChatController {

	private Logger logger = LogManager.getLogger(getClass());

	@Resource
	private IBdChatService chatServie;

	// 条件查询
	@RequestMapping(value = "/queryChatList", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject queryChatList(HttpServletRequest request) {

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
		
		if (("").equals(request.getParameter("fromPhone"))) {
			record.put("fromPhone", null);
		} else {
			record.put("fromPhone", request.getParameter("fromPhone"));
		}
		
		if (("").equals(request.getParameter("toPhone"))) {
			record.put("toPhone", null);
		} else {
			record.put("toPhone", request.getParameter("toPhone"));
		}
		

		String page=request.getParameter("page");
	    String pageSize=request.getParameter("pageSize");
		PageHelper.startPage(Integer.valueOf(page),Integer.valueOf(pageSize));
		List<BdMsgChat> list = chatServie.queryChatList(record);
		PageInfo pages =new PageInfo(list,5);
	    JSONObject obj = new JSONObject();
	    obj.put("Rows", list);
	    obj.put("recordNum",pages.getTotal());
	    obj.put("currentPage",pages.getPageNum());
	    obj.put("sumPageNum",pages.getPages());
	    
	   
		return obj;
	}

	@RequestMapping("/toChatList")
	public String toChatList(HttpServletRequest request, Model model) {

		return "chat/chatList";
	}

	// 查询所有
	@RequestMapping(value = "/showChatList", method = RequestMethod.POST)
	@ResponseBody
	public List<BdMsgChat> showChatList(HttpServletRequest request, BdMsgChat model) {

		List<BdMsgChat> list = chatServie.showChatList();

		return list;
	}

	// 显示from,to的页面信息
	@RequestMapping(value = "/showFromTo")
	public String showFromTo(HttpServletRequest request, ModelMap retMap) {
		return "chat/chatEdit";
	}

	// 向bd_msg_chat表插入向船员发送的数据
	@RequestMapping(value = "/toInsertMsg", method = RequestMethod.POST)
	@ResponseBody
	public int toInsertMsg(HttpServletRequest request) {

		String liString = request.getParameter("list");
		List<BdMsgChat> list = new ArrayList<>();
		JSONArray jsonArray = JSONArray.fromObject(liString);
		for (int i = 0; i < jsonArray.size(); i++) {
			Object object = jsonArray.get(i);		
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(object);
			BdMsgChat bdMsgChat = (BdMsgChat) net.sf.json.JSONObject.toBean(jsonObject, BdMsgChat.class);
			bdMsgChat.setSendTime(new Date());
			list.add(bdMsgChat);
			System.out.println(bdMsgChat);
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		int returnInt = chatServie.insertMsg(list);
		System.out.println(returnInt);
		return returnInt;
	}

	// 找到所有人信息，并且跳转到选择chat人员界面
	@RequestMapping("/toSendMsg")
	public String toSendMsg(HttpServletRequest request, BdMsgChat record, ModelMap retMap) {
		return "chat/chatSend";
	}

	// 短信信息插入
	@RequestMapping("/toSendPhoneMsg")
	public String toSendPhoneMsg(HttpServletRequest request, BdMsgChat record, ModelMap retMap) {
		return "phonemsg/phoneToSend2";
	}

	// 找到所有人的信息构造树形结构
	@RequestMapping(value = "/findAll", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> findAll(HttpServletRequest request, BdMsgChat record) {

		List<Map<String, Object>> list = chatServie.findAll(request);

		return list;
	}

	// 船员向船员发送信息（点对点）
	@RequestMapping(value = "/chatWithPhone", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> chatWithPhone(HttpServletRequest request) {

		Map<String, Object> returnMap = chatServie.insertChatWithPhone(request);

		return returnMap;
	}

}
