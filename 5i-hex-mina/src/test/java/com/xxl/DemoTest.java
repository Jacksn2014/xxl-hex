package com.xxl;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.xxl.hex.util.MsgReflectUtil;
import com.xxl.hex.util.ResponseStreamFactory;
import com.xxl.util.MessageUtil;

public class DemoTest {

	@Test
	public void testMain() {		
		ResponseStreamFactory response = new ResponseStreamFactory();
		
		response.writeInt(1000);	//消息类型
		response.writeInt(0);		//时间戳	
		
		response.writeInt(123456);
		response.writeString("token", 32);
		
		String signature = MsgReflectUtil.generateSignature(response.toBytes());
		
		response = new ResponseStreamFactory();
		response.writeString(signature, 32);	//签名
		response.writeInt(1000);				//消息类型
		response.writeInt(0);					//时间戳
		
		response.writeInt(123456);
		response.writeString("token", 32);	
		
		String hex = response.getHexResponse();
		
		for (int i = 0; i < 500; i++) {
			System.out.println("**************" + i);
			MessageUtil.sendMessage(hex);
			try {
				TimeUnit.MILLISECONDS.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
