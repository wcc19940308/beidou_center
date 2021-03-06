package com.ctbt.beidou.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ctbt.beidou.base.model.BdDataSite;
import com.ctbt.beidou.base.model.BdDevice;
import com.ctbt.beidou.base.utils.SpringContextUtil;
import com.ctbt.beidou.device.service.IDeviceService;

public class DataJobManager {

	private static Logger logger = LogManager.getLogger(DataJobManager.class);

	private static DataJobManager instance = new DataJobManager();
	
	private Thread sendThread = null;
	private DataSendRunnable sendRunnable = null;
	private Map<Thread, DataRecvRunnable> recvThreadMap = new HashMap<Thread, DataRecvRunnable>();
	public List<BdDataSite> siteList = new ArrayList<BdDataSite>();
	public Map<String, BdDataSite> siteMap = new HashMap<String, BdDataSite>();
	
	public List<BdDevice> deviceList = new ArrayList<BdDevice>();
	public Map<String, BdDevice> deviceMapSlave = new HashMap<String, BdDevice>();
	
	public static DataJobManager getInstance() {
		return instance;
	}

	public void init() {
		initDataSite();
		initDevice();
		
		ResourceBundle resource = ResourceBundle.getBundle("webconfig");
        int Recvers = Integer.valueOf(resource.getString("DataRecvers"));

        //初始化 数据发送器
    	sendRunnable = new DataSendRunnable();
        sendThread = new Thread(sendRunnable);
        sendThread.start();
        
        //初始化 数据接收器
//        for(int k = 1; k <= Recvers; k++) {
//        	DataRecvRunnable recbRunnable = new DataRecvRunnable(k);
//        	Thread recvThread = new Thread(recbRunnable);
//        	recvThread.start();
//        	
//        	recvThreadMap.put(recvThread, recbRunnable);
//        }
	}

	/**
	 * 加载数据站的配置信息
	 */
	private void initDataSite() {
		IDeviceService deviceService = (IDeviceService)SpringContextUtil.getBean("deviceService");
		List<BdDataSite> list = deviceService.selectAllDataSite();
		siteList.clear();
		siteList.addAll(list);
		
		siteMap.clear();
		for(BdDataSite site : list) {
			siteMap.put(site.getSiteNo(), site);
		}
	}
	
	/**
	 * 加载所有设备（数据站、指挥机、用户机）的关联信息
	 */
	private void initDevice() {
		IDeviceService deviceService = (IDeviceService)SpringContextUtil.getBean("deviceService");
		List<BdDevice> list = deviceService.selectAll();
		deviceList.clear();
		deviceList.addAll(list);
		
		deviceMapSlave.clear();
		for(BdDevice d : deviceList) {
			deviceMapSlave.put(d.getSlaveCardNo().toString(), d);
		}
	}
}
