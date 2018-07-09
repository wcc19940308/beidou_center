package com.ctbt.beidou.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.bo.BdUserBo;
import com.ctbt.beidou.base.dao.BdUserDetailMapper;
import com.ctbt.beidou.base.dao.BdUserMapper;
import com.ctbt.beidou.base.model.BdUser;
import com.ctbt.beidou.base.model.BdUserDetail;
import com.ctbt.beidou.user.service.IUserService;

@Transactional
@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private BdUserMapper bdUserMapper;
	@Resource
	private BdUserDetailMapper bdUserDetailMapper;
	@Override
	public BdUser getUserById(int userId) {
		return this.bdUserMapper.selectByPrimaryKey(userId);
	}

	@Override
	public int saveBdUser(BdUser record) {
		BdUser user = new BdUser();
		user.setUserId(Integer.valueOf(2));
		user.setPassword("Password");
		bdUserMapper.insert(user);
		return 0;
	}

	@Override
	public List<BdUserBo> queryUserList(BdUserBo bdUserBo) {
		// TODO Auto-generated method stub
		List<BdUserBo> list = bdUserMapper.selectByCondition(bdUserBo);
		System.out.println(list.size());
		
		return list;
	}

	@Override
	public int addUser(BdUser bdUser,BdUserDetail bdUserDetail) {
		// TODO 自动生成的方法存根
		int IsSucess = 0;
		if(bdUserMapper.insert(bdUser)==1&&bdUserDetailMapper.insert(bdUserDetail)==1)
		{
			IsSucess = 1;
		}
		System.out.println("isSucess------------------------------------------------------------------"
		+IsSucess);
		return IsSucess;
	}
	@Override
	public int updateUser(BdUser bdUser,BdUserDetail bdUserDetail) {
		// TODO 自动生成的方法存根
		int IsSucess = 0;
		if(bdUserMapper.insert(bdUser)==1&&bdUserDetailMapper.insert(bdUserDetail)==1)
		{
			IsSucess = 1;
		}
		System.out.println("isSucess------------------------------------------------------------------"
		+IsSucess);
		return IsSucess;
	}
	
	@Override
	public int deleteUser(BdUser bdUser,BdUserDetail bdUserDetail) {
		// TODO 自动生成的方法存根
		int IsSucess = 0;
		if(bdUserMapper.insert(bdUser)==1&&bdUserDetailMapper.insert(bdUserDetail)==1)
		{
			IsSucess = 1;
		}
		System.out.println("isSucess------------------------------------------------------------------"
		+IsSucess);
		return IsSucess;
	}

	@Override
	public BdUser selectByPrimaryKey(Integer userId) {
		// TODO Auto-generated method stub
		BdUser bdUser = bdUserMapper.selectByPrimaryKey(userId);
		return bdUser;
	}

	@Override
	public BdUserDetail selectDetailByPrimaryKey(Integer userId) {
		// TODO Auto-generated method stub
		System.out.println("这是测试1："+userId);
		BdUserDetail bdUserDetail = bdUserDetailMapper.selectByPrimaryKey(userId);
//		System.out.println("这是测试2："+bdUserDetail.getAddr());
		return bdUserDetail;
	}

}
