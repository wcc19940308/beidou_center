package com.ctbt.beidou.news.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@RequestMapping("/news")
public class NewsController {

	private Logger logger = Logger.getLogger(getClass());
		
	@RequestMapping("/toNewsList")
	public String toNewsList(HttpServletRequest request,Model model){

		return "news/newsList";
	}
	
	@RequestMapping("/queryNewsList")
	public String queryNewsList(HttpServletRequest request,Model model){

		return "news/newsList";
	}
	
	@RequestMapping("/toNewsEdit")
	public String toNewsEdit(HttpServletRequest request,Model model){

		return "news/newsEdit";
	}
}
