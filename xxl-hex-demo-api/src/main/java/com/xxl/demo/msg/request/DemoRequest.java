package com.xxl.demo.msg.request;

import com.xxl.hex.core.codec.impl.IRequest;


public class DemoRequest extends IRequest {
	
	private String param;
	
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	
}
