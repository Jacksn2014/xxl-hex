package com.xxl.core.exception;

@SuppressWarnings("serial")
public class HexException extends RuntimeException {
	private String hex;
	
	public HexException(String hex) {
		this.hex = hex;
	}

	public String getHex() {
		return hex;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}
	
}
