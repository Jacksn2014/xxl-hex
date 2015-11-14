package com.xxl.hex;

import com.xxl.hex.logic.ILogic;
import com.xxl.hex.msg.IHexMsg;
import com.xxl.hex.msg.IRequest;
import com.xxl.hex.msg.IResponse;
import com.xxl.hex.msg.request.DemoMsg;
import com.xxl.hex.util.MsgReflectUtil;

public class HexTest {
	// request(4.type + msg.hex) >>> 4.type + msg.hex
	public static void main(String[] args) throws Exception {
		DemoMsg request = new DemoMsg();
		request.setMsgType(1000);
		request.setUserId(9999);
		request.setUserToken("token");
		
		String hex = MsgReflectUtil.msg2hex(request);
		
		IHexMsg request2 = MsgReflectUtil.hex2msg(hex);
		ILogic handler = HexEnum.get(request2.getMsgType()).getLogicClazz().newInstance();
		IResponse response = handler.handle((IRequest)request2, "");
		
		System.out.println(response);
	}
	
	
}
