
package com.xxl.hex.handler;

import com.xxl.hex.msg.impl.IRequest;
import com.xxl.hex.msg.impl.IResponse;

public abstract class IHandler {
	
	public abstract IResponse handle(IRequest request);
	
}
