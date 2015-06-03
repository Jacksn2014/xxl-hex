package com.xxl.hex.logic;

import com.xxl.core.exception.WebException;
import com.xxl.hex.msg.IRequest;
import com.xxl.hex.msg.IResponse;

public abstract class ILogic {
	
	/**
	 * 处理页面逻辑
	 * @param msg
	 * @param clientIp
	 * @return
	 */
	public abstract IResponse handle(IRequest msg, String clientIp) throws WebException;
	
}
