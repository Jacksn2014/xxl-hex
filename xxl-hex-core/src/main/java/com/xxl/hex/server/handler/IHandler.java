package com.xxl.hex.server.handler;

import com.xxl.hex.codec.IRequest;
import com.xxl.hex.codec.IResponse;

public abstract class IHandler {
	
	public abstract IResponse handle(IRequest request) throws Exception;
	
}
