package com.ctbt.beidou.base.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ctbt.beidou.base.Interceptor.BaseInterceptor;
import com.ctbt.beidou.base.Interceptor.MyHandlerExceptionResolver;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 日志拦截器
     */
    @Autowired
    private BaseInterceptor baseInterceptor;
    
    /**
     * 重写添加拦截器方法并添加配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(baseInterceptor).addPathPatterns("/**").excludePathPatterns("/user/toLogin.ctbt","/user/login.ctbt");
    }
}