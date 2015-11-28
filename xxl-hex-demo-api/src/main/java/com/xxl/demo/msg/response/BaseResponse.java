package com.xxl.demo.msg.response;

import com.xxl.hex.core.codec.impl.IResponse;

public class BaseResponse extends IResponse {
	
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
