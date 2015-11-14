package com.xxl.test.action;

import org.junit.Test;
import org.junit.runners.Suite.SuiteClasses;

import com.xxl.action.hex.util.MsgReflectUtil;
import com.xxl.action.hex.util.RequestStreamFactory;
import com.xxl.action.hex.util.ResponseStreamFactory;
import com.xxl.core.util.HttpClient4Util;

@SuiteClasses(DemoTest.class)
public class DemoTest {
	@Test
	public void test() {
		ResponseStreamFactory response = new ResponseStreamFactory();
		response.writeInt(1001);
		response.writeInt(0);
		
		response.writeInt(590004858);
		response.writeString("BTPLCPJPDTLU", 32);
		String signature = MsgReflectUtil.generateSignature(response.toBytes());
		
		response = new ResponseStreamFactory();
		response.writeString(signature, 32);
		response.writeInt(1001);
		response.writeInt(0);
		
		response.writeInt(590004858);
		response.writeString("BTPLCPJPDTLU", 32);
		
		String hex = response.getHexResponse();
		System.out.println(hex);
		
		String resp = HttpClient4Util.httpGet("http://localhost:8080/hex-struts2/data/handleMsg.do", "hex=" + hex + "&resultType=text");
		RequestStreamFactory request = new RequestStreamFactory();
		request.readRequestHex(resp);
		
		
		int ret = request.readInt();
		System.out.println("ret : " + ret);
		if (ret == 0) {
			System.out.println("intValue : " + request.readInt());
			System.out.println("strValue : " + request.readString(64));
		} else {
			System.out.println("msg : " + request.readString(128));
		}
		
		
	}
	
	public static void main(String[] args) {
	}
}
