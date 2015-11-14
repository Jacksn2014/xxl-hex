package com.xxl.hex.client;

import com.xxl.hex.codec.IRequest;
import com.xxl.hex.codec.IResponse;
import com.xxl.hex.serialise.EntityByteHexSeiralizer;
import com.xxl.hex.server.Server;

public class Client {
	
	public static IResponse send(IRequest request) throws Exception {
		// serialize request
		String request_hex = EntityByteHexSeiralizer.serializeRequest(request);
		
		// send
		String response_hex = Server.messageReceive(request_hex);
		
		// derialize response
		IResponse response = (IResponse) EntityByteHexSeiralizer.deserializeResponse(response_hex, request.getMsgType());
		return response;
	}
}
