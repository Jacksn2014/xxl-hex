package com.xxl.hex;

import com.xxl.hex.codec.IRequest;
import com.xxl.hex.codec.IResponse;
import com.xxl.hex.codec.request.DemoRequest;
import com.xxl.hex.codec.response.DemoResponse;
import com.xxl.hex.server.handler.IHandler;
import com.xxl.hex.server.handler.impl.DemoHandler;

public enum HexEnum {
	
	DEMO_REQUEST(1000, DemoRequest.class, DemoResponse.class, DemoHandler.class);
	
	private int msgType;
	private Class<? extends IRequest> requestClazz;
	private Class<? extends IResponse> responseClazz;
	private Class<? extends IHandler> handlerClazz;
	private HexEnum(int msgType, Class<? extends IRequest> requestClazz, 
			Class<? extends IResponse> responseClazz, Class<? extends IHandler> handlerClazz){
		this.msgType = msgType;
		this.requestClazz = requestClazz;
		this.responseClazz = responseClazz;
		this.handlerClazz = handlerClazz;
	}
	
	public int getMsgType() {
		return msgType;
	}
	public Class<? extends IRequest> getRequestClazz() {
		return requestClazz;
	}
	public Class<? extends IResponse> getResponseClazz() {
		return responseClazz;
	}
	public Class<? extends IHandler> getHandlerClazz() {
		return handlerClazz;
	}
	public static HexEnum get(int msgType){
		for (HexEnum item : HexEnum.values()) {
			if (item.getMsgType() == msgType) {
				return item;
			}
		}
		return null;
	}
	
}
