package com.ctbt.beidou.phonemsg.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/phonemsg")
public class PhonemsgController {

	private Logger logger = LogManager.getLogger(getClass());

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
