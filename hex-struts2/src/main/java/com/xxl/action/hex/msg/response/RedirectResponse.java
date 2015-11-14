package com.xxl.action.hex.msg.response;

import com.xxl.action.hex.msg.IResponse;

public class RedirectResponse extends IResponse {
	private String redirectInfo;
	
	public RedirectResponse(String redirectInfo) {
		this.redirectInfo = redirectInfo;
	}

	public String getRedirectInfo() {
		return redirectInfo;
	}

	public void setRedirectInfo(String redirectInfo) {
		this.redirectInfo = redirectInfo;
	}

}
