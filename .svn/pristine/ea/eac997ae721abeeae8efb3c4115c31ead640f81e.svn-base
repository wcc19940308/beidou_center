package com.ctbt.beidou.base.init;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.ctbt.beidou.base.utils.DicUtil;

public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(getClass());
	
    @Override
	public void init(ServletConfig sc) throws ServletException {
		logger.info("InitServlet init start ...");
		try{

            //常量初始化
        	ResourceBundle resource = ResourceBundle.getBundle("webconfig");
        	String ProjectName = resource.getString("ProjectName");
        	logger.info("InitServlet ProjectName:"+ProjectName);

        	ServletContext application = sc.getServletContext();
            application.setAttribute("ProjectName", ProjectName);
        	
            //加载字典到内存
            DicUtil.getInstance().loadAllDic();
            
		}catch (Exception e){
			logger.error(e.getMessage(), e);
		}
		logger.info("InitServlet init finished !!!");
	}
	
    @Override
    public void destroy() {
    	logger.info("InitServlet destroy");
    }

    @Override
    public ServletConfig getServletConfig() {
    	logger.info("InitServlet getServletConfig");
        return null;
    }

    @Override
    public String getServletInfo() {
    	logger.info("InitServlet getServletInfo");
        return null;
    }

    @Override
    public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
    	logger.info("InitServlet service");
    }

}
