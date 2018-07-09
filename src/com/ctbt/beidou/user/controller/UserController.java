package com.ctbt.beidou.user.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctbt.beidou.base.bo.BdUserBo;
import com.ctbt.beidou.base.dao.BdUserMapper;
import com.ctbt.beidou.base.model.BdUser;
import com.ctbt.beidou.base.model.BdUserDetail;
import com.ctbt.beidou.base.utils.EncryptUtil;
import com.ctbt.beidou.user.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private Logger logger = Logger.getLogger(getClass());
	
	@Resource
	private IUserService userService;
	
	@RequestMapping("/toLogin")
	public String toLogin(HttpServletRequest request, Model model){
		logger.info("user toLogin");
		ServletContext application = request.getServletContext();
        String WebUrl = (String)application.getAttribute("WebUrl");
        if("".equals(StringUtils.trimToEmpty(WebUrl))) {
        	WebUrl = request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort() +request.getContextPath();
        	application.setAttribute("WebUrl", WebUrl);
        }
		
		return "login";
	}
	
	@RequestMapping("/toWelcome")
	public String toWelcome(HttpServletRequest request, Model model){
		
		return "welcome";
	}
	
	@RequestMapping("/login") //登陆
	public String login(HttpServletRequest request,BdUser bdUser, Model model){
//		BdUser bdUserInDB= userService.selectByPrimaryKey(bdUser.getUserId());
//		if(EncryptUtil.Md5(bdUser.getPassword())==bdUser.getPassword()) {
//			return "success";
//		}
		return "main";
	}
	
	@RequestMapping("/toUserList")
	public String toUserList(HttpServletRequest request,Model model){
//		int userId = Integer.parseInt(request.getParameter("id"));
//		BdUser user = this.userService.getUserById(userId);
//		model.addAttribute("user", user);
		return "user/userList";
	}
	
	@RequestMapping(value = "/queryUserList", method = RequestMethod.POST)
	@ResponseBody
	public List<BdUserBo> queryUserList(HttpServletRequest request, BdUserBo bdUserBo,ModelMap retMap){
		System.out.println(bdUserBo.getRoleName());
		List<BdUserBo> list = userService.queryUserList(bdUserBo);
		return list;
	}
	@RequestMapping("/toUserAdd")
	public String toUserAdd(HttpServletRequest request,BdUser bdUser,BdUserDetail bdUserDetail){
		return "user/userAdd";
	}
	
	@RequestMapping("/toUserEdit") //显示UserEdit界面
	public String toUserEdit(HttpServletRequest request,BdUser bdUser,ModelMap retMap){
		Integer userId = null;
		BdUserDetail bdUserDetail = null;
		//先去查询数据库有没有这个人的id 如果有则修改，如果没有则添加
		if(bdUser != null) {
			userId = bdUser.getUserId();
		}
		if(userId != null) {
			bdUser = userService.selectByPrimaryKey(userId);
			bdUserDetail = userService.selectDetailByPrimaryKey(userId);
			System.out.println("QQ:"+bdUserDetail.getAddr());
		}
		retMap.addAttribute("BdUser",bdUser);
		retMap.addAttribute("BdUserDetail",bdUserDetail);
		
		return "user/userEdit";
	}
	@RequestMapping("/saveUserAdd") //添加用户
	@ResponseBody
	public int saveUserAdd(HttpServletRequest request,BdUser bdUser,BdUserDetail bdUserDetail){
		int IsAddSuccess = 0;
		//判断两者ID不为空才可以仅需
		bdUser.setPassword(EncryptUtil.Md5(bdUser.getPassword()));
		IsAddSuccess = userService.addUser(bdUser,bdUserDetail);
		return IsAddSuccess;
	}
	
	@RequestMapping("/saveUserEdit") //修改用户，返回Map
	@ResponseBody
	public int saveUserEdit(HttpServletRequest request,BdUser bdUser,BdUserDetail bdUserDetail){
		int IsAddSuccess = 0;
		//判断两者ID是否同时不为空
		if(bdUser.getPassword()!=null) {
			bdUser.setPassword(EncryptUtil.Md5(bdUser.getPassword()));//重新修改密码
		}
		IsAddSuccess = userService.updateUser(bdUser,bdUserDetail);
		return IsAddSuccess;
	}
	@RequestMapping("/saveUserDelete") //删除用户，返回Map
	@ResponseBody
	public int saveUserDelete(HttpServletRequest request,BdUser bdUser,BdUserDetail bdUserDetail){
		int IsAddSuccess = 0;
		//判断两者ID是否同时不为空
		IsAddSuccess = userService.deleteUser(bdUser,bdUserDetail);
		return IsAddSuccess;
	}
	
	@RequestMapping("/saveUserPasswordEdit") //修改用户密码，返回Map
	@ResponseBody
	public int saveUserPasswordEdit(HttpServletRequest request,BdUser bdUser,BdUserDetail bdUserDetail){
		int IsAddSuccess = 0;
		//判断两者ID是否同时不为空
		IsAddSuccess = userService.deleteUser(bdUser,bdUserDetail);
		return IsAddSuccess;
	}
	
}
