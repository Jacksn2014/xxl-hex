package com.xxl.hex.msg.request;

import com.xxl.hex.annotation.FieldDef;
import com.xxl.hex.msg.IRequest;

/**
 * 打开背包道具
 * @author xuxueli
 */
public class DemoMsg extends IRequest {
	private int userId;
	@FieldDef(fieldLength=32)
	private String userToken;

	@Override
	public String toString() {
		return "DemoMsg [userId=" + userId + ", userToken=" + userToken + "]";
	}

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

}
