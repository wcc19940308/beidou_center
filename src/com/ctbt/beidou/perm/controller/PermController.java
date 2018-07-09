package com.ctbt.beidou.perm.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@RequestMapping("/perm")
public class PermController {

	private Logger logger = Logger.getLogger(getClass());

	@RequestMapping("/toPermList")
	public String toPermList(HttpServletRequest request,Model model){

		return "perm/permList";
	}

	@RequestMapping("/queryPermList")
	public String queryPermList(HttpServletRequest request,Model model){

		return "perm/permList";
	}

	@RequestMapping("/toPermEdit")
	public String toPermEdit(HttpServletRequest request,Model model){

		return "perm/permEdit";
	}
}
