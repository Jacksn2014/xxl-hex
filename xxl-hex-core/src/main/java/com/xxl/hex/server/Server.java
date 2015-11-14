package com.xxl.hex.server;

import com.xxl.hex.HexEnum;
import com.xxl.hex.codec.IRequest;
import com.xxl.hex.codec.IResponse;
import com.xxl.hex.serialise.EntityByteHexSeiralizer;
import com.xxl.hex.server.handler.IHandler;


public class Server {

	public static String messageReceive(String request_hex) throws Exception {
		// deserialize request
		IRequest request = (IRequest) EntityByteHexSeiralizer.deserializeRequest(request_hex);
		
		// handler
		IHandler handler = HexEnum.get(request.getMsgType()).getHandlerClazz().newInstance();
		IResponse response = handler.handle(request);
		
		// serialize response
		return EntityByteHexSeiralizer.serializeResponse(response);
	}
}
