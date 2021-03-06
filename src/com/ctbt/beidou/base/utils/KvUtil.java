package com.ctbt.beidou.base.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ctbt.beidou.base.bo.KeyValue;

public class KvUtil {

	private static Logger logger = LogManager.getLogger(KvUtil.class);
	
	/**
	 * 将 List 中包含的对象，转换出一个 KeyValue List
	 * @param list
	 * @param keyFieldName
	 * @param valFieldName
	 * @return
	 */
	public static List<KeyValue> list2KvList(List list, String keyFieldName, String valFieldName) {
		List<KeyValue> kvList = new ArrayList<KeyValue>();

		if(list != null && list.size() > 0){
			Object obj = null;
			Class objClass = null;
			Field keyField = null;
			Field valField = null;
			Object key = null;
			Object value = null;
			for(int k = 0; k < list.size(); k++){
				obj = list.get(k);
				objClass = obj.getClass();
				try{
					keyField = objClass.getDeclaredField(keyFieldName);
					valField = objClass.getDeclaredField(valFieldName);
					keyField.setAccessible(true);
					valField.setAccessible(true);
					key = keyField.get(obj);
					value = valField.get(obj);

					kvList.add(new KeyValue((String) key, (String) value));
				}catch (Exception e){
					logger.error(e.getMessage(), e);
				}
			}
		}

		return kvList;
	}

	/**
	 * 将 List 中包含的对象，转换出一个 Map
	 * @param list
	 * @param keyFieldName
	 * @param valFieldName
	 * @return
	 */
	public static Map<String, String> list2Map(List list, String keyFieldName, String valFieldName) {
		Map<String, String> kvMap = new HashMap<String, String>();

		if(list != null && list.size() > 0){
			Object obj = null;
			Class objClass = null;
			Field keyField = null;
			Field valField = null;
			Object key = null;
			Object value = null;
			for(int k = 0; k < list.size(); k++){
				obj = list.get(k);
				objClass = obj.getClass();
				try{
					keyField = objClass.getDeclaredField(keyFieldName);
					valField = objClass.getDeclaredField(valFieldName);
					keyField.setAccessible(true);
					valField.setAccessible(true);
					key = keyField.get(obj);
					value = valField.get(obj);

					kvMap.put((String) key, (String) value);
				}catch (Exception e){
					logger.error(e.getMessage(), e);
				}
			}
		}

		return kvMap;
	}

	/**
	 * lista - listb, lista中去掉listb中也有的KeyValue
	 * @param lista
	 * @param listb
	 */
	public static void subtract(List<KeyValue> lista, List<KeyValue> listb) {
		for(KeyValue kvb : listb){
			for(int k = lista.size() - 1; k >= 0; k--){
				KeyValue kva = lista.get(k);
				if(kva.getKey().equals(kvb.getKey())){
					//key相同，则删除lista中的这个KeyValue
					lista.remove(kva);
				}
			}
		}
	}

	/**
	 * 求 两个集合的 交集
	 * @param lista
	 * @param listb
	 */
	public static List<KeyValue> intersection(List<KeyValue> lista, List<KeyValue> listb) {
		List<KeyValue> listc = new ArrayList<KeyValue>();

		for(KeyValue kvb : listb){
			for(int k = lista.size() - 1; k >= 0; k--){
				KeyValue kva = lista.get(k);
				if(kva.getKey().equals(kvb.getKey())){
					//key相同，则删除lista中的这个KeyValue
					listc.add(new KeyValue(kva.getKey(), kva.getValue()));
				}
			}
		}

		return listc;
	}

	/**
	 * 把ids、names还原成KeyValue List
	 * @param ids
	 * @param names
	 * @param sign
	 * @return
	 */
	public static List<KeyValue> getKvListFormIdNames(String ids, String names, String sign) {
		List<KeyValue> kvList = new ArrayList<KeyValue>();

		ids = StrUtil.trim(ids);
		names = StrUtil.trim(names);
		if(!"".equals(ids) && ids.indexOf(sign) > -1 && !"".equals(names) && names.indexOf(sign) > -1){
			String[] idAry = ids.split(sign);
			String[] nameAry = names.split(sign);
			if(idAry.length == nameAry.length){
				//个数相同，即一一对应
				for(int k = 0; k < idAry.length; k++){
					kvList.add(new KeyValue(idAry[k], nameAry[k]));
				}
			}
		}

		return kvList;
	}

	/**
	 * 根据ids,从allList中取出kvList，并从allList中删除
	 * @param ids
	 * @param allList
	 * @return
	 */
	public static List<KeyValue> getKvListFormAllListByIds(String[] ids, List<KeyValue> allList) {
		List<KeyValue> kvList = new ArrayList<KeyValue>();

		if(ids != null && ids.length > 0){
			for(int k = 0; k < ids.length; k++){
				String id = ids[k];
				for(int m = allList.size() - 1; m >= 0; m--){
					KeyValue kv = allList.get(m);
					if(kv.getKey().equals(id)){
						kvList.add(kv);
						allList.remove(kv);
					}
				}
			}
		}

		return kvList;
	}
}
