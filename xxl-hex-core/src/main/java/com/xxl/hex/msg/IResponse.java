package com.xxl.hex.msg;


public abstract class IResponse extends IHexMsg {
	public int ret;	// 默认0,成功状态
	
	public IResponse() {
		this.ret = 0;
	}
	public IResponse(int ret) {
		this.ret = ret;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}
	
}
