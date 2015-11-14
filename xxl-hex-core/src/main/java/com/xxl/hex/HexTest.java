package com.xxl.hex;

import java.lang.reflect.Field;

import com.xxl.hex.logic.ILogic;
import com.xxl.hex.msg.IHexMsg;
import com.xxl.hex.msg.IRequest;
import com.xxl.hex.msg.IResponse;
import com.xxl.hex.msg.request.DemoMsg;
import com.xxl.hex.util.MsgReflectUtil;

public class HexTest {
	
	public static void main(String[] args) {
		DemoMsg request = new DemoMsg();
		request.setMsgType(1000);
		request.setUserId(9999);
		request.setUserToken("token");
		
		System.out.println(request.getClass().getName());
		System.out.println(request.getClass().getSuperclass().getName());
		System.out.println(request.getClass().getSuperclass().getSuperclass().getName());
		System.out.println(request.getClass().getSuperclass().getSuperclass().getSuperclass().getName());
		System.out.println(Object.class.getClass().getName());
		//Field[] allFields = request.getClass().getDeclaredFields();
		Field[] allFields = request.getClass().getFields();
		for (Field field : allFields) {
			//System.out.println(field);
		}
	}
	
	// request(4.type + msg.hex) >>> 4.type + msg.hex
	public static void main2(String[] args) throws Exception {
		DemoMsg request = new DemoMsg();
		request.setMsgType(1000);
		request.setUserId(9999);
		request.setUserToken("token");
		
		String hex = MsgReflectUtil.msg2hex(request);
		
		IHexMsg request2 = MsgReflectUtil.hex2msg(hex);
		ILogic handler = HexEnum.get(request2.getMsgType()).getLogicClazz().newInstance();
		IResponse response = handler.handle((IRequest)request2, "");
		
		System.out.println(response);
	}
	
	
	
}
