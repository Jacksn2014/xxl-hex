package com.xxl.hex.msg.impl;

import com.xxl.hex.msg.IHexMsg;
import com.xxl.hex.serialise.ByteReadFactory;
import com.xxl.hex.serialise.ByteWriteFactory;

/**
 * request msg iface
 * @author xuxueli 2015-11-16 21:09:49
 */
public abstract class IRequest extends IHexMsg {
	
	private int msgType;
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	
	@Override
	public byte[] toHexByte(){
		ByteWriteFactory writer = new ByteWriteFactory();
		
		writer.writeInt(msgType);
		writer.write(super.toHexByte());
		
		return writer.getBytes();
	}
	
	@Override
	public IHexMsg fillHexByte(byte[] hexBytes){
		ByteReadFactory reader = new ByteReadFactory(hexBytes);
		
		this.setMsgType(reader.readInt());
		return super.fillHexByte(reader.readByteAll());
	}
}
