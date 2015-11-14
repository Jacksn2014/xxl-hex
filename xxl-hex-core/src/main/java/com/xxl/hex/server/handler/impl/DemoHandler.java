package com.xxl.hex.server.handler.impl;

import com.xxl.hex.codec.IRequest;
import com.xxl.hex.codec.IResponse;
import com.xxl.hex.codec.request.DemoRequest;
import com.xxl.hex.codec.response.DemoResponse;
import com.xxl.hex.server.handler.IHandler;

public class DemoHandler extends IHandler{

	@Override
	public IResponse handle(IRequest request) throws Exception {
		
		DemoRequest demoRequest = (DemoRequest) request;
		int code = 200;
		String msg = demoRequest.getName() + " say " + demoRequest.getWord();
		
		DemoResponse response = new DemoResponse();
		response.setCode(code);
		response.setWords(msg);
		
		return response;
	}

}
