package com.xxl.action.hex.logic;

import com.xxl.action.hex.msg.IRequest;
import com.xxl.action.hex.msg.IResponse;
import com.xxl.core.exception.WebException;

public abstract class ILogic {
	
	/**
	 * 处理页面逻辑
	 * @param msg
	 * @param clientIp
	 * @return
	 */
	public abstract IResponse handle(IRequest msg, String clientIp) throws WebException;
	
}
