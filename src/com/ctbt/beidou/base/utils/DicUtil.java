package com.ctbt.beidou.base.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.ctbt.beidou.alarm.service.IBdAlarmService;
import com.ctbt.beidou.base.bo.KeyValue;
import com.ctbt.beidou.base.model.BdPerm;
import com.ctbt.beidou.base.model.SysRegion;
import com.ctbt.beidou.base.service.IDicService;
import com.ctbt.beidou.perm.service.IBdPermService;

@Component
public class DicUtil {

	private static Logger logger = LogManager.getLogger(DicUtil.class);

	private static DicUtil instance = new DicUtil();

	private static Map<String, List<KeyValue>> dicMap = new HashMap<String, List<KeyValue>>();
	private static  Map<Integer,Map<String,Object>> middleMap = new HashMap<>();
	public static List<Map<String,Object>> sysRegionList = null;
	public static HashMap<Integer,Map<String,Object>> sysRegionMap = new HashMap<Integer,Map<String,Object>>();
	public static List<BdPerm> permList = new ArrayList<>();
	public static List<BdPerm> AllpermList = new ArrayList<>();
	public static DicUtil getInstance() {
		return instance;
	}

	public synchronized void putDic(String dicId, List<KeyValue> dataList) {
		if(dicMap.containsKey(dicId)){
			dicMap.remove(dicId);
		}

		dicMap.put(dicId, dataList);
	}

	public void loadAllDic() {
		IDicService dicService = (IDicService) SpringContextUtil.getBean("dicService");
		//获取bd_perm表所有数据
		IBdPermService permService = (IBdPermService)SpringContextUtil.getBean("permService");
		List<BdPerm> DBpermList = permService.selectAllTable();
		for (BdPerm bdPerm : DBpermList) {
			if (bdPerm.getPermLevel()==1) {
				permList.add(bdPerm);
			}
			AllpermList.add(bdPerm);
		}
		dicMap = dicService.getAllDic();
		
		querySysRegionTree();//加载行政区划树
	}

	public void loadDic(Integer dicId) {
		IDicService dicService = (IDicService) SpringContextUtil.getBean("dicService");
		
	
		dicMap.put(dicId.toString(), dicService.loadDic(dicId));
	}

	/**
	 * 根据字典编号得到字典内容列表。
	 * @param dicId 字典编号
	 * @return 字典内容列表
	 */
	public static List<KeyValue> getDic(String dicId) {
		if(dicMap.containsKey(dicId) == false){
			logger.error("该字典不存在。 dic_id = " + dicId);
			return new ArrayList<KeyValue>();
		}

		return (List<KeyValue>) dicMap.get(dicId);
	}

	/**
     * 根据传入的字典编号和键值得到描述信息。
     * 
     * <p>
     * 
     * @param dicId
     *                字典编号
     * @param itemKey
     *                键值
     * 
     * @return 描述信息
     */
	public static String getItemValue(String dicId, Object itemKey) {
		if(itemKey == null){
			return null;
		}

		List dicList = getDic(dicId);
		String itemValue = "";
		if(dicList != null && dicList.size() > 0){
			KeyValue kv = null;
			for(int k = 0; k < dicList.size(); k++){
				kv = (KeyValue) dicList.get(k);
				if(itemKey.equals(kv.getKey())){
					itemValue = kv.getValue();
					break;
				}
			}
		}

		if(StringUtils.isEmpty(itemValue)){
			return itemKey.toString();
		}else{
			return itemValue;
		}
	}
	
	public List<Map<String,Object>> querySysRegionTree() {
		
		if(sysRegionList == null) {

			IDicService dicService = (IDicService) SpringContextUtil.getBean("dicService");
			List<SysRegion> sysRegList = dicService.querySysRegionTree();
			//构造树
			sysRegionMap.clear();
			sysRegionList = DicUtil.getInstance().arrangeSysRegionTree(sysRegList);
		}
		
		return sysRegionList;
	}
	
	/**
	 * 整理行政区划树
	 * @param sreg
	 * @param sysRegList
	 * @return
	 */
	private int count;
	private List<Map<String,Object>> arrangeSysRegionTree(List<SysRegion> sysRegList){
		logger.debug("arrangeSysRegionTree count:"+(count++));
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		SysRegion sysreg = null;
		for(int k = 0; k < sysRegList.size(); k++) {
			sysreg = sysRegList.get(k);
			Map<String,Object> node = new HashMap<String,Object>();
			node.put("id", sysreg.getRegId());
			node.put("text", sysreg.getRegName());
			node.put("level", sysreg.getLevel().intValue());
			node.put("pid", sysreg.getParentId());
			node.put("countryId", sysreg.getCountryId());
			node.put("publicId", sysreg.getPublicId());
			node.put("regCode", sysreg.getRegCode());
			node.put("type", "SysRegion");
			
			Map<String,Object> parent = sysRegionMap.get(sysreg.getParentId());
			if(parent != null) {
				//找到父节点
				List<Map<String,Object>> childList = (List<Map<String,Object>>) parent.get("children");
				if(childList == null) {
					childList = new ArrayList<Map<String,Object>>();
					parent.put("children", childList);
				}
								
				childList.add(node);
			}else {
				//没找到父节点，就当一级节点		
				//
				list.add(node);					
			}			
			sysRegionMap.put(sysreg.getRegId(), node);//添加节点索引
		}
		
		return list;
	}
	
	private Map<String,Object> findSysRegionTreeParentNode(SysRegion sysreg, List<Map<String,Object>> list){
		logger.debug("findSysRegionTreeParentNode count:"+(count++)+"   sysreg:"+sysreg.toString());
		Integer parentId = sysreg.getParentId();
		Integer level = sysreg.getLevel();
		if(parentId == null) {
			//父节点为空，不可能找到的，一般是第一级节点
			return null;
		}
		
		Map<String,Object> node = null;
		Map<String,Object> nodeChild = null;
		Integer regId = null;
		List<Map<String,Object>> childList = null;
		for(int k = 0; k < list.size(); k++) {
			node = list.get(k);
			regId = (Integer)node.get("regId");
			if(regId.intValue() == parentId.intValue()) {
				//找到父节点 就返回
				return node;
			}else {
				//该节点不是，往它的子节点中找
				childList = (List<Map<String,Object>>) node.get("children");
				if(childList != null) {
					nodeChild = findSysRegionTreeParentNode(sysreg, childList);
					if(nodeChild != null) {
						//找到了，就返回
						return nodeChild;
					}
				}
			}
		}
		
		return null;
	}
}
