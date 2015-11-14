package com.xxl.action.hex.logic.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxl.action.hex.logic.ILogic;
import com.xxl.action.hex.msg.IRequest;
import com.xxl.action.hex.msg.IResponse;
import com.xxl.action.hex.msg.request.DemoMsg;
import com.xxl.action.hex.msg.response.DemoResponse;
import com.xxl.core.exception.WebException;

/**
 * Demo
 * @author xuxueli
 */

public class DemoLogic extends ILogic {
	private static transient Logger logger = LoggerFactory.getLogger(DemoLogic.class);
	
	/*
	 * Demo
	 * @see com.xxl.action.logic.ILogic#handle(com.xxl.action.msg.IRequest, java.lang.String)
	 */
	public IResponse handle(IRequest msg, String clientIp) throws WebException {
		DemoMsg msgInfo = (DemoMsg) msg;
		logger.info(msgInfo.toString());	
		
		return new DemoResponse(500, "一帆风顺");
	}

}
