package com.ctbt.beidou.perm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.dao.BdPermMapper;
import com.ctbt.beidou.base.dao.BdRoleMapper;
import com.ctbt.beidou.base.dao.BdUserMapper;
import com.ctbt.beidou.base.dao.BdUserPermMapper;
import com.ctbt.beidou.base.model.BdPerm;
import com.ctbt.beidou.base.model.BdRole;
import com.ctbt.beidou.base.model.BdUser;
import com.ctbt.beidou.base.model.BdUserPerm;
import com.ctbt.beidou.base.model.BdUserPermKey;
import com.ctbt.beidou.base.model.SysRegion;
import com.ctbt.beidou.base.utils.DicUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Transactional
@Service("permService")
public class BdPermServiceImpl implements IBdPermService {
	@Resource
	private BdPermMapper bdPermMapper;

	@Resource
	private BdUserPermMapper bdUserPermMapper;
	
	@Resource
	private BdUserMapper bdUserMapper;
	
	@Override
	public BdRole selectByPrimaryKey(Integer PermId) {
		// TODO 自动生成的方法存根
		return null;
	}
	@Override
	public int deleteByPrimaryKey(Integer roleId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(BdPerm record) {
		return this.bdPermMapper.insert(record);
	}

	@Override
	public int insertSelective(BdPerm record) {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public int updateByPrimaryKeySelective(BdPerm record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(BdPerm record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResultView saveBdRoleEdit(BdPerm record) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public List<Map<String, Object>> queryRolePermTree(Integer urId) {
		// TODO 自动生成的方法存根
		List<Map<String, Object>> rolePermList = bdPermMapper.queryRolePermTreeByRoleid(urId);
		return rolePermList;
	}

	

	@Override
	public List<Map<String, Object>> arrangeRolePermTree(List<Map<String, Object>> rolePermListData) {
		// TODO 自动生成的方法存根
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();	
		for (int i = 0; i < rolePermListData.size(); i++) {
			Map<String, Object> record = rolePermListData.get(i);
			Map<String,Object> node = new HashMap<String,Object>();
			node.put("id", record.get("perm_id"));
			node.put("pid", record.get("parent_id"));
			node.put("text", record.get("perm_name"));
			node.put("level", record.get("perm_level"));
			node.put("validaty", record.get("validity"));
			node.put("type", record.get("perm_type"));
			node.put("url", record.get("perm_url"));
			node.put("order", record.get("perm_order"));
			if (record.get("ur_id")!=null) {
				node.put("ischecked", true);
			}
			//将节点放入list
			list.add(node);
		}
		return list;
	}
	
	@Override
	public String updateRolePermTree(String data,Integer roleId) {
		// TODO 自动生成的方法存根
		List<Map<String, Object>> rolePermListData = bdUserPermMapper.selectByroleId(roleId);
		//数据库中的该角色列表
		List<Map<String, Object>> selectedData= new ArrayList<>();
		//前端的该角色列表
		HashMap<String, Object> hashMapInFrontEnd = new HashMap<>();
		HashMap<String, Object> hashMapInDB = new HashMap<>();
		JSONArray JsonArray = JSONArray.fromObject(data); 
		for (int i = 0; i < JsonArray.size(); i++) {
			HashMap<String, Object> JsonObjMap = new HashMap<>();
			JSONObject dataObj = JsonArray.getJSONObject(i).getJSONObject("data");
			if (dataObj.get("children") == null) {//只保存叶子节点
				hashMapInFrontEnd.put(dataObj.get("id").toString(),dataObj.get("type"));
				JsonObjMap.put("id", dataObj.get("id"));
				JsonObjMap.put("urId", roleId);
				JsonObjMap.put("permType", dataObj.get("type"));
				selectedData.add(JsonObjMap);
			}	
		}
		System.out.println("Hello");
		//删除操作
		for (int i = 0; i < rolePermListData.size(); i++) {
			hashMapInDB.put(rolePermListData.get(i).get("perm_id").toString(), rolePermListData.get(i).get("perm_type").toString());
			if (hashMapInFrontEnd.containsKey(rolePermListData.get(i).get("perm_id").toString())) {
				continue;
			}
			//删除这条 
			BdUserPermKey bdUserPermKey = new BdUserPermKey();
			bdUserPermKey.setPermId(Integer.valueOf(rolePermListData.get(i).get("perm_id").toString()));
			bdUserPermKey.setPermType(rolePermListData.get(i).get("perm_type").toString());
			bdUserPermKey.setUrId(Integer.valueOf(rolePermListData.get(i).get("ur_id").toString()));
			bdUserPermMapper.deleteByPrimaryKey(bdUserPermKey);
		}
		//添加操作
		System.out.println("Hello");
		for (int i = 0; i < selectedData.size(); i++) {
			if (hashMapInDB.containsKey(selectedData.get(i).get("id").toString())) {
				continue;
			}
			BdUserPermKey bdUserPermKey = new BdUserPermKey();
			bdUserPermKey.setUrId(roleId);
			bdUserPermKey.setPermId(Integer.valueOf(selectedData.get(i).get("id").toString()));
			bdUserPermKey.setPermType(selectedData.get(i).get("permType").toString());
			//插入数据
			bdUserPermMapper.insert(bdUserPermKey);
			
		}

		return "failed";
	}

}
