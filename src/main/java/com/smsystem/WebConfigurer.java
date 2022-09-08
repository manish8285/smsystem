package com.smsystem;

import java.io.File;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
			
					ApplicationHome home = new ApplicationHome(SmsystemApplication.class);
					String path ="file://"+ home.getDir().toString()+ File.separator+"public/";
					//System.out.println(home.getDir().toString());
					registry.addResourceHandler("/**").addResourceLocations(path);
	}
	
}
