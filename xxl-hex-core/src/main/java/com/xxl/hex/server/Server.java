package com.xxl.hex.server;

import com.xxl.hex.HexEnum;
import com.xxl.hex.codec.IRequest;
import com.xxl.hex.codec.IResponse;
import com.xxl.hex.server.handler.IHandler;
import com.xxl.hex.util.HexSeiralizer;


public class Server {

	public static String messageReceive(String request_hex) throws Exception {
		// deserialize request
		IRequest request = (IRequest) HexSeiralizer.deserializeRequest(request_hex);
		
		// handler
		IHandler handler = HexEnum.get(request.getMsgType()).getHandlerClazz().newInstance();
		IResponse response = handler.handle(request);
		
		// serialize response
		return HexSeiralizer.serializeResponse(response);
	}
}
