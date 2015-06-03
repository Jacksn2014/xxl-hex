package com.xxl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ResourceBundle {
	private static transient Logger logger = LoggerFactory.getLogger(ResourceBundle.class);
	
	/**
	 * 获取资源实例
	 */
	private static ResourceBundle resource;
	public static AbstractApplicationContext context;
	public static ResourceBundle getRegistry() {
		if (resource == null) {
			context = new ClassPathXmlApplicationContext(new String[]{"application-context.xml"});
			resource = new ResourceBundle();
			resource.setLogService((ILogService) context.getBean(ILogService.class));
			logger.info("[ResourceBundle init success]");
		}
		return resource;
	}
	
	
	public static void dispose() {
		resource = null;
	}
	
	private ILogService logService;
	
	public ILogService getLogService() {
		return logService;
	}
	public void setLogService(ILogService logService) {
		this.logService = logService;
	}
	
}
