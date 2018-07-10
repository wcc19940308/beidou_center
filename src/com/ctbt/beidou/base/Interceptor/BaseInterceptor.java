package com.ctbt.beidou.base.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class BaseInterceptor implements HandlerInterceptor {

	private Logger logger = LogManager.getLogger(getClass());
	
	//请求执行方法之前执行
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
    	logger.info("BaseIntercpetor.preHandle()");
        return true;
    }
    
    //执行方法之后执行
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    	//logger.info("BaseIntercpetor.postHandle()");
    }
    
    //最终执行的方法
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    	//logger.info("BaseIntercpetor.afterCompletion()");
    }

}
