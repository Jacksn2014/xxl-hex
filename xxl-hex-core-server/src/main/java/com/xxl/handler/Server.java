package com.xxl.handler;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.xxl.demo.msg.request.DemoRequest;
import com.xxl.demo.msg.response.DemoResponse;
import com.xxl.handler.impl.DemoHandler;
import com.xxl.hex.serialise.ByteHexConverter;

public class Server {
	
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// message receive
		String request_hex = "88130000E59388E596BDEFBC8CE68891E698AF636C69656E";

		DemoRequest msg = (DemoRequest) DemoRequest.class.newInstance().fillHexByte(ByteHexConverter.hex2Byte(request_hex));
		System.out.println(BeanUtils.describe(msg));
		
		DemoHandler handler = new DemoHandler();
		DemoResponse response = (DemoResponse) handler.handle(msg);
		
		// message write back
		String response_hex = ByteHexConverter.byte2hex(response.toHexByte());
		System.out.println(response_hex);	// C8000000737563636573730000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000007265733AE59388E596BDEFBC8CE68891E698AF63
	}
	
}
