package com.xxl.action.hex.msg;

import com.xxl.action.hex.annotation.FieldDef;

/**
 * 抽象消息包
 * @author xuxueli
 *
 */
public abstract class IRequest {
	@FieldDef(fieldLength=32)
	public String signature;	// 签名密钥
	public int msgType;			// 消息类型
	public int timestamp;		// 时间戳

	/**
	 * toString
	 */
	public abstract String toString();

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
}
