package com.ctbt.beidou.base.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctbt.beidou.base.bo.KeyValue;
import com.ctbt.beidou.base.model.SysDic;
import com.ctbt.beidou.base.service.IDicService;
import com.ctbt.beidou.base.utils.DicUtil;

@Controller
@RequestMapping("/dic")
public class DicController {

	private Logger logger = LogManager.getLogger(getClass());
	
	@Resource
	private IDicService dicService;


	@RequestMapping(value = "/getDic", method = RequestMethod.POST)
	@ResponseBody
	public List<KeyValue> getDic(Integer dicId) {
		String id = dicId != null ? dicId.toString() : "";
		List<KeyValue> list = DicUtil.getInstance().getDic(id);
		return list;
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
		logger.debug("DicController queryProvinceList countryId:"+countryId);
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
