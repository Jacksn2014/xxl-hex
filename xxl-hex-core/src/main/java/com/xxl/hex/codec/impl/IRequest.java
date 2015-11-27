package com.xxl.hex.codec.impl;

import com.xxl.hex.annotation.HexField;
import com.xxl.hex.codec.IHexMsg;
import com.xxl.hex.serialise.ByteReadFactory;
import com.xxl.hex.serialise.ByteWriteFactory;

/**
 * request msg iface
 * @author xuxueli 2015-11-16 21:09:49
 */
public abstract class IRequest extends IHexMsg {
	public static final int ifaceName_len = 64;
	
	@HexField(length=ifaceName_len)
	private String ifaceName;
	public String getIfaceName() {
		return ifaceName;
	}
	public void setIfaceName(String ifaceName) {
		this.ifaceName = ifaceName;
	}

	@Override
	public byte[] toHexByte(){
		ByteWriteFactory writer = new ByteWriteFactory();
		
		writer.writeString(this.getClass().getName(), ifaceName_len);
		writer.write(super.toHexByte());
		
		return writer.getBytes();
	}
	
	@Override
	public IHexMsg fillHexByte(byte[] hexBytes){
		ByteReadFactory reader = new ByteReadFactory(hexBytes);
		
		this.setIfaceName(reader.readString(ifaceName_len));
		return super.fillHexByte(reader.readByteAll());
	}
}
