package com.xxl.hex.logic.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxl.hex.logic.ILogic;
import com.xxl.hex.msg.IRequest;
import com.xxl.hex.msg.IResponse;
import com.xxl.hex.msg.request.DemoMsg;
import com.xxl.hex.msg.response.DemoResponse;

/**
 * Demo
 * @author xuxueli
 */

public class DemoLogic extends ILogic {
	private static transient Logger logger = LoggerFactory.getLogger(DemoLogic.class);
	
	/*
	 * Demo
	 * @see com.xxl.hex.logic.ILogic#handle(com.xxl.hex.msg.IRequest, java.lang.String)
	 */
	public IResponse handle(IRequest msg, String clientIp) throws Exception {
		DemoMsg msgInfo = (DemoMsg) msg;
		logger.info(msgInfo.toString());	
		
		/*ILogService service = ResourceBundle.getRegistry().getLogService();
		String logs = service.loginLog(msgInfo.getUserId());*/
		
		return new DemoResponse(500, null);
	}

}
