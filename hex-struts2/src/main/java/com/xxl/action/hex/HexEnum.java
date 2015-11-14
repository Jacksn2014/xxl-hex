package com.xxl.action.hex;

import com.xxl.action.hex.logic.ILogic;
import com.xxl.action.hex.logic.impl.DemoLogic;
import com.xxl.action.hex.logic.impl.RedirectLogic;
import com.xxl.action.hex.msg.IRequest;
import com.xxl.action.hex.msg.request.DemoMsg;
import com.xxl.action.hex.msg.request.RedirectMsg;

public enum HexEnum {
	HANDLE_REDIRECT_REQUEST(1000, RedirectMsg.class, RedirectLogic.class),	// 页面请求
	HANDLE_DEMO(1001, DemoMsg.class, DemoLogic.class);					// 打开道具
	
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
