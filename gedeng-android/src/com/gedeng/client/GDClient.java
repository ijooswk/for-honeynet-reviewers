package com.gedeng.client;

import android.content.Context;

import cn.jpush.android.api.JPushInterface;
import com.gedeng.client.adaptor.QXNetwork.QxHttpClient;
import com.gedeng.client.adaptor.QXStorage.StorageManager;
import com.gedeng.client.entity.User;

public class GDClient {
	public static final int SUCCESS_FLAG = 0;
	public static final int version = 1;
	
	public static final int LOGINSTATUS_LOGIN = 1;
	public static final int LOGINSTATUS_NOT_LOGIN = 0;
	public static final int LOGINSTATUS_LASTTIME_LOGIN = 2;
	
	
	protected GDClient() {
	}
	protected static GDClient mGDClient = null;
	public static synchronized GDClient getInstance() {
		if (mGDClient == null) {
			initialize(null);
		}
		return mGDClient;
	}
	public static synchronized GDClient initialize(Context context) {
		if (mGDClient == null) {
			mGDClient = new GDClient();
		}
		mGDClient.mContext = context;
		QxHttpClient.init(context);
        JPushInterface.init(context);
        JPushInterface.setDebugMode(false);
        StorageManager.init(context);
        Storage.initialize();
		return mGDClient;
	}
	
	private Context mContext;
	
	
	public boolean isLoaded() {
		return Storage.getInstance().loadFlag > 0;
	}
	public User getCurrentUser() {
		return Storage.getInstance().currentUser;
	}
	public boolean isFirstUse() {
		return (Storage.getInstance().useCount > 0);
	}
	public int loginStatus() {
		return Storage.getInstance().loginStatus;
	}
	public boolean isNeedUpdate() {
		return (Storage.getInstance().serverVersion > version);
	}
	public boolean isDemandUpdate() {
		return (Storage.getInstance().serverDemandVersion > version);
	}
	public Context getContext() {
		return mContext;
	}
	public String getUsername() {
		return Storage.getInstance().username;
	}
	public void setUsername(String username) {
		Storage.getInstance().username = username;
		saveCache();
	}
	public String getPassword() {
		return Storage.getInstance().password;
	}
	public void setPassword(String password) {
		Storage.getInstance().password = password;
		saveCache();
	}
	public void setUser(User user) {
		Storage.getInstance().currentUser = user;
		saveCache();
	}
	public void login(User user,String username,String password) {
		Storage.getInstance().currentUser = user;
		Storage.getInstance().username = username;
		Storage.getInstance().password = password;
		Storage.getInstance().loginStatus = 1;
		saveCache();
	}
	public void logout() {
		Storage.getInstance().currentUser = null;
		Storage.getInstance().loginStatus = 0;
		saveCache();
	}
	
	
	public void saveCache() {
		Storage.getInstance().saveCache();
	}
	public void loadCache() {
		Storage.getInstance().readCache();
	}
	
}
