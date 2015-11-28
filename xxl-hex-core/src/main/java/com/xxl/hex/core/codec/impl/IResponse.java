package com.xxl.hex.core.codec.impl;

import com.xxl.hex.core.codec.IHexMsg;
import com.xxl.hex.core.serialise.ByteReadFactory;
import com.xxl.hex.core.serialise.ByteWriteFactory;

/**
 * response msg iface
 * @author xuxueli 2015-11-16 21:09:14
 */
public abstract class IResponse extends IHexMsg {
	
	private int code;
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
	
	@Override
	public byte[] toHexByte() {
		ByteWriteFactory writer = new ByteWriteFactory();
		
		// write IResponse
		writer.writeInt(code);
		int len = IHexMsg.getParamByteLen(msg);
		writer.writeInt(len);
		writer.writeString(msg, len);
		
		// write bottom response
		writer.write(super.toHexByte());
		
		return writer.getBytes();
	}
	@Override
	public IHexMsg fillHexByte(byte[] hexBytes) {
		ByteReadFactory reader = new ByteReadFactory(hexBytes);
		
		// read IResponse
		this.setCode(reader.readInt());
		this.setMsg(reader.readString(reader.readInt()));
		
		// read bottom response
		return super.fillHexByte(reader.readByteAll());
	}
	
}
