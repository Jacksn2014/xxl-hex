package com.xxl.hex.util;

import java.lang.reflect.Field;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxl.hex.HexEnum;
import com.xxl.hex.annotation.FieldDef;
import com.xxl.hex.msg.IHexMsg;
import com.xxl.hex.msg.request.DemoMsg;

public class MsgReflectUtil {
	private static transient Logger logger = LoggerFactory.getLogger(MsgReflectUtil.class);
	
	/**
	 * IHexMsg 2 hex[msgType + msg.data]
	 */
	public static String msg2hex(IHexMsg msg){
		if (msg == null) {
			return null;
		}
		ResponseStreamFactory response = new ResponseStreamFactory();
		try {
			
			Field[] allFields = msg.getClass().getDeclaredFields();
			
			response.writeInt(msg.getMsgType());
			for (Field fieldInfo : allFields) {
				Class<?> fieldClazz = fieldInfo.getType();
				fieldInfo.setAccessible(true);
				if (fieldClazz == String.class) {
					FieldDef fieldDef = fieldInfo.getAnnotation(FieldDef.class);
					String value = (String) fieldInfo.get(msg);
					response.writeString(value, fieldDef.fieldLength());
				} else if (fieldClazz == Integer.TYPE) {
					int value = fieldInfo.getInt(msg);
					response.writeInt(value);
				}
			}
		} catch (Exception e) {
			logger.error("[responseToHex reflection exception.]", e);
		}
		return response.getHexResponse();
	}
	
	/**
	 * IHexMsg 2 signature
	 */
	public static String requestToSignature (IHexMsg msg) {
		return SignatureUtil.generateSignature(msg2hex(msg).getBytes());
	}
	
	/**
	 * hex[msgType + msg.data] 2 IHexMsg
	 */
	public static IHexMsg hex2msg(String hex){
		if (hex == null || hex.trim().length() == 0) {
			return null;
		}
		RequestStreamFactory request = new RequestStreamFactory();
		boolean readOver = request.readRequestHex(hex);
		if (!readOver) {
			return null;
		}
		
		int msgType = request.readInt();
		Class<?> msgClazz = HexEnum.get(msgType).getMsgClazz();
		try {
			Object msgInfo = msgClazz.newInstance();
			IHexMsg requestMsgInfo = (IHexMsg) msgInfo; 
			Field[] allFields = msgClazz.getDeclaredFields();
			for (Field fieldInfo : allFields) {
				Class<?> fieldClazz = fieldInfo.getType();
				fieldInfo.setAccessible(true);
				if (fieldClazz == String.class) {
					FieldDef fieldDef = fieldInfo.getAnnotation(FieldDef.class);
					String sValue = request.readString(fieldDef.fieldLength());
					fieldInfo.set(msgInfo, sValue);
				} else if (fieldClazz == Integer.TYPE) {				
					int iValue = request.readInt();
					fieldInfo.set(msgInfo, iValue);
				}								
			}
			return requestMsgInfo;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	
}
