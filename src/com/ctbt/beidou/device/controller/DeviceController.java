package com.ctbt.beidou.device.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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

import com.ctbt.beidou.advert.service.IAdvertService;
import com.ctbt.beidou.base.model.BdAdvert;
import com.ctbt.beidou.base.model.BdDevice;
import com.ctbt.beidou.base.model.BdDevice;
import com.ctbt.beidou.base.model.BdShip;
import com.ctbt.beidou.device.service.IDeviceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/device")
public class DeviceController {

	private Logger logger = LogManager.getLogger(getClass());

	@Resource
	private IDeviceService deviceService;

	@RequestMapping(value = "/selectAllDevice")
	@ResponseBody
	public JSONObject selectAllDevice(HttpServletRequest request) {
		String page=request.getParameter("page");
	    String pageSize=request.getParameter("pageSize");
		PageHelper.startPage(Integer.valueOf(page),Integer.valueOf(pageSize));
		List<BdDevice> result = deviceService.selectAll();
		PageInfo pages =new PageInfo(result,5);
	    JSONObject obj = new JSONObject();
	    obj.put("Rows", result);
	    obj.put("recordNum",pages.getTotal());
	    obj.put("currentPage",pages.getPageNum());
	    obj.put("sumPageNum",pages.getPages());
		
		return obj;
	}

	@RequestMapping("/selectDeviceItem")
	@ResponseBody
	public JSONObject queryDeviceItem(HttpServletRequest request, BdDevice bdDevice, Model model) {
		String page=request.getParameter("page");
	    String pageSize=request.getParameter("pageSize");
		PageHelper.startPage(Integer.valueOf(page),Integer.valueOf(pageSize));	
		List<BdDevice> result = deviceService.selectByItem(bdDevice);
		PageInfo pages =new PageInfo(result,5);
	    JSONObject obj = new JSONObject();
	    obj.put("Rows", result);
	    obj.put("recordNum",pages.getTotal());
	    obj.put("currentPage",pages.getPageNum());
	    obj.put("sumPageNum",pages.getPages());
		return obj;
	}

	@RequestMapping("/toDeviceList")
	public String toAdvertList(HttpServletRequest request, ModelMap retMap) {

		return "device/deviceList";
	}

	@RequestMapping(value = "/updateDevice")
	@ResponseBody
	public int updateDevice(HttpServletRequest request, BdDevice bdDevice) {

		int result = deviceService.updateByPrimaryKeySelective(bdDevice);

		return result;
	}

	@RequestMapping("/toDeviceEdit")
	public String toAdvertEdit(HttpServletRequest request, BdDevice bdDevice, ModelMap retMap) {
		Integer msid = bdDevice.getMsid();
		if (msid != null) {
			bdDevice = deviceService.selectByPrimaryKey(bdDevice);
		} else {
			bdDevice.setMsid(0);
			bdDevice.setSiteNo("0");
		}

		retMap.addAttribute("BdDevice", bdDevice);

		return "device/deviceEdit";
	}

	@RequestMapping(value = "/insertDevice", method = RequestMethod.POST)
	@ResponseBody
	public int insertAdvert(HttpServletRequest request, BdDevice bdDevice) throws ParseException {

		int result = deviceService.insert(bdDevice);

		return result;
	}

	@RequestMapping(value = "/deleteDevice", method = RequestMethod.POST)
	@ResponseBody
	public int deleteDevice(HttpServletRequest request, BdDevice bdDevice) throws ParseException {

		int result = deviceService.deleteByPrimaryKey(bdDevice);

		return result;
	}
}
