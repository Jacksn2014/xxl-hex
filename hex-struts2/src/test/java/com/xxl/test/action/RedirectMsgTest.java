package com.xxl.test.action;

import org.junit.Test;
import org.junit.runners.Suite.SuiteClasses;

import com.xxl.action.hex.util.MsgReflectUtil;
import com.xxl.action.hex.util.ResponseStreamFactory;

@SuiteClasses(RedirectMsgTest.class)
public class RedirectMsgTest {

	
	@Test
	public void checkLoginMsg() {		
		ResponseStreamFactory response = new ResponseStreamFactory();
		
		response.writeInt(1000);//消息类型
		response.writeInt(0);//时间戳
		
		response.writeInt(590004858);
		response.writeString("GLIIJJFKNOKQOSHM", 32);
		response.writeInt(1000);
		
		String signature = MsgReflectUtil.generateSignature(response.toBytes());
		
		response = new ResponseStreamFactory();
		response.writeString(signature, 32);//签名
		response.writeInt(1000);//消息类型
		response.writeInt(0);//时间戳
		
		response.writeInt(590004858);
		response.writeString("GLIIJJFKNOKQOSHM", 32);
		response.writeInt(1000);
		
		String hex = response.getHexResponse();
		System.out.println("http://localhost:8080/hex-struts2/data/handleMsg.do?hex="+hex + "&resultType=html");
		
	}
	
}



