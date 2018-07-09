package com.ctbt.beidou.phonemsg.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@RequestMapping("/phonemsg")
public class PhonemsgController {

	private Logger logger = Logger.getLogger(getClass());

	@RequestMapping("/toPhonemsgList")
	public String toPhonemsgList(HttpServletRequest request,Model model){

		return "phonemsg/phonemsgList";
	}

	@RequestMapping("/queryPhonemsgList")
	public String queryPhonemsgList(HttpServletRequest request,Model model){

		return "phonemsg/phonemsgList";
	}

	@RequestMapping("/toPhonemsgEdit")
	public String toPhonemsgEdit(HttpServletRequest request,Model model){

		return "phonemsg/phonemsgEdit";
	}
}
