package com.xxl.hex;

import org.apache.commons.beanutils.BeanUtils;

import com.xxl.hex.client.Client;
import com.xxl.hex.codec.IResponse;
import com.xxl.hex.codec.request.DemoRequest;

public class HexTest {
	
	public static void main(String[] args) throws Exception {
		DemoRequest demorequest = new DemoRequest("jack", "hello world");
		demorequest.setMsgType(1000);
		
		IResponse response = Client.send(demorequest);
		
		System.out.println(BeanUtils.describe(response));
	}
	
}
