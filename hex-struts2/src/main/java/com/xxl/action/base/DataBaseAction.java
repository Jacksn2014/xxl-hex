package com.xxl.action.base;

import com.xxl.action.hex.msg.IRequest;

@SuppressWarnings("serial")
public abstract class DataBaseAction extends BaseAction {
	private String hex;		// 请求源数据
	private IRequest msg;	// 请求消息
	private String resp;	// 重定向地址
	
	public IRequest getMsg() {
		return msg;
	}
	public void setMsg(IRequest msg) {
		this.msg = msg;
	}
	public String getHex() {
		return hex;
	}
	public void setHex(String hex) {
		this.hex = hex;
	}
	public String getResp() {
		return resp;
	}
	public void setResp(String resp) {
		this.resp = resp;
	}
	
}
