package com.ctbt.beidou.data;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ctbt.beidou.base.CommValue;

public class DataRecvRunnable implements Runnable {
	
	private Logger logger = LogManager.getLogger(getClass());
	
	private boolean alive;
	
	private int recvNo;
	
	public DataRecvRunnable(int recvNo) {
		this.alive = true;
		this.recvNo = recvNo;
	}
	
	@Override
	public void run() {
		logger.info("-----------DataRecvRunnable run start recvNo:"+this.recvNo+"-----------");
		int count = 0;
		try {
			
			while(alive){
				logger.info("-----------DataRecvRunnable do Action recvNo:"+this.recvNo+"-----------");
				
				
				Thread.sleep(CommValue.RECV_INTERVAL);
			}
			
		} catch (Exception ex) {
			logger.error("-----------DataRecvRunnable Exception recvNo:"+this.recvNo+"-----------");
			logger.error(ex);
		}
		
		try{

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}

		logger.info("-----------DataRecvRunnable run end recvNo:"+this.recvNo+"-----------");
	}

	/**
	 * 
	 */
	public void recvAction() {
		String QUERY_URL = "http://192.168.1.15:4242/api/query";

		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(QUERY_URL);
			String queryRequest = "";//genQueryReq();
			// logger.info("Request=" + queryRequest);
			StringEntity entity = new StringEntity(queryRequest, "UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();
			logger.info("Status Code : " + statusCode);
			if (statusCode != HttpStatus.SC_OK) {
				logger.info("Request failed! " + response.getStatusLine());
			}

			String body = EntityUtils.toString(response.getEntity(), "UTF-8");
			logger.info("Response content : " + body);
		}catch(Exception e) {
			logger.error("-----------DataRecvRunnable recvAction Exception recvNo:"+this.recvNo+"-----------");
			logger.error(e);
		}
	}
}
