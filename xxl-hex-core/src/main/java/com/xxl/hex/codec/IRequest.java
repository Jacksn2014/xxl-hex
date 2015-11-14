package com.xxl.hex.codec;


public abstract class IRequest {
	
	private int msgType;
	/*@FieldDef(fieldLength=32)
	private String signature;*/
	
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	
}
