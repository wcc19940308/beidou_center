package com.ctbt.beidou.base.init;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.ctbt.beidou.user.service.IUserService;

@Component
public class InitData implements ApplicationListener<ContextRefreshedEvent> {
	
    @Autowired  
    private IUserService userService;
  
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {  
        if (event.getApplicationContext().getParent() == null) {  
            //初始化动作  
//        	ApplicationContext app = event.getApplicationContext();
//        	ResourceBundle resource = ResourceBundle.getBundle("webconfig");
//        	String ProjectName = resource.getString("ProjectName");
//        	System.out.println("ProjectName:"+ProjectName);
//        	System.out.println("InitData onApplicationEvent");
        }  
  
    }  
}