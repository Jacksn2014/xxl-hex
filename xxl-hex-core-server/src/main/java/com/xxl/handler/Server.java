package com.xxl.handler;

import org.apache.commons.beanutils.BeanUtils;

import com.xxl.demo.msg.response.DemoResponse;
import com.xxl.handler.impl.DemoHandler;
import com.xxl.hex.msg.impl.IRequest;
import com.xxl.hex.serialise.ByteHexConverter;
import com.xxl.hex.serialise.ByteReadFactory;

public class Server {
	
	
	public static void main(String[] args) throws Exception {
		// message receive
		String request_hex = "636F6D2E78786C2E64656D6F2E6D73672E726571756573742E44656D6F5265717565737400000000000000000000000000000000000000000000000000000000E59388E596BDEFBC8CE68891E698AF636C69656E";

		ByteReadFactory reader = new ByteReadFactory(ByteHexConverter.hex2Byte(request_hex));
		String ifaceName = reader.readString(64);
		System.out.println(ifaceName);
		
		IRequest msg = (IRequest) Class.forName(ifaceName).newInstance();
		msg.fillHexByte(ByteHexConverter.hex2Byte(request_hex));
		System.out.println(BeanUtils.describe(msg));
		
		DemoHandler handler = new DemoHandler();
		DemoResponse response = (DemoResponse) handler.handle(msg);
		
		// message write back
		String response_hex = ByteHexConverter.byte2hex(response.toHexByte());
		System.out.println(response_hex);	// C8000000737563636573730000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000007265733AE59388E596BDEFBC8CE68891E698AF63
	}
	
}
