package com.xxl.action.base;

import java.io.Serializable;

/**
 * 登陆身份
 * @author 
 */
@SuppressWarnings("serial")
public class LoginIdentity implements Serializable {
	private int userId;
	private String userToken;
	private String userName;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
