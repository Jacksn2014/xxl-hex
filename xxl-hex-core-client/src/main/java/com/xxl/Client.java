package com.xxl;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.xxl.demo.msg.request.DemoRequest;
import com.xxl.demo.msg.response.DemoResponse;
import com.xxl.hex.serialise.ByteHexConverter;



public class Client {
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		DemoRequest request = new DemoRequest();
		request.setMsgType(5000);
		request.setParam("哈喽，我是client。");
		
		String request_hex = ByteHexConverter.byte2hex(request.toHexByte());
		System.out.println(request_hex);	// 88130000E59388E596BDEFBC8CE68891E698AF636C69656E
		
		// 通讯中...
		
		String response_hex = "C8000000737563636573730000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000007265733AE59388E596BDEFBC8CE68891E698AF63";
		DemoResponse msg = (DemoResponse) DemoResponse.class.newInstance().fillHexByte(ByteHexConverter.hex2Byte(response_hex));
		System.out.println(BeanUtils.describe(msg));
		
	}
	
}
