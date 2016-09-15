package com.xxl.hex.demo.msg;

import com.xxl.hex.handler.response.HexResponse;

import java.io.Serializable;

public class DemoResponse extends HexResponse implements Serializable {
	private static final long serialVersionUID = 42L;

	private int sum;

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}
}
