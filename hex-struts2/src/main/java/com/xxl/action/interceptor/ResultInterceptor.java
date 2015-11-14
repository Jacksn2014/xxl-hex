package com.xxl.action.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xxl.action.base.BaseAction;
import com.xxl.action.hex.msg.IResponse;
import com.xxl.action.hex.msg.response.HexResponse;
import com.xxl.action.hex.util.MsgReflectUtil;
import com.xxl.core.exception.HexException;
import com.xxl.core.exception.JsonException;
import com.xxl.core.exception.WebException;
import com.xxl.core.util.IpAddressUtil;

/**
 * 相应处理
 * 
 * @author xuxueli
 */
@SuppressWarnings("serial")
public class ResultInterceptor extends AbstractInterceptor {

	public String intercept(ActionInvocation invocation) throws Exception {
		Action invokeAction = (Action) invocation.getAction();
		if (invokeAction instanceof BaseAction) {
			BaseAction baseAction = (BaseAction) invokeAction;

			/*Class<?> clazz = invocation.getAction().getClass();
			Method target = clazz.getMethod(invocation.getProxy().getMethod(), BaseAction.class);
			JsonpInfo jsonpInfo = target.getAnnotation(JsonpInfo.class);*/

			String resultType = baseAction.getResultType(); // 请求类型
			String result = null;
			try {
				// pre拦截
				baseAction.setClientIp(IpAddressUtil.getIp()); // 设置客户端访问IP

				// 执行调用
				result = invocation.invoke();

				// post拦截
				if ("json".equals(resultType)) {
					return "success_json";
				} else if ("text".equals(resultType)) {
					return "success_text";
				} else {
					return result;
				}
			} catch (WebException e) {
				if ("json".equals(resultType)) {
					throw new JsonException(e.getExceptionKey(), e.getExceptionMsg());
				} else if ("text".equals(resultType)) {
					IResponse response = new HexResponse(1, e.getExceptionMsg());
					String hex = MsgReflectUtil.responseToHex(response);
					throw new HexException(hex);
				} else {
					baseAction.setReturnCode(e.getExceptionKey());
					baseAction.setReturnMsg(e.getExceptionMsg());
					return "defaultException";
				}
			}
		}
		return null;
	}

}
