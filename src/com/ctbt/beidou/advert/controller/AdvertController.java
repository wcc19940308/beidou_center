package com.ctbt.beidou.advert.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@RequestMapping("/advert")
public class AdvertController {

	private Logger logger = Logger.getLogger(getClass());

	@RequestMapping("/toAdvertList")
	public String toAdvertList(HttpServletRequest request,Model model){

		return "advert/advertList";
	}
	
	@RequestMapping("/queryAdvertList")
	public String queryAdvertList(HttpServletRequest request,Model model){

		return "advert/advertList";
	}
	
	@RequestMapping("/toAdvertEdit")
	public String toAdvertEdit(HttpServletRequest request,Model model){

		return "advert/advertEdit";
	}
}
