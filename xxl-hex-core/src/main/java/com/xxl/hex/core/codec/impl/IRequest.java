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
		byte[] origin_byte = writer.getBytes();
		
		// prefix append length
		ByteWriteFactory writer2 = new ByteWriteFactory();
		writer2.writeInt(origin_byte.length + 4);
		writer2.write(origin_byte);
		return writer2.getBytes();
	}
	
	@Override
	public IHexMsg fillHexByte(byte[] hexBytes){
		ByteReadFactory reader = new ByteReadFactory(hexBytes);
		// totalLength
		int totalLength = reader.readInt();
		if (hexBytes.length != totalLength) {
			throw new IllegalArgumentException(">>>>>>>>>> xxl-hex, request length not equal");
		}
		
		// read IRequest
		this.setIfaceName(reader.readString(reader.readInt()));
		// read bottom request
		return super.fillHexByte(reader.readByteAll());
	}
}
