package com.xxl.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xxl.service.ILogService;
import com.xxl.service.ResourceBundle;

/**
 * 注册监听器
 * @author xuxueli
 */
public class RegistryListener implements ServletContextListener {
	private static transient Logger logger = LoggerFactory.getLogger(RegistryListener.class);
	
	public void contextDestroyed(ServletContextEvent context) {
	}

	public void contextInitialized(ServletContextEvent sc) {
		logger.info("[5i hex registry listener starting...]");
		
		// 初始化.资源实例
		ResourceBundle resource = ResourceBundle.getInstance();
		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(sc.getServletContext());
		resource.setLogService((ILogService) context.getBean("logService"));
		
		logger.info("[5i hex registry listener finished...]");
	}

}
