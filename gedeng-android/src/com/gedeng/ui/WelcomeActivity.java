/**
 * @author Bella
 * 启动页面
 */

package com.gedeng.ui;

import cn.jpush.android.api.InstrumentedActivity;

import com.gedeng.R;
import com.gedeng.client.ClientError;
import com.gedeng.client.GDClient;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.GetImageByUrlParam;
import com.gedeng.client.connector.params.GetPostsParam;
import com.gedeng.client.connector.params.LoginParam;
import com.gedeng.client.entity.Post;
import com.gedeng.ui.basic.Converters;
import com.gedeng.ui.basic.UIPostBasicInfo;
import com.gedeng.ui.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class WelcomeActivity extends InstrumentedActivity {
	private Intent intent;

	@Override
	/**
	 * 注意，没有在该  Activity 中加载layout，而是为这个activity设计了特别的主题
	 * 直接将加载界面的背景设置为欢迎页，避免加载页的闪动
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// setContentView(R.layout.activity_welcome);
		
		GDClient.initialize(getApplicationContext());
		
		intent = new Intent();
		loadInAdvance();
		login();
	}

	private void login() {
		int loginStatus = GDClient.getInstance().loginStatus();
		Log.v("login", "" + loginStatus);
		switch (loginStatus) {
		case GDClient.LOGINSTATUS_LASTTIME_LOGIN:
			String name = GDClient.getInstance().getUsername();
			String password = GDClient.getInstance().getPassword();
			LoginParam loginParam = new LoginParam();
			loginParam.setUsername(name);
			loginParam.setPassword(password);
			Connector.getInstance().sendAsynRequest(loginParam, new Callback() {

				@Override
				public void onSuccess(Response response) {
					intent.setClass(WelcomeActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				}

				@Override
				/*
				 * 此处互动有问题 如果不是网络error，应该给出的提示语暂时未定
				 */
				public void onFailed(int errorCode, String message) {
					if (errorCode == ClientError.ERROR_HTTP) {
						Utils.showShortToast(WelcomeActivity.this,
								R.string.network_error_hint);
					} else {
						Utils.showShortToast(WelcomeActivity.this, message);
					}
					intent.setClass(WelcomeActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				}

			});
			break;
		case GDClient.LOGINSTATUS_LOGIN:
			intent.setClass(WelcomeActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			break;
		case GDClient.LOGINSTATUS_NOT_LOGIN:
			intent.setClass(WelcomeActivity.this, GuideActivity.class);
			startActivity(intent);
			finish();
			break;
		}
	}

	
	/*
	 * 尝试提前加载
	 * 加载完成之后尝试登陆
	 */
	private void loadInAdvance() {
		GetPostsParam getPostsParam = new GetPostsParam();
		getPostsParam.setCount(10);
		Connector.getInstance().sendAsynRequest(getPostsParam, new Callback() {
			public void onSuccess(Response response) {
				Post[] posts = (Post[]) response.getData();
				if (posts == null || posts.length == 0)
					return;
				for (int i = 0; i < posts.length; i++) {
					UIPostBasicInfo uiPostBasicInfo = Converters.covertILPostToUIPostBasicInfo(posts[i]);
					String backgroundUrl = uiPostBasicInfo.getBackgroundUrl();
					if (Utils.isValidUrl(backgroundUrl)) {
						GetImageByUrlParam getImageByUrlParam = new GetImageByUrlParam();
						getImageByUrlParam.setUrl(backgroundUrl);
						Connector.getInstance().sendAsynRequest(getImageByUrlParam, new Callback());
					}
				}
			}
			public void onFailed(int errorCode, String message) {
			}
		});
	}
}