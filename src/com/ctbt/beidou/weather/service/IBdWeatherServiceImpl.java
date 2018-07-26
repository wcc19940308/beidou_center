package com.ctbt.beidou.weather.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.dao.BdMsgWeatherMapper;
import com.ctbt.beidou.base.model.BdMsgWeather;

@Transactional
@Service("weatherService")
public class IBdWeatherServiceImpl implements IBdWeatherService{
	@Resource
	private BdMsgWeatherMapper bdMsgWeatherMapper;
	

	@Override
	public int deleteByPrimaryKey(Integer msgId) {
		
		return this.bdMsgWeatherMapper.deleteByPrimaryKey(msgId);
	}

	@Override
	public int insert(BdMsgWeather record) {
		
		return  this.bdMsgWeatherMapper.insert(record);
	}

	@Override
	public int insertSelective(BdMsgWeather record) {
		
		return this.bdMsgWeatherMapper.insertSelective(record);
	}

	@Override
	public BdMsgWeather selectByPrimaryKey(Integer msgId) {
		
		return this.bdMsgWeatherMapper.selectByPrimaryKey(msgId);
	}

	@Override
	public int updateByPrimaryKeySelective(BdMsgWeather record) {
		
		return this.bdMsgWeatherMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(BdMsgWeather record) {
		
		return this.updateByPrimaryKey(record);
	}

	@Override
	public List<BdMsgWeather> queryWeatherList(Map<String, Object> record) {
		
		return this.bdMsgWeatherMapper.selectByCondition(record);
	}

	//查询消息，构造树形结构
	@Override
	public List<Map<String, Object>> findAll(HttpServletRequest request) {
		
		String text = request.getParameter("text");
		String str = request.getParameter("str");
		List<Map<String, Object>> list = null;
		//如果搜索框的内容和to中的内容都为空，构造整棵树
		if((text == null || text == "") && (str == null || str == "")) 
		{
			list = bdMsgWeatherMapper.findAll();
		}
		//如果搜索框内容不为空，则按搜索框内容进行搜索
		else if(text != null || text != "")
		{
			text = "%"+text+"%";
			list = bdMsgWeatherMapper.searchInfo(text);
		}
		//如果搜索框内容为空，to中内容不为空，则按to中的内容构造树形结构显示
		else if(str != "" && (text == null || text == "")) {
			
			//List<String> stringList = Arrays.asList(str);
			String[] stringList = str.split(",");
			list = bdMsgWeatherMapper.findByPhone(stringList);
		}
		
		//6
		System.out.println("------------------------"+list.size());
		//返回的List数据
		List<Map<String, Object>> returnListInfo = new LinkedList<>();	
		while(list.size() > 0){
			//返回的Map数据
			Map<String,Object> returnMapInfo = new HashMap<>();
			//获得每项明细
			int detailShip_id = (int) list.get(0).get("ship_id");
			String detailShip_name = (String) list.get(0).get("ship_name");
			String detailCard_no1 = (String) list.get(0).get("card_no1");
			//船的具体信息
			returnMapInfo.put("ship_id", detailShip_id);
			returnMapInfo.put("ship_name", detailShip_name);
			returnMapInfo.put("card_no1", detailCard_no1);
			returnMapInfo.put("text", detailShip_name+"("+detailCard_no1+")");
			returnMapInfo.put("icon", request.getScheme() +"://" + request.getServerName()  + 
					":" +request.getServerPort() +request.getContextPath()+"/images/icons/ship22.png");			
			list.remove(0);
			returnListInfo.add(returnMapInfo);
		}
		return returnListInfo;
	}

	@Override
	public int insertMsg(List<BdMsgWeather> list) {
		
		return this.bdMsgWeatherMapper.toInsertMsg(list);
	}
}
