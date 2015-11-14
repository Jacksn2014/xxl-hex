package com.xxl.hex;

import com.xxl.hex.logic.ILogic;
import com.xxl.hex.logic.impl.DemoLogic;
import com.xxl.hex.msg.IRequest;
import com.xxl.hex.msg.request.DemoMsg;

public enum HexEnum {
	
	DEMO_REQUEST(1000, DemoMsg.class, DemoLogic.class);
	
	private int msgType;
	private Class<? extends IRequest> MsgClazz;
	private Class<? extends ILogic> LogicClazz;
	private HexEnum(int msgType, Class<? extends IRequest> MsgClazz, Class<? extends ILogic> LogicClazz){
		this.msgType = msgType;
		this.MsgClazz = MsgClazz;
		this.LogicClazz = LogicClazz;
	}
	
	public int getMsgType() {
		return msgType;
	}
	public Class<? extends IRequest> getMsgClazz() {
		return MsgClazz;
	}
	public Class<? extends ILogic> getLogicClazz() {
		return LogicClazz;
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
