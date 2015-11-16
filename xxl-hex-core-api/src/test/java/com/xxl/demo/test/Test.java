package com.xxl.demo.test;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.xxl.demo.msg.request.DemoRequest;
import com.xxl.demo.msg.response.DemoResponse;
import com.xxl.hex.msg.IHexMsg;
import com.xxl.hex.serialise.ByteHexConverter;

public class Test {
	
	public static void main(String[] args) {
		testResponse();
	}
	
	public static void testResponse(){
		try {
			DemoResponse response = new DemoResponse();
			response.setCode(200);
			response.setMsg("success");
			response.setResult("hello");
			String hex = ByteHexConverter.byte2hex(response.toHexByte());
			System.out.println(hex);
			IHexMsg ins = DemoResponse.class.newInstance();
			ins.fillHexByte(ByteHexConverter.hex2Byte(hex));
			System.out.println("###" + BeanUtils.describe(response));
			System.out.println("###" + BeanUtils.describe(ins));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testRequest() {
		try {
			DemoRequest msg = new DemoRequest();
			msg.setMsgType(9999);
			msg.setParam("随碟附送的");
			
			String hex = ByteHexConverter.byte2hex(msg.toHexByte());
			System.out.println(hex);
			
			
			IHexMsg ins = DemoRequest.class.newInstance();
			ins.fillHexByte(ByteHexConverter.hex2Byte(hex));
			System.out.println("###" + BeanUtils.describe(msg));
			System.out.println("###" + BeanUtils.describe(ins));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
