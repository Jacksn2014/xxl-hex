package com.xxl.action.base;


@SuppressWarnings("serial")
public class LoginBaseAction extends BaseAction {
	private LoginIdentity loginIdentity;
	
	public LoginIdentity getLoginIdentity() {
		return loginIdentity;
	}

	public void setLoginIdentity(LoginIdentity loginIdentity) {
		this.loginIdentity = loginIdentity;
	}
	
}
