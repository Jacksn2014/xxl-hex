package com.xxl.hex.core.codec.impl;

import com.xxl.hex.core.codec.IHexMsg;
import com.xxl.hex.core.serialise.ByteReadFactory;
import com.xxl.hex.core.serialise.ByteWriteFactory;

/**
 * request msg iface
 * @author xuxueli 2015-11-16 21:09:49
 */
public abstract class IRequest extends IHexMsg {
	
	private String ifaceName = this.getClass().getName();
	public String getIfaceName() {
		return ifaceName;
	}
	public void setIfaceName(String ifaceName) {
		this.ifaceName = ifaceName;
	}

	@Override
	public byte[] toHexByte(){
		ByteWriteFactory writer = new ByteWriteFactory();
		
		// write IRequest
		int len = IHexMsg.getParamByteLen(ifaceName);
		writer.writeInt(len);
		writer.writeString(ifaceName, len);
		
		// write bottom request
		writer.write(super.toHexByte());
		
		return writer.getBytes();
	}
	
	@Override
	public IHexMsg fillHexByte(byte[] hexBytes){
		ByteReadFactory reader = new ByteReadFactory(hexBytes);
		
		// read IRequest
		this.setIfaceName(reader.readString(reader.readInt()));
		
		// read bottom request
		return super.fillHexByte(reader.readByteAll());
	}
}
