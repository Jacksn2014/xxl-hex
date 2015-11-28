
package com.xxl.hex.core.handler;

import com.xxl.hex.core.codec.impl.IRequest;
import com.xxl.hex.core.codec.impl.IResponse;
import com.xxl.hex.http.servlet.HexServlet;

/**
 * request handler
 * @author xuxueli 
 * @version 2015-11-28 13:56:05
 */
public abstract class IHandler {
	
	protected void registry(Class<? extends IRequest> requestClass) {
		HexServlet.registryHandler(requestClass.getName(), this);
	}
	
	public abstract IResponse handle(IRequest request);
	
}
