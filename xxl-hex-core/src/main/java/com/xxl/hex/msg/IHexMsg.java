package com.xxl.hex.msg;

import com.xxl.hex.annotation.FieldDef;

public abstract class IHexMsg {
	
	public int msgType;
	@FieldDef(fieldLength=32)
	public String signature;
	public int timestamp;
	
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public int getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
}
