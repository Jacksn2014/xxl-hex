package com.xxl.service;

public class ResourceBundle {
	private ResourceBundle() {}
	
	/**
	 * 获取资源实例
	 */
	private static final ResourceBundle resource = new ResourceBundle();
	public static ResourceBundle getInstance() {
		return resource;
	}
	
	private ILogService logService;
	
	public ILogService getLogService() {
		return logService;
	}

	public void setLogService(ILogService logService) {
		this.logService = logService;
	}
	
}
