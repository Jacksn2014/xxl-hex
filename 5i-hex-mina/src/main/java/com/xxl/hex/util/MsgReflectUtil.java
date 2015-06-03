package com.xxl.hex.util;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxl.hex.HexEnum;
import com.xxl.hex.annotation.FieldDef;
import com.xxl.hex.msg.IRequest;
import com.xxl.hex.msg.IResponse;

public class MsgReflectUtil {
	private static transient Logger logger = LoggerFactory.getLogger(MsgReflectUtil.class);
	
	/**
	 * 16进制消息-->IRequest
	 * @param request
	 * @return
	 */
	public static IRequest hexToRequest(String hex) {
		if (hex == null || hex.trim().length() == 0) {
			return null;
		}
		
		// 实例化解析工厂
		RequestStreamFactory request = new RequestStreamFactory();
		boolean readOver = request.readRequestHex(hex);
		if (!readOver) {
			return null;
		}
		
		// 通用消息解析
		String signature = request.readString(32);	// 获取签名密钥
		int msgType = request.readInt();			// 获取消息类型
		int timestamp = request.readInt();			// 获取时间戳	
		
		Class<?> msgClazz = HexEnum.get(msgType).getMsgClazz();	// 获取消息的clazz定义
		try {
			Object msgInfo = msgClazz.newInstance();
			// 通用消息解析
			IRequest requestMsgInfo = (IRequest) msgInfo; 
			requestMsgInfo.setMsgType(msgType);
			requestMsgInfo.setTimestamp(timestamp);
			requestMsgInfo.setSignature(signature);
			
			// 实例消息解析
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
			logger.info("[hexToRequest reflection success : {} msg info : {}]", new Object[]{msgClazz.getName(), requestMsgInfo.toString()});
			return requestMsgInfo;
		} catch (Exception e) {
			logger.error("[hexToRequest reflection exception.]", e);
		}
		
		return null;
	}
	
	/**
	 * IRequest-->生成签名
	 * @param request
	 * @return
	 */
	public static String requestToSignature (IRequest obj) {
		ResponseStreamFactory response = new ResponseStreamFactory();
		
		try {
			// 通用消息
			response.writeInt(obj.getMsgType());
			response.writeInt(obj.getTimestamp());
			// 实例消息
			Field[] allFields = obj.getClass().getDeclaredFields();
			for (Field fieldInfo : allFields) {
				Class<?> fieldClazz = fieldInfo.getType();

				fieldInfo.setAccessible(true);
				if (fieldClazz == String.class) {
					FieldDef fieldDef = fieldInfo.getAnnotation(FieldDef.class);
					String value = (String) fieldInfo.get(obj);
					response.writeString(value, fieldDef.fieldLength());
				} else if (fieldClazz == Integer.TYPE) {
					int value = fieldInfo.getInt(obj);
					response.writeInt(value);
				}
			}
		} catch (Exception e) {
			logger.error("[requestToSignature exception.]", e);
		}
		
		return generateSignature(response.toBytes());
	}
	
	
	// 签名秘钥
	private static final String oridinalSuffix = "stpx1!#s0f8p6v5sdq";
	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	// 十六进制编码
	protected static char[] encodeHex(byte[] data) {
		int l = data.length;
		char[] out = new char[l << 1];
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}
		return out;
	}
	// 生成签名密钥
	public static String generateSignature(byte[] bytes) {
		byte[] original = new byte[bytes.length + 18];
		System.arraycopy(bytes, 0, original, 0, bytes.length);
		byte[] keys = oridinalSuffix.getBytes();
		for (int index = 0; index < keys.length; index++) {
			original[bytes.length + index] = keys[index];
		}
		
		MessageDigest msgDigest = null;
		try {
			msgDigest = MessageDigest.getInstance("MD5");
			msgDigest.update(original);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("System doesn't support MD5 algorithm.");
		}
		
		return String.valueOf(encodeHex(msgDigest.digest()));
	}
	
	/**
	 * IResponse-->16进制消息
	 * @param obj
	 * @return
	 */
	public static String responseToHex(IResponse obj) {
		ResponseStreamFactory response = new ResponseStreamFactory();
		
		try {
			// 通用消息
			response.writeInt(obj.getRet());
			// 实例消息
			Field[] allFields = obj.getClass().getDeclaredFields();		
			for (Field fieldInfo : allFields) {
				Class<?> fieldClazz = fieldInfo.getType();

				fieldInfo.setAccessible(true);
				if (fieldClazz == String.class) {
					FieldDef fieldDef = fieldInfo.getAnnotation(FieldDef.class);
					String value = (String) fieldInfo.get(obj);
					response.writeString(value, fieldDef.fieldLength());
				} else if (fieldClazz == Integer.TYPE) {
					int value = fieldInfo.getInt(obj);
					response.writeInt(value);
				}
			}
		} catch (Exception e) {
			logger.error("[responseToHex reflection exception.]", e);
		}
		return response.getHexResponse();
	}
	
}
