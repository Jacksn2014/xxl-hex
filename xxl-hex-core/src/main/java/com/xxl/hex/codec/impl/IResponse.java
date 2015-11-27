package com.xxl.hex.codec.impl;

import com.xxl.hex.annotation.HexField;
import com.xxl.hex.codec.IHexMsg;
import com.xxl.hex.serialise.ByteReadFactory;
import com.xxl.hex.serialise.ByteWriteFactory;

/**
 * response msg iface
 * @author xuxueli 2015-11-16 21:09:14
 */
public abstract class IResponse extends IHexMsg {
	public static final int msg_len = 64;
	
	private int code;
	@HexField(length=msg_len)
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
		
		writer.writeInt(code);
		writer.writeString(msg, msg_len);
		writer.write(super.toHexByte());
		
		return writer.getBytes();
	}
	@Override
	public IHexMsg fillHexByte(byte[] hexBytes) {
		ByteReadFactory reader = new ByteReadFactory(hexBytes);
		
		this.setCode(reader.readInt());
		this.setMsg(reader.readString(msg_len));
		return super.fillHexByte(reader.readByteAll());
	}
	
}
