package com.ctbt.beidou.chat.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.model.BdMsgChat;
import com.ctbt.beidou.base.model.BdMsgChatDTO;
import com.ctbt.beidou.base.model.BdRole;
import com.ctbt.beidou.base.utils.DateUtil;
import com.ctbt.beidou.chat.service.IBdChatService;
import com.sun.org.apache.xerces.internal.util.EncodingMap;

import jdk.internal.org.objectweb.asm.commons.Remapper;
import net.sf.json.JSONArray;


@Controller
@RequestMapping("/chat")
public class ChatController {

	private Logger logger = Logger.getLogger(getClass());
	
	@Resource
	private IBdChatService chatServie;
		
	
	//条件查询
	@RequestMapping(value="/queryChatList", method = RequestMethod.POST)
	@ResponseBody
	public List<BdMsgChat> queryChatList(HttpServletRequest request,BdMsgChat model){

		BdMsgChat bdMsgChat = new BdMsgChat();
//		
//		if(request.getParameter("sendTime").equals(""))
//			bdMsgChat.setFromPhone(null);
//		else
//			bdMsgChat.setFromPhone(request.getParameter("sendTime"));
//		
//		if(request.getParameter("recvTime").equals(""))
//			bdMsgChat.setRecvTime(null);
//		else
//			bdMsgChat.setRecvTime(request.getParameter("recvTime"));
		
		if(request.getParameter("fromPhone").equals(""))
			bdMsgChat.setFromPhone(null);
		else
			bdMsgChat.setFromPhone("fromPhone");
		
		if(request.getParameter("toPhone").equals(""))
			bdMsgChat.setToPhone(null);
		else
			bdMsgChat.setToPhone(request.getParameter("toPhone"));
		
		
		List<BdMsgChat> list = chatServie.queryChatList(bdMsgChat);
		
		return list;
	}
	
	@RequestMapping("/toChatList")
	 public String toChatList(HttpServletRequest request,Model model){

	  return "chat/chatList";
	 }
	
	//查询所有
	@RequestMapping(value="/showChatList", method = RequestMethod.POST)
	@ResponseBody
	public List<BdMsgChat> showChatList(HttpServletRequest request,BdMsgChat model){
		
		List<BdMsgChat> list = chatServie.showChatList();
		
		return list;
	}
	
	//找到所有人信息，并且跳转到发送信息页面
	@RequestMapping("/toSendMsg")
	public String toSendMsg(HttpServletRequest request,BdMsgChat record,ModelMap retMap){

		List<Map<String,Object>> list = this.findAll(request, record);
		retMap.addAttribute("BdMsgChat", list);
		return "chat/chatEdit";
	}
	
	//向bd_msg_chat表插入向船员发送的数据
	@RequestMapping(value="/toInsertMsg", method = RequestMethod.POST)
	@ResponseBody
	public int toInsertMsg(HttpServletRequest request) {
		
		//实验用的List
		List<BdMsgChat> myList = new ArrayList<>();
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		for(int i=0; i<3; i++) {
//			BdMsgChat bdMsgChat = new BdMsgChat();
//			bdMsgChat.setSendTime(new Date());
//			bdMsgChat.setMsgType("1");
//			bdMsgChat.setMsgTxt("测试数据");
//			bdMsgChat.setFromPhone("数据中心");
//			bdMsgChat.setMsgFrom("数据中心");
//			bdMsgChat.setToPhone("1311311313131");	
//			myList.add(bdMsgChat);
//		}
		
		
		return 1;
	}
	
	//找到所有人的信息
	@RequestMapping(value = "/findAll",method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> findAll(HttpServletRequest request,BdMsgChat record){
				
		List<Map<String, Object>> list = chatServie.findAll();
		
		return list;
	}
}
