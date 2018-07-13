package com.ctbt.beidou.weather.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ctbt.beidou.base.model.BdMsgWeather;

public interface IBdWeatherService {
	int deleteByPrimaryKey(Integer msgId);

    int insert(BdMsgWeather record);

    int insertSelective(BdMsgWeather record);

    BdMsgWeather selectByPrimaryKey(Integer msgId);
    
    int updateByPrimaryKeySelective(BdMsgWeather record);

    int updateByPrimaryKey(BdMsgWeather record);
        
    //按条件查询
    List<BdMsgWeather> queryWeatherList(BdMsgWeather record);
    
    //展示所有人员信息
    List<Map<String, Object>> findAll(HttpServletRequest request);
    
    
    //将发送的信息批量插入
    int insertMsg(List<BdMsgWeather> list);
}
