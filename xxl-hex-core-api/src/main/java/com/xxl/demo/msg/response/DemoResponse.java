package com.xxl.demo.msg.response;

import com.xxl.hex.annotation.HexField;
import com.xxl.hex.msg.impl.IResponse;

public class DemoResponse extends IResponse {
	
	
	@HexField(length=20)
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
