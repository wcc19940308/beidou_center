package com.ctbt.beidou.base.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctbt.beidou.base.bo.KeyValue;
import com.ctbt.beidou.base.model.SysDic;
import com.ctbt.beidou.base.service.IDicService;

@Controller
@RequestMapping("/dic")
public class DicController {

	private Logger logger = Logger.getLogger(getClass());
	
	@Resource
	private IDicService dicService;


	@RequestMapping(value = "/queryAllCity", method = RequestMethod.POST)
	@ResponseBody
	public List<KeyValue> queryAllCity(Integer regId) {
		return dicService.queryAllCity(regId);
	}

	@RequestMapping(value = "/queryAllCityArea", method = RequestMethod.POST)
	@ResponseBody
	public List<KeyValue> queryAllCityArea(Integer regId) {
		return dicService.queryAllCityArea(regId);
	}
	@RequestMapping(value = "/queryAllTown", method = RequestMethod.POST)
	@ResponseBody
	public List<KeyValue> queryAllTown(Integer regId) {
		return dicService.queryAllTown(regId);
	}
	@RequestMapping(value = "/queryAllVillage", method = RequestMethod.POST)
	@ResponseBody
	public List<KeyValue> queryAllVillage(Integer regId) {
		System.out.println(dicService.queryAllVillage(regId));
		return dicService.queryAllVillage(regId);
	}
	
	@RequestMapping(value = "/queryAllProvince", method = RequestMethod.POST)
	@ResponseBody
	public List<KeyValue> queryAllProvince () {
		return dicService.queryAllProvince();
	}
	
	@RequestMapping(value = "/queryCountryList", method = RequestMethod.POST)
	@ResponseBody
	public List<KeyValue> queryCountryList() {
		return dicService.queryCountryList();
	}
	
	@RequestMapping(value = "/loadDic", method = RequestMethod.POST)
	@ResponseBody
	public List<KeyValue> loadDic(Integer dicId) {
		return dicService.loadDic(dicId);
	}
	
	@RequestMapping(value = "/queryProvinceList", method = RequestMethod.POST)
	@ResponseBody
	public List<KeyValue> queryProvinceList(Integer countryId) {
		return dicService.queryProvinceList(countryId);
	}

	@RequestMapping(value = "/queryCityList", method = RequestMethod.POST)
	@ResponseBody
	public List<KeyValue> queryCityList(Integer regId) {
		return dicService.queryCityList(regId);
	}

	@RequestMapping(value = "/queryCityAreaList", method = RequestMethod.POST)
	@ResponseBody
	public List<KeyValue> queryCityAreaList(Integer regId) {
		return dicService.queryCityAreaList(regId);
	}

	@RequestMapping(value = "/queryTownList", method = RequestMethod.POST)
	@ResponseBody
	public List<KeyValue> queryTownList(Integer regId) {
		return dicService.queryTownList(regId);
	}

	@RequestMapping(value = "/queryVillageList", method = RequestMethod.POST)
	@ResponseBody
	public List<KeyValue> queryVillageList(Integer regId) {
		return dicService.queryVillageList(regId);
	}

	
}
