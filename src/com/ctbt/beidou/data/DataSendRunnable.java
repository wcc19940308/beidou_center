package com.ctbt.beidou.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ctbt.beidou.base.CommValue;

public class DataSendRunnable implements Runnable {
	
	private Logger logger = LogManager.getLogger(getClass());
	
	private boolean alive;
	
	public DataSendRunnable() {
		this.alive = true;
	}
	
	@Override
	public void run() {
		logger.info("-----DataSendRunnable run start-----------");
		int count = 0;
		try {
			
			while(alive){
				logger.info("-----DataSendRunnable do Action-----------");
				
				Thread.sleep(CommValue.SEND_INTERVAL);
			}
			
		} catch (Exception ex) {
			logger.error("-----DataSendRunnable Exception-----------");
			logger.error(ex);
		}
		
		try{

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}

		logger.info("-----DataSendRunnable run end-----------");
	}

}
