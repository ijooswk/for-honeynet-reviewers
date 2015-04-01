package com.gedeng.client;

import org.joda.time.DateTime;
import org.json.JSONObject;

import com.gedeng.client.adaptor.QXStorage.StorageManager;
import com.gedeng.client.entity.User;
import com.gedeng.client.util.JsonDecoder;
import com.gedeng.client.util.JsonEncoder;

public class Storage {
	protected Storage() {
	}
	protected static Storage mStorage = null;
	public static synchronized Storage getInstance() {
		if (mStorage == null) {
			initialize();
		}
		return mStorage;
	};
	protected static synchronized Storage initialize() {
		mStorage = new Storage();
		mStorage.readCache();
		if (mStorage.loginStatus == GDClient.LOGINSTATUS_LOGIN) {
			mStorage.loginStatus = GDClient.LOGINSTATUS_LASTTIME_LOGIN;
		}
		mStorage.useCount ++;
		mStorage.loadFlag = 1;
		return mStorage;
	}
	
	
	protected int loadFlag;
	protected String username;
	protected String password;
	protected User currentUser; 
	protected int useCount;
	protected int loginStatus;
	protected int serverVersion;
	protected int serverDemandVersion;
	protected DateTime versionCheck;
	
	
	protected void saveCache() {
		if (username != null) {
			StorageManager.putString("username", username);
		}
		if (password != null) {
			StorageManager.putString("password", password);
		}
		if (currentUser !=  null) {
			StorageManager.putString("currentUser", JsonEncoder.parseUser(currentUser));
		}
		StorageManager.putInt("useCount", useCount);
		StorageManager.putInt("loginStatus", loginStatus);
		StorageManager.putInt("serverVersion", serverVersion);
		StorageManager.putInt("serverDemandVersion", serverDemandVersion);
		if (versionCheck != null) {
			StorageManager.putString("versionCheck", versionCheck.toString());
		}
	}
	protected void readCache() {
		
		username = StorageManager.getString("username");
		password = StorageManager.getString("password");
		try {
			currentUser = JsonDecoder.parseUser(new JSONObject(StorageManager.getString("currentUser")));
		} catch (Exception e) {
			currentUser = null;
		}
		loginStatus = StorageManager.getInt("loginStatus");
		if (loginStatus < 0) {
			loginStatus = 0;
		}
		useCount = StorageManager.getInt("useCount");
		if (useCount < 0) {
			useCount = 0;
		}
		serverVersion = StorageManager.getInt("serverVersion");
		if (serverVersion < 0) {
			serverVersion = 0;
		}
		serverDemandVersion = StorageManager.getInt("serverDemandVersion");
		if (serverDemandVersion < 0) {
			serverDemandVersion = 0;
		}
		String datetimeStr = StorageManager.getString("versionCheck");
		if (datetimeStr != null) {
			versionCheck = new DateTime(datetimeStr);
		}
	}
	
	public void saveData(String key,String value) {
		StorageManager.putString("Data-"+key,value);
	}
	public String loadData(String key) {
		return StorageManager.getString("Data-"+key);
	}
	
}
