package com.ctbt.beidou.alarm.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@RequestMapping("/alarm")
public class AlarmController {

	private Logger logger = Logger.getLogger(getClass());
		
	@RequestMapping("/toAlarmList")
	public String toAlarmList(HttpServletRequest request,Model model){

		return "alarm/alarmList";
	}
	
	@RequestMapping("/queryAlarmList")
	public String queryAlarmList(HttpServletRequest request,Model model){

		return "alarm/alarmList";
	}
	
	@RequestMapping("/toAlarmEdit")
	public String toAlarmEdit(HttpServletRequest request,Model model){

		return "alarm/alarmEdit";
	}
}
