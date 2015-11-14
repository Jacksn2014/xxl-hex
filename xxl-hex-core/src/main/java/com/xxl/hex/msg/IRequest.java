package com.xxl.hex.msg;

import com.xxl.hex.annotation.FieldDef;

public abstract class IRequest extends IHexMsg {
	// 
	@FieldDef(fieldLength=40)
	private String clientIp;
}
