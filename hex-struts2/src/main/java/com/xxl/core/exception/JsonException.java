package com.xxl.core.exception;

/**
 * 通过json形式返回异常信息
 * @author xuxueli
 *
 */
@SuppressWarnings("serial")
public class JsonException extends WebException {

	public JsonException(String exceptionKey, String exceptionMessage) {
		super(exceptionKey, exceptionMessage);
	}
	
}
