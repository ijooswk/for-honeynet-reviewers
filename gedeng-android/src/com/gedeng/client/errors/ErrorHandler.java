package com.gedeng.client.errors;

import java.util.HashMap;
import java.util.Map;

public class ErrorHandler {
	
	public static final int STRATEGY_ABORT = 0;
	public static final int STRATEGY_RETRY = 1;
	public static final int STRATEGY_IGNORE = 2;
	
	private Map<Class,Integer> strategyMap = null;
	
	protected ErrorHandler() {
	}
	protected static ErrorHandler mErrorHandler = null;
	public static synchronized ErrorHandler getInstance() {
		if (mErrorHandler == null) {
			initialize();
		}
		return mErrorHandler;
	}
	protected static synchronized ErrorHandler initialize() {
		if (mErrorHandler == null) {
			mErrorHandler = new ErrorHandler();
		}
		mErrorHandler.buildDefaultMap();
		return mErrorHandler;
	}
	
	private void buildDefaultMap() {
		strategyMap = new HashMap<Class,Integer>();
		
		strategyMap.put(Error.class, STRATEGY_ABORT);
		strategyMap.put(NotLoginError.class, STRATEGY_ABORT);
	}
	
}
