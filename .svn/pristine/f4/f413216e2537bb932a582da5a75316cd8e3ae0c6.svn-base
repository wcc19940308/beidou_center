package com.ctbt.beidou.sysdic.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctbt.beidou.base.bo.BdUserBo;
import com.ctbt.beidou.base.dao.BdUserLoginLogMapper;
import com.ctbt.beidou.base.dao.BdUserMapper;
import com.ctbt.beidou.base.model.BdUser;
import com.ctbt.beidou.base.model.BdUserDetail;
import com.ctbt.beidou.base.model.BdUserLoginLog;
import com.ctbt.beidou.base.model.BdUserLoginSession;
import com.ctbt.beidou.base.model.SysDic;
import com.ctbt.beidou.base.utils.EncryptUtil;
import com.ctbt.beidou.sysdic.service.ISysDicService;
import com.ctbt.beidou.user.service.IUserService;

@Controller
@RequestMapping("/sysdic")
public class SysDicController {

	private Logger logger = LogManager.getLogger(getClass());

	@Resource
	private ISysDicService sysdicservice;

	@RequestMapping("/toSysdic")
	public String toWelcome(HttpServletRequest request, Model model) {

		return "sysdic/sysdicList";
	}


	@RequestMapping(value = "/selectAll", method = RequestMethod.POST)
	@ResponseBody
	public List<SysDic> selectAll(HttpServletRequest request, ModelMap retMap) {
		List<SysDic> list = sysdicservice.selectAll();
		return list;
	}
	
	@RequestMapping(value = "/selectAllByItem", method = RequestMethod.POST)
	@ResponseBody
	public List<SysDic> selectAll(HttpServletRequest request, SysDic sysDic, ModelMap retMap) {
		List<SysDic> list = sysdicservice.selectAllByItem(sysDic);
		return list;
	}

	

}
