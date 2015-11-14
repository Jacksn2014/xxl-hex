package com.xxl.action.hex.logic.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.xxl.action.hex.logic.ILogic;
import com.xxl.action.hex.msg.IRequest;
import com.xxl.action.hex.msg.IResponse;
import com.xxl.action.hex.msg.request.RedirectMsg;
import com.xxl.action.hex.msg.response.RedirectResponse;
import com.xxl.core.exception.WebException;

/**
 * 授权认证
 * @author 
 */

public class RedirectLogic extends ILogic {
	/*
	 * 处理登陆业务逻辑
	 * @see pook.puic.lobby.action.logic.ILogic#handle(pook.puic.lobby.action.msg.IRequest, java.lang.String)
	 */
	public IResponse handle(IRequest msg, String clientIp) 
	throws WebException {
		RedirectMsg msgInfo = (RedirectMsg) msg;
			
		// 校验登录状态
		
		// 获取重定向地址信息
		String redirectInfo = actionType.get(msgInfo.getActionType());
		if (StringUtils.isBlank(redirectInfo)) {
			redirectInfo = "../maintenance.html";
		}
		redirectInfo = redirectInfo + "?aa=" + Math.random();
		
		return new RedirectResponse(redirectInfo);
	}
	
	/**
	 * 重定向map
	 */
	private static Map<Integer, String> actionType = new HashMap<Integer, String>();
	static{
		actionType.put(1000, "../demo/test.do");
	}

}
