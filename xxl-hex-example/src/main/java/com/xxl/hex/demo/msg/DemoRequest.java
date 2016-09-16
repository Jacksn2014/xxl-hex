package com.xxl.hex.demo.msg;

/**
 * DemoRequest规定: 普通Java类即可
 */
public class DemoRequest {

	private String passphrase;

	private int a;
	private int b;

	public String getPassphrase() {
		return passphrase;
	}

	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

}
