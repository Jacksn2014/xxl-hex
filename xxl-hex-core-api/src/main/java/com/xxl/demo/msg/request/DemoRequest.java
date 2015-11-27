package com.xxl.demo.msg.request;

import com.xxl.hex.annotation.HexField;
import com.xxl.hex.codec.impl.IRequest;


public class DemoRequest extends IRequest {
	
	@HexField(length=20)
	private String param;
	
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	
}
