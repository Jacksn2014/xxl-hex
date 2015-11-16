package com.xxl.handler.impl;

import com.xxl.demo.msg.request.DemoRequest;
import com.xxl.demo.msg.response.DemoResponse;
import com.xxl.hex.handler.IHandler;
import com.xxl.hex.msg.impl.IRequest;
import com.xxl.hex.msg.impl.IResponse;

public class DemoHandler extends IHandler{

	@Override
	public IResponse handle(IRequest request) {
		DemoRequest msg = (DemoRequest) request;
		DemoResponse res = new DemoResponse();
		res.setCode(200);
		res.setMsg("success");
		res.setResult("res:" + msg.getParam());
		return res;
	}
	
}
