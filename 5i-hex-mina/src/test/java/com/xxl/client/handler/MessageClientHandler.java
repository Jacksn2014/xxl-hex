package com.xxl.client.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxl.hex.util.RequestStreamFactory;

public class MessageClientHandler extends IoHandlerAdapter {
	private static transient Logger logger = LoggerFactory.getLogger(MessageClientHandler.class);

	public void messageReceived(IoSession session, Object message)throws Exception {
		String resp = (String) message;
		RequestStreamFactory request = new RequestStreamFactory();
		request.readRequestHex(resp);
		
		int ret = request.readInt();
		if (ret == 0) {
			logger.info("[response success]");	
		} else {
			String msg = request.readString(128);
			logger.info("[response error, msg:{}]", msg);
		}
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		// String msgInfo = (String) message;
		// System.out.println("client msg info : " + msgInfo);
	}

	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}
}
