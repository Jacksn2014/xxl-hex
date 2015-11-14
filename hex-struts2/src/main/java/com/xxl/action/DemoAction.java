package com.xxl.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxl.action.base.DataBaseAction;

@SuppressWarnings("serial")
public class DemoAction extends DataBaseAction {
	private static transient Logger logger = LoggerFactory.getLogger(DemoAction.class);
	
	public String test(){
		logger.info("demo test");
		return SUCCESS;
	}
	
}
