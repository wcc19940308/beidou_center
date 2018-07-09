package com.ctbt.beidou.base.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ctbt.beidou.base.CommValue;

public class SessionUtil {

	/**
	 * 将一个值放入session中的一个map
	 * @param session
	 * @param key
	 * @param value
	 */
	public static void putToSessionMap(HttpServletRequest request, String mapKey, String key, Object value) {
		HttpSession session = request.getSession();
		Map<String, Object> randomCodeMap = (Map) session.getAttribute(mapKey);
		if(randomCodeMap == null){
			randomCodeMap = new HashMap<String, Object>();
			session.setAttribute(mapKey, randomCodeMap);
		}
		randomCodeMap.put(key, value);
	}

	/**
	 * 从session中的一个map中取键值
	 * @param session
	 * @param key
	 * @param value
	 */
	public static Object getFromSessionMap(HttpServletRequest request, String mapKey, String key) {
		HttpSession session = request.getSession();
		Map<String, Object> randomCodeMap = (Map) session.getAttribute(mapKey);
		if(randomCodeMap != null){
			return randomCodeMap.get(key);
		}

		return null;
	}

	/**
	 * 判断是否存在于session中的一个map中的key
	 * @param session
	 * @param key
	 * @param value
	 */
	public static boolean isInSessionMap(HttpServletRequest request, String mapKey, String key) {
		HttpSession session = request.getSession();
		Map<String, Object> randomCodeMap = (Map) session.getAttribute(mapKey);
		if(randomCodeMap != null){
			return randomCodeMap.containsKey(key);
		}

		return false;
	}

	/**
	 * 从session中的一个map中删除一个键值
	 * @param session
	 * @param key
	 * @param value
	 */
	public static void removeFromSessionMap(HttpServletRequest request, String mapKey, String key) {
		HttpSession session = request.getSession();
		Map<String, Object> randomCodeMap = (Map) session.getAttribute(mapKey);
		if(randomCodeMap != null){
			randomCodeMap.remove(key);
		}
	}

	/**
	 * 产生一个临时操作码
	 * @param request
	 */
	public static void makeTempOpCode(HttpServletRequest request) {
		String TempOpCode = UUID.randomUUID().toString();
		request.setAttribute(CommValue.TEMP_OP_CODE, TempOpCode);//临时操作码
		//	将临时操作码放入session中的一个map
		SessionUtil.putToSessionMap(request, CommValue.SESSION_TEMP_OP_CODE_MAP_KEY, TempOpCode, new Date());
	}
}
