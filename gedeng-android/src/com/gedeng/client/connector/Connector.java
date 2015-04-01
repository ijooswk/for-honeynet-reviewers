package com.gedeng.client.connector;

import com.gedeng.client.connector.params.Param;

public class Connector {

	private final int version;
	private int retrySpan;
	private int retryTimes;
	
	protected Connector() {
		version = 1;
		retrySpan = 5000;
		retryTimes = 3;
	};
	protected static Connector mConnector = null;
	public static synchronized Connector getInstance() {
		if (mConnector == null) {
			initialize();
		}
		return mConnector;
	};
	public static synchronized Connector initialize() {
		mConnector = new Connector();
		return mConnector;
	}
	
	
	public int getVersion() {
		return version;
	}
	
	public void setRetrySpan(int span) {
		if (span >= 0) {
			retrySpan = span;
		}
	}
	
	public void setRetryTimes(int times) {
		if (times >= 0) {
			retryTimes = times;
		}
	}
	
	public void sendAsynRequest(final Param param,final Callback callback) {
		AsynRequest task = new AsynRequest(param,callback,retryTimes,retrySpan);
		task.execute();
	}
	
	
}
