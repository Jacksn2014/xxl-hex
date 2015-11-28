package com.xxl.service.impl;

import org.springframework.stereotype.Service;

import com.xxl.demo.msg.request.DemoRequest;
import com.xxl.demo.msg.response.DemoResponse;
import com.xxl.hex.core.codec.impl.IRequest;
import com.xxl.hex.core.codec.impl.IResponse;
import com.xxl.hex.core.handler.IHandler;

@Service
public class DemoHandler extends IHandler{
	
	public DemoHandler() {
		super.registry(DemoRequest.class);
	}

	@Override
	public IResponse handle(IRequest request) {
		DemoRequest msg = (DemoRequest) request;
		DemoResponse res = new DemoResponse();
		res.setCode(200);
		res.setMsg("success");
		
		res.setResult("hi, jack. (" + msg.getParam() + ")");
		return res;
	}
	
}
