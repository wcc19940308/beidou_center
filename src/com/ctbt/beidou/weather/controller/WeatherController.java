package com.ctbt.beidou.weather.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctbt.beidou.base.model.BdMsgWeather;
import com.ctbt.beidou.weather.service.IBdWeatherService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/weather")
public class WeatherController {

	private Logger logger = LogManager.getLogger(getClass());

	@Resource
	private IBdWeatherService weatherService;

	@RequestMapping(value = "/queryWeatherList", method = RequestMethod.POST)
	@ResponseBody
	public List<BdMsgWeather> queryWeatherList(HttpServletRequest request, Model model) {

		BdMsgWeather bdMsgWeather = new BdMsgWeather();

		List<BdMsgWeather> list = weatherService.queryWeatherList(bdMsgWeather);

		return list;
	}

	@RequestMapping("/toWeatherList")
	public String toWeatherList(HttpServletRequest request, Model model) {

		return "weather/weatherList";
	}

	@RequestMapping("/toWeatherEdit")
	public String toWeatherEdit(HttpServletRequest request, Model model) {

		return "weather/weatherEdit";
	}

	// 显示from,to的页面信息
	@RequestMapping(value = "/showFromTo")
	public String showFromTo(HttpServletRequest request, ModelMap retMap) {
		return "weather/weatherEdit";
	}

	// 向bd_msg_weather表插入向船员发送的数据
	@RequestMapping(value = "/toInsertMsg", method = RequestMethod.POST)
	@ResponseBody
	public int toInsertMsg(HttpServletRequest request) {

		String liString = request.getParameter("list");
		List<BdMsgWeather> list = new ArrayList<>();
		JSONArray jsonArray = JSONArray.fromObject(liString);
		for (int i = 0; i < jsonArray.size(); i++) {
			Object object = jsonArray.get(i);
			JSONObject jsonObject = JSONObject.fromObject(object);
			BdMsgWeather BdMsgWeather = (BdMsgWeather) JSONObject.toBean(jsonObject, BdMsgWeather.class);
			BdMsgWeather.setSendTime(new Date());
			list.add(BdMsgWeather);
			System.out.println(BdMsgWeather);
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		int returnInt = weatherService.insertMsg(list);
		System.out.println(returnInt);
		return returnInt;
	}

	// 找到所有人信息，并且跳转到选择人员界面
	@RequestMapping("/toSendMsg")
	public String toSendMsg(HttpServletRequest request, BdMsgWeather record, ModelMap retMap) {
		return "weather/weatherSend";
	}
	
	//找到所有人的信息构造树形结构
	@RequestMapping(value = "/findAll",method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> findAll(HttpServletRequest request,BdMsgWeather record){
				
		List<Map<String, Object>> list = weatherService.findAll(request);		
		return list;
	}

}
