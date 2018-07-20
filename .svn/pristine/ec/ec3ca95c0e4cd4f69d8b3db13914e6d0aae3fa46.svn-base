package com.ctbt.beidou.data;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataJobManager {

	private static Logger logger = LogManager.getLogger(DataJobManager.class);

	private static DataJobManager instance = new DataJobManager();
	
	private Thread sendThread = null;
	private DataSendRunnable sendRunnable = null;
	private Map<Thread, DataRecvRunnable> recvThreadMap = new HashMap<Thread, DataRecvRunnable>();
	
	public static DataJobManager getInstance() {
		return instance;
	}

	public void init() {
		ResourceBundle resource = ResourceBundle.getBundle("webconfig");
        int Recvers = Integer.valueOf(resource.getString("DataRecvers"));

        //初始化 数据发送器
    	sendRunnable = new DataSendRunnable();
        sendThread = new Thread(sendRunnable);
        sendThread.start();
        
        //初始化 数据接收器
        for(int k = 1; k <= Recvers; k++) {
        	DataRecvRunnable recbRunnable = new DataRecvRunnable(k);
        	Thread recvThread = new Thread(recbRunnable);
        	recvThread.start();
        	
        	recvThreadMap.put(recvThread, recbRunnable);
        }
	}
}
