package com.xxl.hex.codec.response;

import com.xxl.hex.annotation.FieldDef;
import com.xxl.hex.codec.IResponse;

public class DemoResponse extends IResponse {

	@FieldDef(fieldLength=64)
	private String words;

	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}
	
}
