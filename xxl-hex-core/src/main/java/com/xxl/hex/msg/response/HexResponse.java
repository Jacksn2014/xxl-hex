package com.xxl.hex.msg.response;

import com.xxl.hex.annotation.FieldDef;
import com.xxl.hex.msg.IResponse;

/**
 * 动态消息
 * @author xuxueli
 */
public class HexResponse extends IResponse {
	@FieldDef(fieldLength=128)
	private String msg;
	
	public HexResponse(String msg) {
		super(1);
		this.msg = msg;
	}
	public HexResponse(int ret, String msg) {
		super(ret);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
