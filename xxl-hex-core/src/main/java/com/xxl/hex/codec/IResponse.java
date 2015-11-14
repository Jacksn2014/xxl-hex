package com.xxl.hex.codec;

import com.xxl.hex.annotation.FieldDef;


public abstract class IResponse {
	
	private int code;
	@FieldDef(fieldLength=64)
	private String msg;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
