package com.gedeng.client.connector;


interface CallbackInterface {
	public void onSuccess(Response response);
	public void onFailed(int errorCode,String message);
	public void onRetrying(int times);
	public void onComplete();
}
