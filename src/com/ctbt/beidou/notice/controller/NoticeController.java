package com.ctbt.beidou.notice.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	private Logger logger = Logger.getLogger(getClass());
		
	@RequestMapping("/toNoticeList")
	public String toNoticeList(HttpServletRequest request,Model model){

		return "notice/noticeList";
	}
	
	@RequestMapping("/queryNoticeList")
	public String queryNoticeList(HttpServletRequest request,Model model){

		return "notice/noticeList";
	}
	
	@RequestMapping("/toNoticeEdit")
	public String toNoticeEdit(HttpServletRequest request,Model model){

		return "notice/noticeEdit";
	}
}
