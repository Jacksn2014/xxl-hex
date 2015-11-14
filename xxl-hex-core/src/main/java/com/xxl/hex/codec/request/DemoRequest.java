package com.xxl.hex.codec.request;

import com.xxl.hex.annotation.FieldDef;
import com.xxl.hex.codec.IRequest;

public class DemoRequest extends IRequest  {
	
	@FieldDef(fieldLength=32)
	private String name;
	@FieldDef(fieldLength=32)
	private String word;
	
	public DemoRequest(){	}
	public DemoRequest(String name, String word){
		this.name = name;
		this.word = word;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
}
