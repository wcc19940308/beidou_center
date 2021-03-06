package com.ctbt.beidou.base.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.client.utils.CloneUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ctbt.beidou.base.bo.KeyValue;
import com.ctbt.beidou.base.dao.BdShipMapper;
import com.ctbt.beidou.base.dao.SysDicMapper;
import com.ctbt.beidou.base.dao.SysRegionMapper;
import com.ctbt.beidou.base.model.BdShip;
import com.ctbt.beidou.base.model.SysDic;
import com.ctbt.beidou.base.model.SysRegion;
import com.ctbt.beidou.base.utils.DicUtil;

@Service("dicService")
public class DicServiceImpl implements IDicService{

	private Logger logger = LogManager.getLogger(getClass());

	@Resource
	private SysDicMapper sysDicMapper;

	@Resource
	private SysRegionMapper sysRegionMapper;

	@Resource
	private BdShipMapper bdShipMapper;
	
	/**
     * 获得所有的字典，map<dicId,map<key,value>>
     * 
     * @return
     */
	public Map<String, List<KeyValue>> getAllDic() {
		Map<String, List<KeyValue>> allDicMap = new HashMap<String, List<KeyValue>>();
		List<SysDic> list = sysDicMapper.findAllValidity();
		if(list != null && list.size() > 0){
			SysDic sysDic;
			List<KeyValue> dicList;
			for(int k = 0; k < list.size(); k++){
				sysDic = (SysDic) list.get(k);
				dicList = this.loadDic(sysDic);
				if(dicList != null){
					allDicMap.put(sysDic.getDicId().toString(), dicList);
				}
			}
		}

		return allDicMap;
	}

	public List<KeyValue> loadDic(SysDic sysDic) {
		String dicSql = sysDic.getDicSql();
		String sql = "Select "+ sysDic.getKeyColumn() + " item_key, "+sysDic.getValueColumn()+" item_value from "+sysDic.getTableName();
		if(dicSql.startsWith("ORDER") || dicSql.startsWith("GROUP")) {
			sql += " "+sysDic.getDicSql();
		}else {
			sql += " where "+sysDic.getDicSql();
		}
		
		return sysDicMapper.queryDicKeyValueListBySql(sql);
	}
	

	/**
     * 根据字典号，返回一个字典，用于重新加载某一个 字典
     * 
     * @param dicId
     * @return
     */
	public List<KeyValue> loadDic(Integer dicId) {
		return sysDicMapper.queryDicKeyValueListById(dicId);
	}

	@Override
	public List<KeyValue> queryCountryList() {
		return sysRegionMapper.queryCountryList();
	}
	
	@Override
	public List<KeyValue> queryProvinceList(Integer countryId) {
		return sysRegionMapper.queryProvinceList(countryId);
	}

	@Override
	public List<KeyValue> queryCityList(Integer regId) {
		return sysRegionMapper.queryCityList(regId);
	}

	@Override
	public List<KeyValue> queryCityAreaList(Integer regId) {
		return sysRegionMapper.queryCityAreaList(regId);
	}

	@Override
	public List<KeyValue> queryTownList(Integer regId) {
		return sysRegionMapper.queryTownList(regId);
	}

	@Override
	public List<KeyValue> queryVillageList(Integer regId) {
		return sysRegionMapper.queryVillageList(regId);
	}


	
	@Override
	public int insert(SysRegion record) {
		// TODO Auto-generated method stub
		return sysRegionMapper.insert(record);
	}

	@Override
	public Integer queryMax() {
		// TODO Auto-generated method stub
		return sysRegionMapper.queryMax();
	}


	@Override
	public List<SysRegion> querySysRegionTree() {
		return sysRegionMapper.querySysRegionTree();
	}

	@Override
	public SysRegion queryV(SysRegion record) {
		// TODO Auto-generated method stub
		return sysRegionMapper.queryV(record);
	}

	@Override
	public SysRegion queryT(SysRegion record) {
		// TODO Auto-generated method stub
		return sysRegionMapper.queryT(record);
	}

	@Override
	public List<Map<String,Object>> querySysRegionShipTree(int[] provinceIds) {
		HashMap<Integer,Map<String,Object>> sysRegionMap2 = new HashMap<Integer,Map<String,Object>>();
		try {
			sysRegionMap2 = (HashMap<Integer,Map<String,Object>>)CloneUtils.clone(DicUtil.getInstance().sysRegionMap);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

		List<Map<String,Object>> treeData = new ArrayList<Map<String,Object>>();
		List<BdShip> shipList = bdShipMapper.selectAll(null);
		
		Integer shipRid = null;
		for(BdShip ship : shipList) {
			
			if(ship.getVillage() != null) {
				shipRid = ship.getVillage();
			}else if(ship.getTown() != null) {
				shipRid = ship.getTown();
			}else if(ship.getCityArea() != null) {
				shipRid = ship.getCityArea();
			}else if(ship.getCity() != null) {
				shipRid = ship.getCity();
			}else if(ship.getProvince() != null) {
				shipRid = ship.getProvince();
			}
			
			Map<String,Object> parent = sysRegionMap2.get(shipRid);
			if(parent != null) {
				//找到父节点
				Integer plevel = (Integer)parent.get("level");
				Integer parentId = (Integer)parent.get("pid");
				Integer countryId = (Integer)parent.get("countryId");
				//Integer publicId = (Integer)parent.get("publicId");
				
				Map<String,Object> node = new HashMap<String,Object>();
				node.put("id", ship.getShipId());
				node.put("text", ship.getShipName());
				node.put("level", plevel+1);
				node.put("pid", parentId);
				node.put("countryId", countryId);
				//node.put("publicId", publicId);
				node.put("type", "BdShip");
				
				List<Map<String,Object>> childList = (List<Map<String,Object>>) parent.get("children");
				if(childList == null) {
					childList = new ArrayList<Map<String,Object>>();
					parent.put("children", childList);
				}
				
				boolean isExist = false;
				for(Map<String,Object> child : childList) {
					Integer cid = (Integer)child.get("id");
					String type = (String)child.get("type");
					if("BdShip".equals(type) && cid == ship.getShipId()) {
						isExist = true;
					}
				}

				if(!isExist) {
					childList.add(node);
				}
			}
		}
		
		for(int pid : provinceIds) {
			Map<String,Object> pmap = sysRegionMap2.get(pid);
			if(pmap != null) {
				treeData.add(pmap);
			}
		}
		
		return treeData;
	}
}
