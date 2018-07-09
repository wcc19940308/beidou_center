package com.ctbt.beidou.role.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.dao.BdRoleMapper;
import com.ctbt.beidou.base.dao.BdUserMapper;
import com.ctbt.beidou.base.model.BdRole;
import com.ctbt.beidou.base.model.BdUser;

@Transactional
@Service("roleService")
public class BdRoleServiceImpl implements IBdRoleService {
	@Resource
	private BdRoleMapper bdRoleMapper;

	@Resource
	private BdUserMapper bdUserMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer roleId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(BdRole record) {
		return this.bdRoleMapper.insert(record);
	}

	@Override
	public int insertSelective(BdRole record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BdRole selectByPrimaryKey(Integer roleId) {
		return this.bdRoleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	public int updateByPrimaryKeySelective(BdRole record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(BdRole record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResultView saveBdRoleEdit(BdRole record) {
		Integer roleId = record.getRoleId();
		if(roleId == null) {
			//主键为空，新增
			record.setRoleId(Integer.valueOf(112));
			bdRoleMapper.insert(record);
		}else {
			//有主键，修改
			bdRoleMapper.updateByPrimaryKey(record);
			
		}
		
		return new ResultView("1","",record);
	}

	@Override
	public List<Map<String,Object>> queryRoleList(){
		List<Map<String,Object>> list = bdRoleMapper.selectByCondition();
		return list;
	}
}
