package com.xxl.action.interceptor;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xxl.action.base.LoginBaseAction;
import com.xxl.action.base.LoginIdentity;
import com.xxl.action.hex.util.RequestStreamFactory;
import com.xxl.core.constant.CommonDic;
import com.xxl.core.exception.ExceptionKey;
import com.xxl.core.exception.WebException;
import com.xxl.core.util.CookieUtil;
import com.xxl.core.util.XMemcachedUtil;

@SuppressWarnings("serial")
public class LoginStateInterceptor extends AbstractInterceptor {
	private static transient Logger logger = LoggerFactory.getLogger(LoginStateInterceptor.class);

	/**
	 * 登陆状态拦截器
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		Action action = (Action) invocation.getAction();
		if (action instanceof LoginBaseAction) {

			ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
			String loginInfo = CookieUtil.getValue(null,CommonDic.LOGIN_IDENTITY_KEY);
			if (loginInfo == null || loginInfo.trim().length() == 0) {
				logger.info("login.identity.invalid, IP:{}", new Object[] { ((LoginBaseAction) action).getClientIp() });
				throw new WebException(ExceptionKey.defaultKey, "登陆失效");
			}

			// 解析登陆状态信息
			LoginIdentity identity = parseLoginInfo(loginInfo);
			((LoginBaseAction) action).setLoginIdentity(identity);
		}

		return invocation.invoke();
	}

	/**
	 * 解析登陆状态信息
	 * 
	 * @param loginInfo
	 * @return
	 * @throws WebException
	 */
	private static LoginIdentity parseLoginInfo(String loginInfo)
			throws WebException {
		RequestStreamFactory request = new RequestStreamFactory();
		request.readRequestHex(loginInfo);
		int userId = request.readInt();
		String userToken = request.readString(32);

		if (userId < 1) {
			throw new WebException(ExceptionKey.defaultKey, "登陆失效");
		}

		if (userToken == null || userToken.trim().length() == 0) {
			throw new WebException(ExceptionKey.defaultKey, "登陆失效");
		}

		LoginIdentity identity = (LoginIdentity) XMemcachedUtil
				.get(CommonDic.LOGIN_IDENTITY_KEY + userId);
		if (identity == null) {
			throw new WebException(ExceptionKey.defaultKey, "登陆失效");
		}
		if (!userToken.equals(identity.getUserToken())) {
			throw new WebException(ExceptionKey.defaultKey, "登陆失效");
		}

		return identity;
	}

}
