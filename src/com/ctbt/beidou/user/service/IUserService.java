package com.ctbt.beidou.user.service;

import java.util.List;

import com.ctbt.beidou.base.bo.BdUserBo;
import com.ctbt.beidou.base.model.BdUser;
import com.ctbt.beidou.base.model.BdUserDetail;

public interface IUserService {
	public BdUser getUserById(int userId);
	int saveBdUser(BdUser record);

	public List<BdUserBo> queryUserList(BdUserBo bdUserBo);
//	public Boolean addUser();
	public BdUser selectByPrimaryKey(Integer userId);
	public BdUserDetail selectDetailByPrimaryKey(Integer userId);
	public int addUser(BdUser bdUser, BdUserDetail bdUserDetail);
	int updateUser(BdUser bdUser, BdUserDetail bdUserDetail);
	int deleteUser(BdUser bdUser,BdUserDetail bdUserDetail);
}
