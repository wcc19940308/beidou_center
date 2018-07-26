package com.ctbt.beidou.data;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SendToManager {
	private static Logger logger = LogManager.getLogger(SendToManager.class);

	private static SendToManager instance = new SendToManager();
	private Thread sendToThread = null;
	private SendToRunnable sendRunnable = null;
	private Map<Thread, SendToRunnable> sendToRunnable = new HashMap<Thread, SendToRunnable>();
	
	public static SendToManager getInstance() {
		return instance;
	}
	public void init() {
		        //初始化 数据发送器
    	sendRunnable = new SendToRunnable();
    	sendToThread = new Thread(sendRunnable);
    	sendToThread.start();        

	}
}
