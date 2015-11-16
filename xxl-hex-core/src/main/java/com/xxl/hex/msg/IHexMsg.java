package com.xxl.hex.msg;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxl.hex.annotation.HexField;
import com.xxl.hex.serialise.ByteReadFactory;
import com.xxl.hex.serialise.ByteWriteFactory;

/**
 * msg iface
 * @author xuxueli  2015-11-16 21:08:53
 */
public abstract class IHexMsg {
	public static Logger logger = LoggerFactory.getLogger(IHexMsg.class);
	
	/**
	 * serialize this object to hex bytes
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
					HexField fieldDef = fieldInfo.getAnnotation(HexField.class);
					if (fieldDef != null) {
						String value = (String) fieldInfo.get(this);
						writer.writeString(value, fieldDef.length());
					}
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
	 * deserialize hex to given clazz 
	 * @param hex
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
					HexField fieldDef = fieldInfo.getAnnotation(HexField.class);
					String sValue = reader.readString(fieldDef.length());
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
