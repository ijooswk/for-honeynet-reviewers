/**
 * @author William
 * 
 * modified by Bella
 */
package com.gedeng.ui;

import java.util.ArrayList;

import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.GetImageByUrlParam;
import com.gedeng.client.connector.params.GetPostsByAuthorIdParam;
import com.gedeng.client.entity.Post;
import com.gedeng.ui.basic.Converters;
import com.gedeng.ui.basic.UIPostBasicInfo;
import com.gedeng.ui.basic.UIUserBasicInfo;
import com.gedeng.ui.utils.Utils;

import com.gedeng.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class ShowAllPostsActivity extends Activity {

	ArrayList<UIPostBasicInfo> postGridItems;
	PostGridAdapter postGridAdapter;
	GridView postGridView;
	View moreView;
	TextView loadMoreHint;

	private UIUserBasicInfo userBasicInfo;

	private int minPostId = -1;

	private boolean loadFlag = false;
	private boolean loadCapability = true;

	private LikeBroadcastReceiver likeBroadcastReceiver = null;
	private CommentBroadcastReceiver commentBroadcastReceiver = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_posts);
		// Show the Up button in the action bar.
		setupActionBar();

		userBasicInfo = (UIUserBasicInfo) getIntent().getSerializableExtra(Constants.EXTRA_NAME_USER_BASIC_INFO);

		final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView_myPosts);

		postGridItems = new ArrayList<UIPostBasicInfo>();
		postGridAdapter = new PostGridAdapter(this, postGridItems);
		moreView = findViewById(R.id.more_view);
		loadMoreHint = (TextView) findViewById(R.id.textView_loadMoreHint);
		moreView.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				loadMoreHint.setText(R.string.xlistview_header_hint_loading);
				loadMoreData();
			}
		});
		moreView.setVisibility(View.GONE);
		postGridView = (GridView) findViewById(R.id.gridView_myPosts);
		postGridView.setAdapter(postGridAdapter);
		scrollView.smoothScrollTo(0, 0); // 重置滚动条


		final ImageView portraitImageView = (ImageView) findViewById(R.id.imageView_portrait);
		TextView userNameTextView = (TextView) findViewById(R.id.textView_userName);
		if (userBasicInfo != null) {
			userNameTextView.setText(userBasicInfo.getUserName());

			final int uid = userBasicInfo.getUid();
			Bitmap portrait = PortraitsHolder.getInstance().getPortrait(uid);
			if (portrait != null) {
				portraitImageView.setImageBitmap(portrait);
			} else {
				String porttaitUrl = userBasicInfo.getPortraitUrl();
				if (!Utils.isValidUrl(porttaitUrl)) {
					portraitImageView.setImageResource(R.drawable.default_portrait);
				} else {
					GetImageByUrlParam getImageByUrlParam = new GetImageByUrlParam();
					getImageByUrlParam.setUrl(userBasicInfo.getPortraitUrl());
					Connector.getInstance().sendAsynRequest(getImageByUrlParam, new Callback() {
						public void onSuccess(Response response) {
							Bitmap bitmap = (Bitmap) response.getData();
							portraitImageView.setImageBitmap(bitmap);
							PortraitsHolder.getInstance().addEntry(uid, bitmap);
						}
						public void onFailed(int errorCode, String message) {
							portraitImageView.setImageResource(R.drawable.default_portrait);
						}
					});
				}
			}

			GetPostsByAuthorIdParam getPostsByAuthorIdParam = new GetPostsByAuthorIdParam();
			System.out.println(userBasicInfo.getUid());
			getPostsByAuthorIdParam.setId(userBasicInfo.getUid());
			getPostsByAuthorIdParam.setCount(10);
			Connector.getInstance().sendAsynRequest(getPostsByAuthorIdParam, new Callback() {
				@Override
				public void onSuccess(Response response) { 
					Post[] posts = (Post[]) response.getData();
					if (posts == null || posts.length == 0) return;
					for (Post post : posts) {
						System.out.println(post.getText());
						UIPostBasicInfo uiPostBasicInfo = Converters.covertILPostToUIPostBasicInfo(post);
						postGridItems.add(uiPostBasicInfo);
						System.out.println(post.getId());
						minPostId = post.getId();
					}
					postGridAdapter.notifyDataSetChanged();
					if(posts.length != 10){
						Utils.showShortToast(ShowAllPostsActivity.this, R.string.no_more_content_hint);
						moreView.setVisibility(View.GONE);
						loadCapability = false;
					}
				}

				@Override
				public void onFailed(int errorCode, String message) {
					System.out.println("FAILED");
				}
			});
		}

		scrollView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_MOVE:
					if (v.getScrollY() <= 0) {
					} else if (scrollView.getChildAt(0).getMeasuredHeight() - 100 <= v.getHeight() + v.getScrollY()) {
						if (loadFlag == false) {
							if(loadCapability)
								moreView.setVisibility(View.VISIBLE);
						}
					}
					break;
				default:
					break;
				}
				return false;
			}
		});

		likeBroadcastReceiver = new LikeBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constants.ACTION_LIKE);
		registerReceiver(likeBroadcastReceiver, intentFilter);

		commentBroadcastReceiver = new CommentBroadcastReceiver();
		intentFilter.addAction(Constants.ACTION_COMMENT);
		registerReceiver(commentBroadcastReceiver, intentFilter);
	}
	
	
	

	/*
	 * 上拉加载
	 */
	private void loadMoreData() {
		if (userBasicInfo != null) {
			loadFlag = true;
			moreView.setClickable(false);
			GetPostsByAuthorIdParam getPostsByAuthorIdParam = new GetPostsByAuthorIdParam();
			System.out.println(userBasicInfo.getUid());
			getPostsByAuthorIdParam.setId(userBasicInfo.getUid());
			getPostsByAuthorIdParam.setMaxId(minPostId);
			getPostsByAuthorIdParam.setCount(4);
			Connector.getInstance().sendAsynRequest(getPostsByAuthorIdParam, new Callback() {
				@Override
				public void onSuccess(Response response) { 
					Post[] posts = (Post[]) response.getData();
					if (posts == null) return;
					for (Post post : posts) {
						//System.out.println(post.getText());
						UIPostBasicInfo uiPostBasicInfo = Converters.covertILPostToUIPostBasicInfo(post);
						postGridItems.add(uiPostBasicInfo);
						//System.out.println(post.getId());
						minPostId = post.getId();
					}
					postGridAdapter.notifyDataSetChanged();
					loadFlag = false;
					if(posts.length != 4){
						Utils.showShortToast(ShowAllPostsActivity.this, R.string.no_more_content_hint);
						moreView.setVisibility(View.GONE);
						loadCapability = false;
					}
					moreView.setClickable(true);
					loadMoreHint.setText(R.string.xlistview_footer_hint_normal);
				}

				@Override
				public void onFailed(int errorCode, String message) {
					moreView.setVisibility(View.GONE);
					loadFlag = false;
					loadMoreHint.setText(R.string.xlistview_footer_hint_normal);
					loadCapability = false;
					moreView.setClickable(true);
				}
			});
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_posts, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			//NavUtils.navigateUpFromSameTask(this);
			finish();
			return true;
			//case R.id.action_messages:
			//	startActivity(new Intent(getApplicationContext(), MessagesActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onDestroy() {
		unregisterReceiver(likeBroadcastReceiver);
		unregisterReceiver(commentBroadcastReceiver);
		super.onDestroy();
	}



	private class LikeBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Constants.ACTION_LIKE)) {
				//System.out.println("LIKED");
				int id = intent.getIntExtra(Constants.EXTRA_NAME_LIKED_POST_ID, -1);
				//System.out.println("id : " + id);
				if (id != -1) {
					for (UIPostBasicInfo postBasicInfo : postGridItems) {
						if (postBasicInfo.getPid() == id) {
							boolean isLiked = intent.getBooleanExtra(Constants.EXTRA_NAME_POST_IS_LIKED, false);
							postBasicInfo.setLiked(isLiked);
							int likeCount = intent.getIntExtra(Constants.EXTRA_NAME_POST_LIKE_COUNT, -1);
							if (likeCount != -1) {
								postBasicInfo.setLikeCount(likeCount);
							}
							postGridAdapter.notifyDataSetChanged();
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
					for (UIPostBasicInfo postBasicInfo : postGridItems) {
						if (postBasicInfo.getPid() == id) {
							int commCount = intent.getIntExtra(Constants.EXTRA_NAME_POST_COMMENT_COUNT, -1);
							if (commCount != -1) {
								postBasicInfo.setCommCount(commCount);
							}
							postGridAdapter.notifyDataSetChanged();
						}
					}
				}
			}
		}
	}
}
