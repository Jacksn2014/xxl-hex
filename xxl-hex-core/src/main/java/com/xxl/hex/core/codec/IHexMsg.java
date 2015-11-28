package com.xxl.hex.core.codec;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxl.hex.core.serialise.ByteReadFactory;
import com.xxl.hex.core.serialise.ByteWriteFactory;

/**
 * msg iface
 * @author xuxueli  2015-11-16 21:08:53
 */
public abstract class IHexMsg {
	public static Logger logger = LoggerFactory.getLogger(IHexMsg.class);
	
	/**
	 * get bytes length*2 of given str
	 * @return
	 */
	public static int getParamByteLen(String str){
		if (str==null || str.length()==0) {
			return 0;
		}
		// because java base on unicode, and one china code's length is one, but it's cost 2 bytes.
		int len = str.getBytes().length * 2;
		if (len % 4 != 0) {
			// Length is best in multiples of four
			len = (len/4 + 1) * 4;
		}
		return len;
	}
	
	/**
	 * serialize this bottom object to hex bytes  (only support int and string)
	 * @return
	 */
	public byte[] toHexByte(){
		ByteWriteFactory writer = new ByteWriteFactory();
		try {
			Field[] allFields = this.getClass().getDeclaredFields();
			for (Field fieldInfo : allFields) {
				Class<?> fieldClazz = fieldInfo.getType();
				fieldInfo.setAccessible(true);
				if (fieldClazz == String.class) {
					String value = (String) fieldInfo.get(this);
					int len = IHexMsg.getParamByteLen(value);
					writer.writeInt(len);
					writer.writeString(value, len);
				} else if (fieldClazz == Integer.TYPE) {
					int value = fieldInfo.getInt(this);
					writer.writeInt(value);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		byte[] bytes = writer.getBytes();
		return bytes;
	}
	
	/**
	 * deserialize hex to given bottom clazz
	 * @param hexBytes
	 * @param msgClazz
	 * @return
	 */
	public IHexMsg fillHexByte(byte[] hexBytes){
		ByteReadFactory reader = new ByteReadFactory(hexBytes);
		try {
			IHexMsg msgInfo = this;
			Field[] allFields = this.getClass().getDeclaredFields();
			for (Field fieldInfo : allFields) {
				Class<?> fieldClazz = fieldInfo.getType();
				fieldInfo.setAccessible(true);
				if (fieldClazz == String.class) {
					String sValue = reader.readString(reader.readInt());
					fieldInfo.set(msgInfo, sValue);
				} else if (fieldClazz == Integer.TYPE) {				
					int iValue = reader.readInt();
					fieldInfo.set(msgInfo, iValue);
				}								
			}
			return this;
		} catch (Exception e) {
			logger.error("", e);
		}
		return this;		
	}
	
}
