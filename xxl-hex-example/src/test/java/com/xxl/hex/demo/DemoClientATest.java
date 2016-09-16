package com.xxl.hex.demo;

import com.xxl.hex.demo.msg.DemoRequest;
import com.xxl.hex.demo.msg.DemoResponse;
import com.xxl.hex.remote.client.HexClient;
import com.xxl.hex.serialise.JacksonUtil;

import java.lang.reflect.InvocationTargetException;

/**
 * 客户端调用示例01: 使用官网提供 "HexClient.handleObj" 发起API请求 (适用于双方均未Java语言)
 *
 * 特点: 使用方便, 但是需要获取Request和Response的Java类文件
 *
 */
public class DemoClientATest {

	private static final String BASE_URL = "http://localhost:8080/hex";
	private static final String mapping = "demohandler";
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		// 封装参数
		DemoRequest demoRequest = new DemoRequest();
		demoRequest.setPassphrase("qwerasdf");
		demoRequest.setA(1);
		demoRequest.setB(2);

		// invoke
		DemoResponse demoResponse = (DemoResponse) HexClient.handleObj(BASE_URL, mapping, demoRequest, DemoResponse.class);

		System.out.println("json=" + JacksonUtil.writeValueAsString(demoResponse));

	}

}
