package com.xxl.action.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xxl.action.base.DataBaseAction;
import com.xxl.action.hex.msg.IRequest;
import com.xxl.action.hex.util.MsgReflectUtil;
import com.xxl.core.exception.ExceptionKey;
import com.xxl.core.exception.WebException;

/**
 * 解析16进制消息 + 校验签名
 * @author xuxueli
 */
@SuppressWarnings("serial")
public class DataInterceptor extends AbstractInterceptor {
	private static transient Logger logger = LoggerFactory.getLogger(DataInterceptor.class);
	
	public String intercept(ActionInvocation invocation) throws Exception {
		Action action = (Action) invocation.getAction();
		if (action instanceof DataBaseAction) {
			DataBaseAction dataAction = (DataBaseAction) action;
			String hex = dataAction.getHex();
			
			// 解析16进制消息
			IRequest msg = MsgReflectUtil.hexToRequest(hex);
			if (msg == null) {
				throw new WebException(ExceptionKey.defaultKey, "请求参数异常");
			}
			dataAction.setMsg(msg);
			
			// 校验签名
			String paramSignature = msg.getSignature();
			String generateSignature = MsgReflectUtil.requestToSignature(msg);
			if (!paramSignature.equals(generateSignature)) {
				logger.info("hexToRequest signature not equal, paramSignature : {} generateSignature : {}", new Object[]{paramSignature, generateSignature});
				throw new WebException(ExceptionKey.defaultKey, "加密串校验异常");
			}
		}
		return invocation.invoke();
	}
	
}
