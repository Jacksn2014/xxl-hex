package com.xxl.action.hex.msg.request;

import com.xxl.action.hex.annotation.FieldDef;
import com.xxl.action.hex.msg.IRequest;

/**
 * 统一页面请求
 * @author xuxueli
 */
public class RedirectMsg extends IRequest {
	private int userId;
	@FieldDef(fieldLength=32)
	private String userToken;
	private int actionType;
	
	@Override
	public String toString() {
		return "RedirectMsg [userId=" + userId + ", userToken=" + userToken
				+ ", actionType=" + actionType + "]";
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
	public int getActionType() {
		return actionType;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	
}