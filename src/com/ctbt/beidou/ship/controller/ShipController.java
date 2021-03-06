package com.ctbt.beidou.ship.controller;

import java.util.HashMap;
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

import com.ctbt.beidou.base.model.BdShip;
import com.ctbt.beidou.base.model.SysRegion;
import com.ctbt.beidou.base.service.IDicService;
import com.ctbt.beidou.ship.service.IShipService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.mysql.cj.Session;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/ship")
public class ShipController {

	private Logger logger = LogManager.getLogger(getClass());

	@Resource
	private IDicService dicService;

	@Resource
	private IShipService shipService;

	@RequestMapping("/toShipList")
	public String toShipList(HttpServletRequest request, Model model) {
		return "ship/shipList";
	}

	@RequestMapping("/queryShipList")
	public String queryShipList(HttpServletRequest request, Model model) {
		
		return "ship/shipList";
	}

	@RequestMapping(value = "/toShipEdit")
	public String toShipEdit(HttpServletRequest request,  ModelMap retMap) {
		String  shipId = request.getParameter("shipId");
		BdShip bdship=new BdShip();
		if(shipId!=null) {
			Integer shipIdI= Integer.valueOf(shipId);
			bdship.setShipId(shipIdI);
			bdship = shipService.selectByPrimaryKey(bdship);
			if(bdship.getCountry()==null||bdship.getCountry()==0) {
				bdship.setCountry(0);
				bdship.setCountryName("请选择");
			}
			if(bdship.getProvince()==null||bdship.getProvince()==0) {
				bdship.setProvince(0);
				bdship.setProvinceName("请选择");
			}
			if(bdship.getCity()==null||bdship.getCity()==0) {
				bdship.setCity(0);
				bdship.setCityName("请选择");
			}
			if(bdship.getCityArea()==null||bdship.getCityArea()==0) {
				bdship.setCityArea(0);
				bdship.setCityAreaName("请选择");
			}
			if(bdship.getTown()==null||bdship.getTown()==0) {
				bdship.setTown(0);
				bdship.setTownName("请选择");
			}
			if(bdship.getVillage()==null||bdship.getVillage()==0) {
				bdship.setVillage(0);
				bdship.setVillageName("请选择");
			}
			if(bdship.getShipType()==null||bdship.getShipType().equals("")) {
				bdship.setShipType("0");
			}
		}
		else {
			bdship.setShipId(0);
			bdship.setCountry(100);
			bdship.setProvince(17009);
			bdship.setCity(17202);
			bdship.setCountryName("中华人民共和国");
			bdship.setProvinceName("浙江省");
			bdship.setCityName("浙江省舟山市");
			bdship.setCityAreaName("0");
			bdship.setCityArea(0);
			bdship.setTown(0);
			bdship.setVillage(0);
			bdship.setShipType("0");
			bdship.setValidity("1");
		}
		retMap.addAttribute("Bdship", bdship);
		return "ship/shipEdit";
	}

	@RequestMapping(value = "/selectByitem")
	@ResponseBody
	public JSONObject selectByitem(HttpServletRequest request, BdShip bdship) {
		String page=request.getParameter("page");
	    String pageSize=request.getParameter("pageSize");
		PageHelper.startPage(Integer.valueOf(page),Integer.valueOf(pageSize));
		List<BdShip> result = shipService.selectByitem(bdship);
		PageInfo pages =new PageInfo(result,5);
	    JSONObject obj = new JSONObject();
	    obj.put("Rows", result);
	    obj.put("recordNum",pages.getTotal());
	    obj.put("currentPage",pages.getPageNum());
	    obj.put("sumPageNum",pages.getPages());
		return obj;
	}

	@RequestMapping(value = "/deleteShip")
	@ResponseBody
	public int deleteShip(HttpServletRequest request, BdShip bdship) {
		int result = shipService.deleteByPrimaryKey(bdship);
		return result;
	}

	@RequestMapping(value = "/selectAll")
	@ResponseBody
	public JSONObject selectAll(HttpServletRequest request) {
		    Map map = new HashMap();
			String page=request.getParameter("page");
		    String pageSize=request.getParameter("pageSize");
			PageHelper.startPage(Integer.valueOf(page),Integer.valueOf(pageSize));
			List<BdShip> result = shipService.selectAll(map);
			PageInfo pages =new PageInfo(result,5);
		    JSONObject obj = new JSONObject();
		    obj.put("Rows", result);
		    obj.put("recordNum",pages.getTotal());
		    obj.put("currentPage",pages.getPageNum());
		    obj.put("sumPageNum",pages.getPages());
		return obj;
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public int insert(HttpServletRequest request, BdShip bdship) {
		int result=0;
		BdShip bdShip2=new BdShip();
		bdShip2.setShipNo(bdship.getShipNo());
		List<BdShip> exist = shipService.selectByitem(bdShip2);
		SysRegion record = new SysRegion();
		SysRegion record1 = new SysRegion();
		Integer cityArea=bdship.getCityArea();
		String townName = bdship.getTownName();
		String villageName = bdship.getVillageName();
		record.setRegName(townName);
		record1.setRegName(villageName);
		if (exist.size()==0) {
			if(!(cityArea.equals(0))&&!(townName.equals(""))&&!(villageName.equals(""))) {		
				record.setParentId(cityArea);
				SysRegion sysRegion1 =dicService.queryT(record);
					if(sysRegion1==null) {
						record.setCountryId(100);
						record.setLevel(4);
						dicService.insert(record);
						record1.setCountryId(100);
						record1.setLevel(5);
						record1.setParentId(record.getRegId());
						record1.setRegName(villageName);
						dicService.insert(record1);
						bdship.setTown(record.getRegId());
						bdship.setVillage(record1.getRegId());
						result = shipService.insert(bdship);
					}
					else {
						record1.setParentId(sysRegion1.getRegId());
						SysRegion sysRegion2 =dicService.queryV(record1);
						if(sysRegion2==null) {
							record1.setCountryId(100);
							record1.setLevel(5);
							dicService.insert(record1);
							bdship.setTown(sysRegion1.getRegId());
							bdship.setVillage(record1.getRegId());
							result = shipService.insert(bdship);
						}
						else {
							bdship.setTown(sysRegion1.getRegId());
							bdship.setVillage(sysRegion2.getRegId());
							result = shipService.insert(bdship);
						}
					}
			}
			else if(!(townName.equals("")) || !(villageName.equals(""))){
				result=0;
			}
			else {
				result = shipService.insert(bdship);
			}
		}
		else {
			result=-1;
		}

	
			return result;
	}

	@RequestMapping(value = "/updateShip", method = RequestMethod.POST)
	@ResponseBody
	public int updateShip(HttpServletRequest request, BdShip bdship) {
		Integer maxId = dicService.queryMax();
		int result=1;
		SysRegion record = new SysRegion();
		SysRegion record1 = new SysRegion();
		Integer cityArea=bdship.getCityArea();
		String townName = bdship.getTownName();
		String villageName = bdship.getVillageName();
		record.setRegName(townName);
		record1.setRegName(villageName);
		if(cityArea==null) {
			cityArea=0;
		}
		if(!(cityArea.equals(0))&&!(townName.equals(""))&&!(villageName.equals(""))) {		
			record.setParentId(cityArea);
			SysRegion sysRegion1 =dicService.queryT(record);
				if(sysRegion1==null) {
					record.setCountryId(100);
					record.setLevel(4);
					dicService.insert(record);
					record1.setCountryId(100);
					record1.setLevel(5);
					record1.setParentId(record.getRegId());
					record1.setRegName(villageName);
					dicService.insert(record1);
					bdship.setTown(record.getRegId());
					bdship.setVillage(record1.getRegId());	
					result = shipService.updateByPrimaryKey(bdship);
				}
				else {
					record1.setParentId(sysRegion1.getRegId());
					SysRegion sysRegion2 =dicService.queryV(record1);
					if(sysRegion2==null) {
						record1.setCountryId(100);
						record1.setLevel(5);
						dicService.insert(record1);
						bdship.setTown(sysRegion1.getRegId());
						bdship.setVillage(record1.getRegId());
						result = shipService.updateByPrimaryKey(bdship);
					}
					else {
						bdship.setTown(sysRegion1.getRegId());
						bdship.setVillage(sysRegion2.getRegId());
						result = shipService.updateByPrimaryKey(bdship);
					}
				}
		}
		else {
			result = shipService.updateByPrimaryKey(bdship);
		}
			return result;
	}
	
	


}
