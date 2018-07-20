package com.ctbt.beidou.user.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.bo.BdUserBo;
import com.ctbt.beidou.base.dao.BdUserDetailMapper;
import com.ctbt.beidou.base.dao.BdUserLoginLogMapper;
import com.ctbt.beidou.base.dao.BdUserLoginSessionMapper;
import com.ctbt.beidou.base.dao.BdUserMapper;
import com.ctbt.beidou.base.model.BdUser;
import com.ctbt.beidou.base.model.BdUserDetail;
import com.ctbt.beidou.base.model.BdUserLoginLog;
import com.ctbt.beidou.base.model.BdUserLoginSession;
import com.ctbt.beidou.base.utils.EncryptUtil;
import com.ctbt.beidou.user.service.IUserService;

@Transactional
@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private BdUserMapper bdUserMapper;
	@Resource
	private BdUserDetailMapper bdUserDetailMapper;
	@Resource
	BdUserLoginLogMapper bdUserLoginLogMapper;
	@Resource
	BdUserLoginSessionMapper bdUserLoginSessionMapper;

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
	public List queryUserList(BdUser bdUser) {
		// TODO Auto-generated method stub
		List<?> list = bdUserMapper.selectByCondition(bdUser);
		System.out.println(list.size());

		return list;
	}

	@Override
	public int addUser(BdUser bdUser, BdUserDetail bdUserDetail) {
		// TODO  Auto-generated method stub
		//必要字段用户密码
		int IsSucess = 0;
		if (bdUser.getPassword()==null) {bdUser.setPassword(EncryptUtil.Md5("111111"));}
		if (bdUser.getPassword()!=null && bdUser.getPhone()!=null &&
				(Integer)bdUser.getRoleId()!=null &&bdUser.getValidity()!=null) {
			bdUser.setPassword(EncryptUtil.Md5(bdUser.getPassword()));
			int user_id = bdUserMapper.getMaxUserID();
			bdUser.setUserId(user_id+1);
			bdUserDetail.setUserId(user_id+1);
			if (bdUserMapper.insert(bdUser) == 1 && bdUserDetailMapper.insert(bdUserDetail) == 1) {
				IsSucess = 1;
			}else {
				IsSucess = 0;
			}
		} else {
			IsSucess = 0; 
		}
		return IsSucess;
	}

	@Override
	public int updateUser(BdUser bdUser, BdUserDetail bdUserDetail) {
		// TODO Auto-generated method stub
		int IsSucess = 0;
		if (bdUserMapper.updateByPrimaryKeySelective(bdUser) == 1 && bdUserDetailMapper.updateByPrimaryKey(bdUserDetail) == 1) {
			IsSucess = 1;
		}    
		return IsSucess;
	}

	@Override
	public int deleteUser(BdUser bdUser, BdUserDetail bdUserDetail) {
		// TODO Auto-generated method stub
		int IsSucess = 0;
		if (bdUserMapper.deleteByPrimaryKey(bdUser.getUserId()) == 1
				&& bdUserDetailMapper.deleteByPrimaryKey(bdUserDetail.getUserId()) == 1) {
			IsSucess = 1;
		}
		System.out.println(
				"deleteUserAndDetail------------------------------------------------------------------" + IsSucess);
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
		System.out.println("���ǲ���1��" + userId);
		BdUserDetail bdUserDetail = bdUserDetailMapper.selectByPrimaryKey(userId);

		return bdUserDetail;
	}

	@Override
	public BdUser selectByModel(BdUser record) {
		BdUser bdUser = null;
		// TODO Auto-generated method stub
		if (record.getPassword() != null) {
			if (record.getUserId()==null) {
				record.setUserId(-1);//如果没有UserID 那就把用户ID设置为无法查找到的条件
			}if (record.getPhone()==null) {
				record.setPhone("");//如果没有UserID 那就把用户ID设置为无法查找到的条件
			}
			record.setPassword(EncryptUtil.Md5(record.getPassword()));
			bdUser = bdUserMapper.selectByModel(record);
		} else {
			bdUser = bdUserMapper.selectByModel(record);
		}

		return bdUser;
	}

	@Override
	public int insertBdUserLoginLog(BdUserLoginLog bdUserLoginLog) {
		// TODO Auto-generated method stub
		if (bdUserLoginLog.getUserId() != null && bdUserLoginLog.getDeviceId() != null) {
			bdUserLoginLogMapper.insertSelective(bdUserLoginLog);
			return 1;
		} else {
			return 0;
		}
	}


	@Override
	public int updateBdUserLoginSession(BdUserLoginSession bdUserLoginSession) {
		// TODO Auto-generated method stub
		//通过user_id和login_device_id去查询是否有这条记录，如果有就覆盖。没有则插入。
		int flag=0;
		if (bdUserLoginSession.getUserId() != null && bdUserLoginSession.getDeviceId() != null) {
			Date time = new java.sql.Date(new java.util.Date().getTime());
			bdUserLoginSession.setLoginTime(time);//设置登陆时间
			if (bdUserLoginSessionMapper.selectByPrimaryKey(bdUserLoginSession)==null) {
				bdUserLoginSessionMapper.insert(bdUserLoginSession);
				flag=1;
			} else {
				bdUserLoginSessionMapper.updateByPrimaryKey(bdUserLoginSession);
				flag =1;
			}
		} 
		return flag;
	}

	@Override
	public int deleteUserSession(String sessionId) {
		// TODO 自动生成的方法存根
		int sessionDeleteFlag = 0;
		sessionDeleteFlag = bdUserLoginSessionMapper.deleteBysessionId(sessionId);
		return sessionDeleteFlag;
	}

}
