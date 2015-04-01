package com.gedeng.client.connector;

import com.gedeng.client.ClientError;
import com.gedeng.client.adaptor.QXNetwork.QxHttpUtil;

class ErrorHandler {
		
	public static boolean isFatal(WorkerReturn workerReturn) {
		return workerReturn.getReturn() != QxHttpUtil.RESPONSE_SUCCESS;
	}
	public static String translate(int code) {
		String result = null;
		switch(code) {
		case ClientError.ERROR_HTTP:
			result = "网络错误";
			break;
		case ClientError.ERROR_JSON:
			result = "内部编码错误";
			break;
		case ClientError.ERROR_NOT_LOGIN:
			result = "尚未登录";
			break;
		case ClientError.ERROR_PARAM_FALSE:
			result = "参数错误";
			break;
		case ClientError.ERROR_LOGIN:
			result = "用户名或密码错误";
			break;
		case ClientError.ERROR_PHONE_NUMBER_ALREADY_INUSE:
			result = "电话号码已经被使用";
			break;
		case ClientError.ERROR_CANNOT_FOLLOW_SELF:
			result = "不能关注自己";
			break;
		case ClientError.ERROR_FOLLOW_NON_EXISTANT_USER:
			result = "不存在该用户";
			break;
		case ClientError.ERROR_NOT_SELF:
			result = "不能对自身以外的用户执行此操作";
			break;
		case ClientError.ERROR_CONTENT_NOT_EXIST:
			result = "内容不存在或已删除";
			break;
		default:
			result = "未知错误";
			break;
		}
		return result;
	}
}
