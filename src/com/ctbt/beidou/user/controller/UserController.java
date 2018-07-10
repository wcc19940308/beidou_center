package com.ctbt.beidou.user.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
import com.ctbt.beidou.base.dao.BdUserMapper;
import com.ctbt.beidou.base.model.BdUser;
import com.ctbt.beidou.base.model.BdUserDetail;
import com.ctbt.beidou.base.utils.EncryptUtil;
import com.ctbt.beidou.user.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private Logger logger = LogManager.getLogger(getClass());
	
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
	
	@RequestMapping("/login") //��½
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
	
	@RequestMapping("/toUserEdit") //��ʾUserEdit����
	public String toUserEdit(HttpServletRequest request,BdUser bdUser,ModelMap retMap){
		Integer userId = null;
		BdUserDetail bdUserDetail = null;
		//��ȥ��ѯ���ݿ���û������˵�id ��������޸ģ����û�������
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
	@RequestMapping("/saveUserAdd") //����û�
	@ResponseBody
	public int saveUserAdd(HttpServletRequest request,BdUser bdUser,BdUserDetail bdUserDetail){
		int IsAddSuccess = 0;
		//�ж�����ID��Ϊ�ղſ��Խ���
		bdUser.setPassword(EncryptUtil.Md5(bdUser.getPassword()));
		IsAddSuccess = userService.addUser(bdUser,bdUserDetail);
		return IsAddSuccess;
	}
	
	@RequestMapping("/saveUserEdit") //�޸��û�������Map
	@ResponseBody
	public int saveUserEdit(HttpServletRequest request,BdUser bdUser,BdUserDetail bdUserDetail){
		int IsAddSuccess = 0;
		//�ж�����ID�Ƿ�ͬʱ��Ϊ��
		if(bdUser.getPassword()!=null) {
			bdUser.setPassword(EncryptUtil.Md5(bdUser.getPassword()));//�����޸�����
		}
		IsAddSuccess = userService.updateUser(bdUser,bdUserDetail);
		return IsAddSuccess;
	}
	@RequestMapping("/saveUserDelete") //ɾ���û�������Map
	@ResponseBody
	public int saveUserDelete(HttpServletRequest request,BdUser bdUser,BdUserDetail bdUserDetail){
		int IsAddSuccess = 0;
		//�ж�����ID�Ƿ�ͬʱ��Ϊ��
		IsAddSuccess = userService.deleteUser(bdUser,bdUserDetail);
		return IsAddSuccess;
	}
	
	@RequestMapping("/saveUserPasswordEdit") //�޸��û����룬����Map
	@ResponseBody
	public int saveUserPasswordEdit(HttpServletRequest request,BdUser bdUser,BdUserDetail bdUserDetail){
		int IsAddSuccess = 0;
		//�ж�����ID�Ƿ�ͬʱ��Ϊ��
		IsAddSuccess = userService.deleteUser(bdUser,bdUserDetail);
		return IsAddSuccess;
	}
	
}
