package com.gedeng.client.errors;

import java.util.Map;

public class Error {
	
	public static final int ERROR_NOT_LOGIN = 403;
	
	public static final int ERROR_VALIDATION_FALSE = 10001;	
	
	public static final int ERROR_LOGIN = 20102;
	public static final int ERROR_NOT_SELF = 20103;
	public static final int ERROR_PHONE_NUMBER_ALREADY_INUSE = 20104;
	public static final int ERROR_ALREADY_FRIEND = 20201;
	public static final int ERROR_CANNOT_FOLLOW_SELF = 20202;
	public static final int ERROR_FOLLOW_NON_EXISTANT_USER = 20203;
	
	public static final int ERROR_CONTENT_NOT_EXIST = 30000;
	
	public static final int ERROR_JSON = 70001;
	public static final int ERROR_HTTP = 80000;
	
	public static final int ERROR_PARAM_FALSE = 80002;
	public static final int ERROR_API_NOT_EXIST = 90000;

	public static final int ERROR_TELEPHONEDIR = 80100;
	
	protected final static String name = "未知错误";
	protected final static int code = 0;
	private final static Map<Integer,Class> codeMap = null;
	
	public Error() {
		
	};
	
	public static int getCode() {
		return code;
	}
	
}
