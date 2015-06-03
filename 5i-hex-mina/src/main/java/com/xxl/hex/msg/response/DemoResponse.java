package com.xxl.hex.msg.response;

import com.xxl.hex.annotation.FieldDef;
import com.xxl.hex.msg.IResponse;

/**
 * @author xuxueli
 */
public class DemoResponse extends IResponse {
	private int intValue;
	@FieldDef(fieldLength=64)
	private String strValue;
	
	public DemoResponse(int intValue, String strValue) {
		this.intValue = intValue;
		this.strValue = strValue;
	}
	
	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}
	
}
