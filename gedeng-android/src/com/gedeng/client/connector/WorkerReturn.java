package com.gedeng.client.connector;

class WorkerReturn {
	private final int apiType;
	private int returnNumber;
	private Object data;
	public WorkerReturn(int type) {
		apiType = type;
	}
	public int getType() {
		return apiType;
	}
	public int getReturn() {
		return returnNumber;
	}
	public Object getData() {
		return data;
	}
	protected void setData(Object objd) {
		data = objd;
	}
	protected void setReturn(int ret_) {
		returnNumber = ret_;
	}
	
}
