/**
 * @author William
 * 
 * modified by Bella
 * 
 */

package com.gedeng.ui;

import java.util.ArrayList;
import java.util.Calendar;

import org.joda.time.DateTime;

import com.gedeng.R;
import com.gedeng.client.ClientError;
import com.gedeng.client.GDClient;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.GetPostsParam;
import com.gedeng.client.connector.params.LoginParam;
import com.gedeng.client.connector.params.LogoutParam;
import com.gedeng.client.entity.Post;
import com.gedeng.client.entity.User;
import com.gedeng.client.util.Time;
import com.gedeng.ui.basic.Converters;
import com.gedeng.ui.basic.UIPostBasicInfo;
import com.gedeng.ui.basic.UIUserBasicInfo;
import com.gedeng.ui.customview.xlistview.XListView;
import com.gedeng.ui.customview.xlistview.XListView.IXListViewListener;
import com.gedeng.ui.utils.Utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	private XListView pullToRefreshList;	//下拉刷新和上拉刷新组件
	private ArrayList<UIPostBasicInfo> postListItems;
	private PostListAdapter postListAdapter;
	private User user;
	private int postMinId = -1;
	private int postMaxId = -1;
	private DateTime LastRefreshTime;

	private boolean firstRefresh = true;
	private boolean firstLoad = true;

	private long firstTime = 0; // used in onKeyUp, the first time to press the return button
	private Handler handler;

	private LikeBroadcastReceiver likeBroadcastReceiver = null;
	private CommentBroadcastReceiver commentBroadcastReceiver = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		handler = new Handler();

		DateTime temp = DateTime.now();
		LastRefreshTime = new DateTime(temp);
		pullToRefreshList = (XListView) findViewById(R.id.pullToRefreshListView);
		pullToRefreshList.setPullLoadEnable(false);
		postListItems = new ArrayList<UIPostBasicInfo>();
		postListAdapter = new PostListAdapter(MainActivity.this, postListItems);
		pullToRefreshList.setAdapter(postListAdapter);
		pullToRefreshList.setXListViewListener(new IXListViewListener() {
			public void onRefresh() {
				int loginStatus = GDClient.getInstance().loginStatus();
				/*
				 * 由于在断网的情况下允许用户进入主界面，所以需要在刷新的时候判断用户是否已经登录
				 * 如果已经登录则刷新
				 * 否则尝试自动登录
				 * 记录firstLoad是为了避免重复加载，因为在init没完成之前，如果用户刷新，就可能会造成重复加载
				 */
				if (loginStatus == GDClient.LOGINSTATUS_LOGIN) {
					if(!firstLoad) {
						handler.postDelayed(new Runnable() {
							public void run() {
								refresh();
							}
						}, 2000);
					}
				}else{
					handler.postDelayed(new Runnable() {
						public void run() {
							login();
						}
					}, 2000);
				}
			}

			@Override
			public void onLoadMore() {
				loadMoreData();
			}
		});

		user = GDClient.getInstance().getCurrentUser();
		if (user != null) {
			GetPostsParam getPostsParam = new GetPostsParam();
			getPostsParam.setCount(8);
			Connector.getInstance().sendAsynRequest(getPostsParam, new Callback() {
				@Override
				public void onSuccess(Response response) { 
					Post[] posts = (Post[]) response.getData();
					if (posts == null || posts.length == 0) return;
					postMaxId = posts[0].getId();
					for (Post post : posts) {
						//System.out.println(post.getText());
						UIPostBasicInfo uiPostBasicInfo = Converters.covertILPostToUIPostBasicInfo(post);
						postListItems.add(uiPostBasicInfo);
						postMinId = post.getId();
					}
					postListAdapter.notifyDataSetChanged();
					pullToRefreshList.setPullLoadEnable(true);
					firstLoad = false;
				}

				@Override
				public void onFailed(int errorCode, String message) {
					System.out.println("on Create, post loading FAILED");
					firstLoad = false;
				}
			});
		}

		likeBroadcastReceiver = new LikeBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constants.ACTION_LIKE);
		registerReceiver(likeBroadcastReceiver, intentFilter);

		commentBroadcastReceiver = new CommentBroadcastReceiver();
		intentFilter.addAction(Constants.ACTION_COMMENT);
		registerReceiver(commentBroadcastReceiver, intentFilter);
	}

	private void onStopLoading() {
		pullToRefreshList.stopRefresh();
		pullToRefreshList.stopLoadMore();
		pullToRefreshList.setRefreshTime(Time.getTimeSpanString(LastRefreshTime));
		DateTime temp = DateTime.now();
		LastRefreshTime = new DateTime(temp);
	}

	/**
	 * 下拉刷新
	 */
	private void refresh() {
		GetPostsParam getPostsParam = new GetPostsParam();
		System.out.println(user.getId());
		//getPostsByAuthorIdParam.setId(user.getId());
		getPostsParam.setCount(10);
		getPostsParam.setMinId(postMaxId);
		Connector.getInstance().sendAsynRequest(getPostsParam, new Callback() {
			@Override
			public void onSuccess(Response response) { 
				Post[] posts = (Post[]) response.getData();
				if (posts == null || posts.length == 0){
					onStopLoading();
					return;
				}
				//System.out.print(posts.length);
				postMaxId = posts[0].getId(); 
				for (int i = 0; i < posts.length; i++) {
					//System.out.println(posts[i].getText());
					UIPostBasicInfo uiPostBasicInfo = Converters.covertILPostToUIPostBasicInfo(posts[i]);
					postListItems.add(i, uiPostBasicInfo);
					//System.out.println(posts[i].getId());
					postListAdapter.notifyDataSetChanged();
				}

				//如果用户起初未登录，在联网的时候第一次刷新成功，需要把上拉刷新功能打开
				if(firstRefresh && posts.length == 10){
					firstRefresh = false;
					pullToRefreshList.setPullLoadEnable(true);
				}
				onStopLoading();
			}

			@Override
			public void onFailed(int errorCode, String message) {
				System.out.println("Load more : FAILED");
				onStopLoading();
			}
		});
	}


	/**
	 * 自动登录
	 */
	private void login(){
		String name = GDClient.getInstance().getUsername();
		String password = GDClient.getInstance().getPassword();
		LoginParam loginParam = new LoginParam();
		loginParam.setUsername(name);
		loginParam.setPassword(password);
		Connector.getInstance().sendAsynRequest(loginParam, new Callback() {
			public void onSuccess(Response response) {
				user = GDClient.getInstance().getCurrentUser();
				refresh();
				onStopLoading();
			}
			@Override
			/*
			 * 此处互动有问题
			 * 如果不是网络error，应该给出的提示语暂时未定
			 */
			public void onFailed(int errorCode, String message) {
				if(errorCode == ClientError.ERROR_HTTP){
					Utils.showShortToast(MainActivity.this, R.string.network_error_hint);
				}else{
					Utils.showShortToast(MainActivity.this, message);
				}
				onStopLoading();
			}

		});
	}

	/**
	 * 注销时用户登出
	 */
	private void logout(){
		LogoutParam logoutParam = new LogoutParam();
		Connector.getInstance().sendAsynRequest(logoutParam, new Callback() {
			public void onSuccess(Response response) {
				startActivity(new Intent(getApplicationContext(), LoginActivity.class));
				finish();
			}
			@Override
			public void onFailed(int errorCode, String message) {
				startActivity(new Intent(getApplicationContext(), LoginActivity.class));
				finish();
			}

		});
	}

	/**
	 * 上拉刷新
	 */
	private void loadMoreData() {
		System.out.println("Load More Data");
		if (user != null) {
			System.out.println(Calendar.getInstance().getTime().toString());
			GetPostsParam getPostsParam = new GetPostsParam();
			System.out.println(user.getId());
			//getPostsByAuthorIdParam.setId(user.getId());
			getPostsParam.setCount(4);
			getPostsParam.setMaxId(postMinId);
			Connector.getInstance().sendAsynRequest(getPostsParam, new Callback() {

				@Override
				public void onSuccess(Response response) { 
					System.out.println(Calendar.getInstance().getTime().toString());
					Post[] posts = (Post[]) response.getData();
					if (posts == null) return;
					for (Post post : posts) {
						System.out.println(post.getText());
						UIPostBasicInfo uiPostBasicInfo = Converters.covertILPostToUIPostBasicInfo(post);
						postListItems.add(uiPostBasicInfo);
						postMinId = post.getId();
					}
					/*
					 * 上拉刷新发现已经没有可以加载的内容了，给出用户提示，并且禁止继续上拉刷新
					 */
					if(posts.length != 4){
						Utils.showShortToast(MainActivity.this, R.string.no_more_content_hint);
						pullToRefreshList.setPullLoadEnable(false);
					}
					postListAdapter.notifyDataSetChanged();
					onStopLoading();
				}

				@Override
				public void onFailed(int errorCode, String message) {
					System.out.println("load more FAILED");
					//pullToRefreshList.setPullLoadEnable(false);
					onStopLoading();
				}
			});
		}
	}


	private class LikeBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Constants.ACTION_LIKE)) {
				System.out.println("LIKED");
				int id = intent.getIntExtra(Constants.EXTRA_NAME_LIKED_POST_ID, -1);
				System.out.println("id : " + id);
				if (id != -1) {
					for (UIPostBasicInfo postBasicInfo : postListItems) {
						if (postBasicInfo.getPid() == id) {
							boolean isLiked = intent.getBooleanExtra(Constants.EXTRA_NAME_POST_IS_LIKED, false);
							postBasicInfo.setLiked(isLiked);
							int likeCount = intent.getIntExtra(Constants.EXTRA_NAME_POST_LIKE_COUNT, -1);
							if (likeCount != -1) {
								postBasicInfo.setLikeCount(likeCount);
							}
							postListAdapter.notifyDataSetChanged();
						}
					}
				}
			}
		}
	}

	private class CommentBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Constants.ACTION_COMMENT)) {
				System.out.println("COMMENTED");
				int id = intent.getIntExtra(Constants.EXTRA_NAME_COMMENTED_POST_ID, -1);
				System.out.println("id : " + id);
				if (id != -1) {
					for (UIPostBasicInfo postBasicInfo : postListItems) {
						if (postBasicInfo.getPid() == id) {
							int commCount = intent.getIntExtra(Constants.EXTRA_NAME_POST_COMMENT_COUNT, -1);
							if (commCount != -1) {
								postBasicInfo.setCommCount(commCount);
							}
							postListAdapter.notifyDataSetChanged();
						}
					}
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_new_post: // 创建新的Post
			startActivity(new Intent(getApplicationContext(), ComposePostActivity.class));
			return true;
		case R.id.menu_my_account: // 我的账号
			Intent intent = new Intent(getApplicationContext(), MyAccountActivity.class);
			UIUserBasicInfo uiUserBasicInfo = Converters.convertILUserToUIUserBasicInfo(user);
			intent.putExtra(Constants.EXTRA_NAME_USER_BASIC_INFO, uiUserBasicInfo);
			startActivity(intent);
			return true;
		case R.id.menu_my_posts:  // 我的发布
			Intent intent2 = new Intent(getApplicationContext(), ShowAllPostsActivity.class);
			UIUserBasicInfo uiUserBasicInfo2 = Converters.convertILUserToUIUserBasicInfo(user);
			intent2.putExtra(Constants.EXTRA_NAME_USER_BASIC_INFO, uiUserBasicInfo2);
			startActivity(intent2);
			return true;
		case R.id.menu_my_follows: // 我的关注
			startActivity(new Intent(getApplicationContext(), MyFollowsActivity.class));
			return true;
		case R.id.menu_messages:  // 我的消息
			startActivity(new Intent(getApplicationContext(), MessagesActivity.class));
			return true;
		case R.id.menu_settings:  // 系统设置
			startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
			return true;
		case R.id.menu_log_out:  // 退出登录
			logout();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime > 1600) {
				Toast.makeText(MainActivity.this, R.string.press_twice_to_exit,
						Toast.LENGTH_SHORT).show();
				firstTime = secondTime;
				return true;
			} else {
				// System.exit(0);//or exit
				this.finish();
			}
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(likeBroadcastReceiver);
		unregisterReceiver(commentBroadcastReceiver);
		super.onDestroy();
	}	
}
