package com.xxl;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.xxl.demo.msg.request.DemoRequest;
import com.xxl.demo.msg.response.DemoResponse;
import com.xxl.hex.serialise.ByteHexConverter;



public class Client {
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		DemoRequest request = new DemoRequest();
		request.setParam("哈喽，我是client。");
		
		String request_hex = ByteHexConverter.byte2hex(request.toHexByte());
		System.out.println(request_hex);	// 636F6D2E78786C2E64656D6F2E6D73672E726571756573742E44656D6F5265717565737400000000000000000000000000000000000000000000000000000000E59388E596BDEFBC8CE68891E698AF636C69656E
		
		// 通讯中...
		
		String response_hex = "C8000000737563636573730000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000007265733AE59388E596BDEFBC8CE68891E698AF63";
		DemoResponse msg = (DemoResponse) DemoResponse.class.newInstance().fillHexByte(ByteHexConverter.hex2Byte(response_hex));
		System.out.println(BeanUtils.describe(msg));
		
	}
	
}
