
package com.xxl.hex.handler;

import com.xxl.hex.codec.impl.IRequest;
import com.xxl.hex.codec.impl.IResponse;
import com.xxl.hex.servlet.HexServlet;

public abstract class IHandler {
	
	protected void registry(Class<? extends IRequest> requestClass) {
		HexServlet.registryHandler(requestClass.getName(), this);
	}
	
	public abstract IResponse handle(IRequest request);
	
}
