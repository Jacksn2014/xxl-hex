package com.xxl.server.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxl.core.exception.WebException;
import com.xxl.hex.HexEnum;
import com.xxl.hex.logic.ILogic;
import com.xxl.hex.msg.IRequest;
import com.xxl.hex.msg.IResponse;
import com.xxl.hex.msg.response.HexResponse;

/**
 * 消息处理器
 * @author xuxueli
 *
 */
public class MsgHandler extends IoHandlerAdapter {
	private static transient Logger logger = LoggerFactory.getLogger(MsgHandler.class);
	private final int idle = 30000;
	
	/*
	 * 接收消息
	 * @see org.apache.mina.core.service.IoHandlerAdapter#messageReceived(org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	public void messageReceived(IoSession session, Object message)
	throws Exception {
		IRequest msgInfo = (IRequest) message;
		logger.info("[msg info : {}]", msgInfo.toString());
		
		ILogic logic = (ILogic) HexEnum.get(msgInfo.getMsgType()).getLogicClazz().newInstance();
		IResponse response = logic.handle(msgInfo, "");		
		
		session.write(response);
	}
	
	/*
	 * 
	 * @see org.apache.mina.core.service.IoHandlerAdapter#sessionOpened(org.apache.mina.core.session.IoSession)
	 */
	public void sessionOpened(IoSession session) throws Exception {
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, idle); 
	}
	
	/*
	 * 
	 * @see org.apache.mina.core.service.IoHandlerAdapter#exceptionCaught(org.apache.mina.core.session.IoSession, java.lang.Throwable)
	 */
	public void exceptionCaught(IoSession session, Throwable cause)
	throws Exception {
		logger.error("server catch exception:", cause);
		
		String errorMsg = cause.getMessage();
		if (cause instanceof WebException) {
			WebException webException = (WebException) cause;
			errorMsg = webException.getMessage();
		}
		
		HexResponse response = new HexResponse(errorMsg);
		session.write(response);
		session.close(true);
	}
}
