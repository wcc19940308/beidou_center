package com.ctbt.beidou.data;

import java.util.Date;
import java.util.List;

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
import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.model.BdDataSite;
import com.ctbt.beidou.base.model.BdDevice;
import com.ctbt.beidou.base.model.BdSendPackage;
import com.ctbt.beidou.base.utils.SpringContextUtil;
import com.ctbt.beidou.datapack.service.IBdDataPackService;
import com.google.gson.Gson;

public class DataSendRunnable implements Runnable {
	
	private Logger logger = LogManager.getLogger(getClass());
	
	private boolean alive;
	
	public DataSendRunnable() {
		this.alive = true;
	}
	
	@Override
	public void run() {
		logger.info("-----------10秒后开始发送数据 DataSendRunnable run -----------");
		int count = 0;
		try {
			Thread.sleep(10000);
			
			IBdDataPackService datapackService = (IBdDataPackService) SpringContextUtil.getBean("datapackService");
			Gson gson = new Gson();
			
			while(alive){
				
				if (Thread.currentThread().isInterrupted()) {
                    logger.info("----线程已中断，退出循环操作----");
                    break;
                }
				
				List<BdSendPackage> sendList = datapackService.queryBdSendPackageForSend();
				if(sendList != null && sendList.size() > 0) {
					Date now = new Date();
					for(BdSendPackage pkg : sendList) {
						try {
							String pkgTo = pkg.getPkgTo().toString();
							BdDevice bdDevice = DataJobManager.getInstance().deviceMapSlave.get(pkgTo);
							String siteNo = bdDevice.getSiteNo();
							BdDataSite site = DataJobManager.getInstance().siteMap.get(siteNo);

							//逐个访问数据站，请求数据
							CloseableHttpClient httpClient = HttpClients.createDefault();
						    HttpPost httpPost = new HttpPost(site.getSiteUrl()+"write");
							
							String params = "centerNo="+CommValue.CenterNo+"&userName="+site.getUserName()+"&password="+site.getPassword();
							String json = gson.toJson(pkg);
							params += "&BdSendPackage="+json;
							
							StringEntity entity = new StringEntity(params, "UTF-8");
							entity.setContentType("application/x-www-form-urlencoded");
							httpPost.setEntity(entity);
							HttpResponse response = httpClient.execute(httpPost);
							int statusCode = response.getStatusLine().getStatusCode();
							if (statusCode == HttpStatus.SC_OK) {
								//操作成功，更新记录的发送时间和发送数据站编号
								String body = EntityUtils.toString(response.getEntity(), "UTF-8");
								ResultView rv = gson.fromJson(body, ResultView.class);
								if(rv != null) {
									if("1".equals(rv.getFlag())) {
										pkg.setSendTime(now);
										pkg.setSendSiteNo(siteNo);
										datapackService.updateBdSendPackageForSend(pkg);
									}else {
										//操作失败
										logger.error("------操作失败: " + rv.getMsg());
									}
								}else {
									logger.error("------操作失败，或许返回的数据异常！------ ");
								}
								
							}else {
								//操作失败
								logger.error("------Status Code : " + statusCode + "---Request failed! " + response.getStatusLine());
								String body = EntityUtils.toString(response.getEntity(), "UTF-8");
								logger.error("------Response content : " + body);
							}
							
						}catch(Exception e) {
							logger.error(e.getMessage());
							logger.error("------数据发送出现异常:"+pkg.toString());
							e.printStackTrace();
						}
						
					}
				}
				
//				Thread.sleep(CommValue.RECV_INTERVAL);
				Thread.sleep(5000);
			}
			
		} catch (Exception ex) {
			logger.error("-----DataSendRunnable Exception-----------");
			logger.error(ex);
		}
		
		logger.info("-----DataSendRunnable run end-----------");
	}
	
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
