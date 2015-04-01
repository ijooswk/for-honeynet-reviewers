package com.gedeng.client.errors;

public class NotLoginError extends Error {

	protected final static String name = "尚未登录";
	protected final static int code = ERROR_NOT_LOGIN;
	
	public NotLoginError() {
		super();
		
	}
	
}
