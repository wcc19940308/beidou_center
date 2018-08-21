package com.ctbt.beidou.data;

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
import com.ctbt.beidou.base.model.BdDataSite;
import com.ctbt.beidou.base.model.BdRecvPackage;
import com.ctbt.beidou.base.utils.SpringContextUtil;
import com.ctbt.beidou.datapack.service.IBdDataPackService;
import com.google.gson.Gson;

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
		logger.info("-----------10秒后开始取数据------DataRecvRunnable run start recvNo:"+this.recvNo+"-----------");
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
				
				List<BdDataSite> siteList = DataJobManager.getInstance().siteList;
				for(BdDataSite site : siteList) {
					//逐个访问数据站，请求数据
					CloseableHttpClient httpClient = HttpClients.createDefault();
				    HttpPost httpPost = new HttpPost(site.getSiteUrl()+"read");
					
					try {
						String params = "centerNo="+CommValue.CenterNo+"&userName="+site.getUserName()+"&password="+site.getPassword();

						StringEntity entity = new StringEntity(params, "UTF-8");
						entity.setContentType("application/x-www-form-urlencoded");
						httpPost.setEntity(entity);
						HttpResponse response = httpClient.execute(httpPost);
						int statusCode = response.getStatusLine().getStatusCode();
						if (statusCode == HttpStatus.SC_OK) {
							//状态码返回200，操作成功！
							String body = EntityUtils.toString(response.getEntity(), "UTF-8");
							RecvBo rbo = gson.fromJson(body, RecvBo.class);
							if(rbo != null) {
								List<BdRecvPackage> pkgList = rbo.getData();
								if(pkgList != null && pkgList.size() > 0) {
									for(BdRecvPackage pkg : pkgList) {
										try {
											pkg.setFromSiteNo(site.getSiteNo());
											datapackService.saveBdRecvPackage(pkg);
										}catch(Exception e) {
											logger.error("------PkgId:"+pkg.getPkgId() + ", "+e.getMessage());
											e.printStackTrace();
										}
									}
								}
							}
						}else {
							//操作失败
							logger.error("---Status Code : " + statusCode + "---Request failed! " + response.getStatusLine());
							String body = EntityUtils.toString(response.getEntity(), "UTF-8");
							logger.error("---Response content : " + body);
						}
					}catch(Exception e) {
						logger.error(e.getMessage());
						e.printStackTrace();
					}
				}
				
//				Thread.sleep(CommValue.RECV_INTERVAL);
				Thread.sleep(5000);
			}
			
		} catch (Exception ex) {
			logger.error("-----------DataRecvRunnable Exception recvNo:"+this.recvNo+"-----------");
			logger.error(ex);
		}
		
		logger.info("-----------DataRecvRunnable run end recvNo:"+this.recvNo+"-----------");
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
