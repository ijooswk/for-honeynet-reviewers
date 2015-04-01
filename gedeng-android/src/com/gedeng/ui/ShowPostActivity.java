/**
 * @author William 
 * 
 * modified by Bella
 */

package com.gedeng.ui;

import java.util.ArrayList;

import com.gedeng.R;
import com.gedeng.client.GDClient;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.CreateCommentParam;
import com.gedeng.client.connector.params.DeleteCommentByIdParam;
import com.gedeng.client.connector.params.DownVotePostParam;
import com.gedeng.client.connector.params.GetCommentsByPostIdParam;
import com.gedeng.client.connector.params.GetImageByUrlParam;
import com.gedeng.client.connector.params.GetUserByIdParam;
import com.gedeng.client.connector.params.UpVotePostParam;
import com.gedeng.client.entity.Comment;
import com.gedeng.client.entity.User;
import com.gedeng.client.util.Time;
import com.gedeng.ui.basic.Converters;
import com.gedeng.ui.basic.UIComment;
import com.gedeng.ui.basic.UIPostBasicInfo;
import com.gedeng.ui.basic.UIUserBasicInfo;
import com.gedeng.ui.utils.Utils;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShowPostActivity extends Activity {

	private UIPostBasicInfo postBasicInfo;
	private ShowPostBasicInfoItem basicInfoItem;

	private ListView listView;
	private ArrayList<ShowPostListItem> listItems;
	private MyCustomAdapter listAdapter;

	private View commentPanelView;
	private EditText commentEditText;
	private View sendCommentButton;
	private View loadMoreView;
	private TextView loadMoreHint;

	private int commentIndex = -1;
	private String commentPrefix = "";

	private int maxCommentId = -1;
	private int minCommentId = -1;
	
	private LikeBroadcastReceiver likeBroadcastReceiver = null;
	private CommentBroadcastReceiver commentBroadcastReceiver = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_post);
		// Show the Up button in the action bar.
		setupActionBar();

		postBasicInfo = (UIPostBasicInfo) getIntent().getSerializableExtra(
				Constants.EXTRA_NAME_POST_BASIC_INFO);

        // TODO: robustness -- if images are not in cache, get images by urls, do not crash (yuanchun commented)
		postBasicInfo.setBackground(PostBitmapCache.getInstance().getBackground());
		postBasicInfo.setPortrait(PostBitmapCache.getInstance().getPortrait());
		
		basicInfoItem = new ShowPostBasicInfoItem(postBasicInfo);
		
		listView = (ListView) findViewById(R.id.listView_showBlog);
		listItems = new ArrayList<ShowPostListItem>();
		listItems.add(basicInfoItem);
		listItems.add(new ShowPostContentItem(postBasicInfo.getPostTags(),
				postBasicInfo.getPostText()));
		listAdapter = new MyCustomAdapter(this, listItems);

		/*
		 * 加入下拉到底点击刷新功能
		 */
		loadMoreView = getLayoutInflater().inflate(R.layout.more_view, null);
		loadMoreHint = (TextView) loadMoreView
				.findViewById(R.id.textView_loadMoreHint);
		loadMoreView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				loadMoreData();
			}
		});
		listView.addFooterView(loadMoreView);
		loadMoreView.setVisibility(View.GONE);
		listView.setAdapter(listAdapter);

		/**
		 * 如果  post的背景为空, 从服务器加载
		 */
		if (postBasicInfo.getBackground() == null) {
			//System.out.println("INVALID BACKGROUND for ShowPostActivity");
			GetImageByUrlParam getImageByUrlParam = new GetImageByUrlParam();
			getImageByUrlParam.setUrl(postBasicInfo.getBackgroundUrl());
			Connector.getInstance().sendAsynRequest(getImageByUrlParam,
					new Callback() {
				public void onSuccess(Response response) {
					Bitmap bitmap = (Bitmap) response.getData();
					postBasicInfo.setBackground(bitmap);
					listItems.set(0, new ShowPostBasicInfoItem(
							postBasicInfo));
					listAdapter.notifyDataSetChanged();
				}

				public void onFailed(int errorCode, String message) {
					// System.out.println("load image : failed");
				}
			});
		} else {
			//System.out.println("VALID BACKGROUND for ShowPostActivity");
		} 


		/**
		 * 查看缓存中是否有对应的头像， 若没有则从服务器加载
		 */
		int authorId = postBasicInfo.getUid();
		Bitmap portrait = PortraitsHolder.getInstance().getPortrait(authorId);
		if (portrait != null) {
			postBasicInfo.setPortrait(portrait);
			listItems.set(0, new ShowPostBasicInfoItem(postBasicInfo));
			listAdapter.notifyDataSetChanged();
		} else {
			String portraitUrl = postBasicInfo.getPortraitUrl();
			if (!Utils.isValidUrl(portraitUrl)) {
				postBasicInfo.setPortrait(
						BitmapFactory.decodeResource(getResources(), R.drawable.default_portrait));
				listItems.set(0, new ShowPostBasicInfoItem(postBasicInfo));
				listAdapter.notifyDataSetChanged();
			} else {
				GetImageByUrlParam getImageByUrlParam = new GetImageByUrlParam();
				getImageByUrlParam.setUrl(postBasicInfo.getPortraitUrl());
				Connector.getInstance().sendAsynRequest(getImageByUrlParam, new Callback(){
					public void onSuccess(Response response) {
						Bitmap bitmap = (Bitmap)response.getData();
						postBasicInfo.setPortrait(bitmap);
						listItems.set(0, new ShowPostBasicInfoItem(postBasicInfo));
						listAdapter.notifyDataSetChanged();
					}
					public void onFailed(int errorCode, String message) {
						//System.out.println("load image : failed");
					}
				});
			}
		}

		/**
		 * 加载评论列表
		 */
		GetCommentsByPostIdParam getCommentsByPostIdParam = new GetCommentsByPostIdParam();
		getCommentsByPostIdParam.setCount(10);
		getCommentsByPostIdParam.setId(postBasicInfo.getPid());
		Connector.getInstance().sendAsynRequest(getCommentsByPostIdParam,
				new Callback() {
			@Override
			public void onSuccess(Response response) {
				Comment[] comments = (Comment[]) response.getData();
				if (comments != null && comments.length != 0) {
					maxCommentId = comments[0].getId();
					minCommentId = comments[comments.length - 1]
							.getId();
				}
				for (Comment comment : comments) {
					System.out.println(comment.getText());
					UIComment uiComment = Converters
							.convertILCommentToUiComment(comment);
					listItems.add(new ShowPostCommentItem(uiComment));
				}
				if (comments.length != 10) {
					listView.removeFooterView(loadMoreView);
				} else {
					loadMoreView.setVisibility(View.VISIBLE);
				}
				listAdapter.notifyDataSetChanged();
			}

			@Override
			public void onFailed(int errorCode, String message) {
				listView.removeFooterView(loadMoreView);
				listAdapter.notifyDataSetChanged();
			}

		});

		/**
		 * 列表项点击事件监听
		 */
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				if (position > 0) {
					if (commentIndex != -1 && commentIndex != position) {
						listItems.get(commentIndex).setTextBuffer(
								commentEditText.getText().toString());
					}
					commentIndex = position;
					commentEditText.setText(listItems.get(commentIndex)
							.getTextBuffer());
					commentEditText.setSelection(commentEditText.getText()
							.toString().length());
					showSoftKeyboard(commentEditText);
					if (position > 1) {
						commentPrefix = getResources()
								.getString(R.string.reply)
								+ " " + ((ShowPostCommentItem) listItems
										.get(position)).getUserName();
						commentEditText.setHint(commentPrefix);
					} else {
						commentPrefix = "";
						commentEditText.setHint(R.string.comment);
					}
				}
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				if (position < 2) return false;
				System.out.println("Long Click Comment : " + position);
				ShowPostCommentItem showPostCommentItem = (ShowPostCommentItem)listItems.get(position);
				if (GDClient.getInstance().getCurrentUser().getId() != showPostCommentItem.getAuthorUID()) {
					return false;
				}

				final int pos = position;
				DeleteCommentByIdParam deleteCommentByIdParam = new DeleteCommentByIdParam();
				deleteCommentByIdParam.setId(showPostCommentItem.getCid());
				Connector.getInstance().sendAsynRequest(deleteCommentByIdParam, new Callback() {
					public void onSuccess(Response response) {
						System.out.println("delete comment success");
						listItems.remove(pos);
						listAdapter.notifyDataSetChanged();
					}
					public void onFailed(int errorCode, String message) {
					}
				});
				return true;
			}
		});

		/**
		 * 评论框和发送按钮初始化，设置监听器
		 */
		commentPanelView = findViewById(R.id.linearLayout_commentPanel);
		commentEditText = (EditText) commentPanelView
				.findViewById(R.id.editText_comment);
		commentEditText.setHint(R.string.comment);
		sendCommentButton = commentPanelView
				.findViewById(R.id.button_sendComment);
		sendCommentButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String commentText = commentEditText.getEditableText()
						.toString();

				if (commentText.equals("")) {
					Utils.showLongToast(getApplicationContext(),
							R.string.message_invalid_comment);
					return;
				}

				sendComment(commentPrefix, commentText);
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
	
	

	@Override
	protected void onDestroy() {
		unregisterReceiver(likeBroadcastReceiver);
		unregisterReceiver(commentBroadcastReceiver);
		super.onDestroy();
	}



	/**
	 * 加载更多评论
	 */
	private void loadMoreData() {
		loadMoreHint.setText(R.string.xlistview_header_hint_loading);
		loadMoreView.setClickable(false);
		GetCommentsByPostIdParam getCommentsByPostIdParam = new GetCommentsByPostIdParam();
		getCommentsByPostIdParam.setCount(10);
		getCommentsByPostIdParam.setId(postBasicInfo.getPid());
		getCommentsByPostIdParam.setMaxId(minCommentId);
		Connector.getInstance().sendAsynRequest(getCommentsByPostIdParam,
				new Callback() {
			@Override
			public void onSuccess(Response response) {
				postBasicInfo.setCommented(true);

				ShowPostBasicInfoItem showPostBasicInfoItem = (ShowPostBasicInfoItem) listItems.get(0);
				showPostBasicInfoItem.setCommented(true);
				listItems.set(0, showPostBasicInfoItem);
				listAdapter.notifyDataSetChanged();
				commentEditText.setText("");
				if (commentIndex != -1) {
					listItems.get(commentIndex).setTextBuffer("");
				}
				hideSoftKeyboard(commentEditText);
				commentEditText.setHint(R.string.comment);

				Comment[] comments = (Comment[]) response.getData();
				if (comments != null && comments.length != 0) {
					minCommentId = comments[comments.length - 1]
							.getId();
				}
				for (Comment comment : comments) {
					System.out.println(comment.getText());
					UIComment uiComment = Converters
							.convertILCommentToUiComment(comment);
					listItems.add(new ShowPostCommentItem(uiComment));
				}
				if (comments.length != 10) {
					listView.removeFooterView(loadMoreView);
					Utils.showShortToast(ShowPostActivity.this, R.string.no_more_content_hint);
				}
				loadMoreHint.setText(R.string.xlistview_header_hint_normal);
				loadMoreView.setClickable(true);
				listAdapter.notifyDataSetChanged();
			}

			@Override
			public void onFailed(int errorCode, String message) {
				Utils.showShortToast(ShowPostActivity.this, R.string.no_more_content_hint);
				loadMoreHint.setText(R.string.xlistview_header_hint_normal);
				listView.removeFooterView(loadMoreView);
				loadMoreView.setClickable(true);
				listAdapter.notifyDataSetChanged();
			}

		});
	}

	/**
	 * 发评论或回复
	 * 
	 * @param commentPrefix
	 * @param commentText
	 */
	private void sendComment(String commentPrefix, String commentText) {
		// System.out.println(commentPrefix + " " + commentText);
		sendCommentButton.setClickable(false);
		CreateCommentParam createCommentParam = new CreateCommentParam();
		createCommentParam.setPostId(postBasicInfo.getPid());
		createCommentParam.setTargetUserId(postBasicInfo.getUid());
		String text = "";
		if (commentPrefix.equals("")) {
			text += commentText;
		} else {
			text += commentPrefix + ": " + commentText;
		}
		createCommentParam.setText(text);
		Connector.getInstance().sendAsynRequest(createCommentParam, new Callback() {
			@Override
			public void onSuccess(Response response) {
				System.out.println("Success");

				Utils.showShortToast(getApplicationContext(), R.string.message_send_comment_success);

				commentEditText.setText("");
				commentEditText.setHint(R.string.comment);
				hideSoftKeyboard(commentEditText);

				sendCommentButton.setClickable(true);

				GetCommentsByPostIdParam getCommentsByPostIdParam = new GetCommentsByPostIdParam();
				getCommentsByPostIdParam.setCount(20);
				getCommentsByPostIdParam.setId(postBasicInfo.getPid());
				getCommentsByPostIdParam.setMinId(maxCommentId);
				Connector.getInstance().sendAsynRequest(getCommentsByPostIdParam, new Callback() {
					@Override
					public void onSuccess(Response response) {
						Comment[] comments = (Comment[]) response.getData();
						if (comments != null && comments.length != 0) {
							maxCommentId = comments[0].getId();
						} else {
							return;
						}

						for (int i = 0; i < comments.length; i++) {
							UIComment uiComment = Converters.convertILCommentToUiComment(comments[i]);
							listItems.add(i + 2, new ShowPostCommentItem(uiComment));
						}

						int commentCount = postBasicInfo.getCommCount() + comments.length;
						ShowPostBasicInfoItem showPostBasicInfoItem = (ShowPostBasicInfoItem) listItems.get(0);
						showPostBasicInfoItem.setCommCount(commentCount);
						listItems.set(0, showPostBasicInfoItem);
						postBasicInfo.setCommCount(commentCount);
						listAdapter.notifyDataSetChanged();	

						Intent intent = new Intent(Constants.ACTION_COMMENT);
						intent.putExtra(Constants.EXTRA_NAME_COMMENTED_POST_ID, postBasicInfo.getPid());
						intent.putExtra(Constants.EXTRA_NAME_POST_COMMENT_COUNT, postBasicInfo.getCommCount());
						sendBroadcast(intent);
					}

					@Override
					public void onFailed(int errorCode, String message) {
					}
				});
			}

			@Override
			public void onFailed(int errorCode, String message) {
				System.out.println("Failed to load Comments : " + errorCode);
				Utils.showShortToast(getApplicationContext(), R.string.message_send_comment_failed);
				sendCommentButton.setClickable(true);
			}
		});

	}

	private void showSoftKeyboard(View view) {
		if (view.requestFocus()) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
		}
	}

	/**
	 * Force Android to hide the virtual keyboard using the InputMethodManager,
	 * passing in the window containing the edit field
	 */
	private void hideSoftKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
		getMenuInflater().inflate(R.menu.show_post, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			//passDataToParentActivity();
			hideSoftKeyboard(commentEditText);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class MyCustomAdapter extends BaseAdapter {

		private Context context;
		private ArrayList<ShowPostListItem> listItems;

		private Bitmap defaultPortrait = null;

		public MyCustomAdapter(Context context,
				ArrayList<ShowPostListItem> listItems) {
			this.context = context;
			this.listItems = listItems;

			defaultPortrait = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.default_portrait);
		}

		@Override
		public int getCount() {
			return listItems.size();
		}

		@Override
		public int getItemViewType(int position) {
			return listItems.get(position).getItemType();
		}

		@Override
		public Object getItem(int position) {
			return listItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("CutPasteId")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			int itemType = getItemViewType(position);
			// if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			switch (itemType) {
			case ShowPostListItem.LIST_ITEM_TYPE_BASIC_INFO:
				ShowPostBasicInfoItem basicInfoItem = (ShowPostBasicInfoItem) listItems.get(position);
				convertView = createBasicInfoView(mInflater, basicInfoItem);
				break;
			case ShowPostListItem.LIST_ITEM_TYPE_POST_CONTENT:
				ShowPostContentItem postContentItem = (ShowPostContentItem) listItems.get(position);
				convertView = createPostContentView(mInflater, postContentItem);

				break;
			case ShowPostListItem.LIST_ITEM_TYPE_COMMENT:
				ShowPostCommentItem showPostCommentItem = (ShowPostCommentItem) listItems.get(position);
				convertView = createPostCommentView(mInflater,
						showPostCommentItem, position);
			}
			// }
			return convertView;
		}

		private View createBasicInfoView(LayoutInflater inflater,
				final ShowPostBasicInfoItem basicInfoItem) {
			View convertView = inflater.inflate(R.layout.post_basic_info_item,
					null);
			ImageView portraitImageView = (ImageView) convertView
					.findViewById(R.id.imageView_portrait);
			ImageView backgroundImageView = (ImageView) convertView
					.findViewById(R.id.imageView_background);
			TextView usernameTextView = (TextView) convertView
					.findViewById(R.id.textView_userName);
			TextView dateTextView = (TextView) convertView
					.findViewById(R.id.textView_date);
			TextView postSourceTextView = (TextView) convertView
					.findViewById(R.id.textView_postSourceDiscription);
			final TextView likeCountTextView = (TextView) convertView
					.findViewById(R.id.textView_likeCount);
			TextView commentCounTextView = (TextView) convertView
					.findViewById(R.id.textView_commentCount);

			ImageView adjustView = backgroundImageView;
			adjustView.setLayoutParams(
					new android.widget.RelativeLayout.LayoutParams(
							Utils.getScreenWidth(context), 
							Utils.getScreenWidth(context)));

			if (basicInfoItem.getBackground() != null) {
				backgroundImageView.setImageBitmap(basicInfoItem
						.getBackground());
			} else {
				backgroundImageView
				.setImageResource(R.drawable.default_background);
			}

			if (basicInfoItem.getPortrait() != null) {
				portraitImageView.setImageBitmap(basicInfoItem.getPortrait());
			} else {
				portraitImageView.setImageResource(R.drawable.default_portrait);
			}

			usernameTextView.setText(basicInfoItem.getUserName());
			dateTextView.setText(Time.getTimeSpanString(basicInfoItem.getDate()));
			postSourceTextView.setText(basicInfoItem.getSource());
			likeCountTextView.setText(Utils.getMoreReadableCount(basicInfoItem.getLikeCount()));
			commentCounTextView.setText(Utils.getMoreReadableCount(basicInfoItem.getCommCount()));

			portraitImageView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					GetUserByIdParam getUserByIdParam = new GetUserByIdParam();
					getUserByIdParam.setId(basicInfoItem.getUid());
					Connector.getInstance().sendAsynRequest(getUserByIdParam,
							new Callback() {
						public void onSuccess(Response response) {
							User user = (User) response.getData();
							if (user != null) {
								UIUserBasicInfo userBasicInfo = Converters
										.convertILUserToUIUserBasicInfo(user);
								Intent intent = new Intent(
										getApplicationContext(),
										UserBasicInfoActivity.class);
								intent.putExtra(
										Constants.EXTRA_NAME_USER_BASIC_INFO,
										userBasicInfo);
								startActivity(intent);
							}
						}

						public void onFailed(int errorCode,
								String message) {
						}
					});

				}
			});

			final ImageView likeImageView = (ImageView) convertView.findViewById(R.id.imageView_like);
			if (basicInfoItem.isLiked()) {
				likeImageView.setImageResource(R.drawable.ic_liked);
			} else {
				likeImageView.setImageResource(R.drawable.ic_like);
			}
			likeImageView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (basicInfoItem.isLiked()) {
						likeImageView.setImageResource(R.drawable.ic_like);
						basicInfoItem.setLiked(false);
						int likeCount = basicInfoItem.getLikeCount();
						likeCount -= 1;
						basicInfoItem.setLikeCount(likeCount);
						likeCountTextView.setText(Utils.getMoreReadableCount(likeCount));

						DownVotePostParam downVotePostParam = new DownVotePostParam();
						downVotePostParam.setId(basicInfoItem.getPid());
						Connector.getInstance().sendAsynRequest(downVotePostParam, new Callback() {
						});
					} else {
						likeImageView.setImageResource(R.drawable.ic_liked);
						basicInfoItem.setLiked(true);
						int likeCount = basicInfoItem.getLikeCount();
						likeCount += 1;
						basicInfoItem.setLikeCount(likeCount);
						likeCountTextView.setText(Utils.getMoreReadableCount(basicInfoItem.getLikeCount()));
						UpVotePostParam upVotePostParam = new UpVotePostParam();
						upVotePostParam.setId(basicInfoItem.getPid());
						Connector.getInstance().sendAsynRequest(upVotePostParam, new Callback() {
						});
					}
					Intent intent = new Intent(Constants.ACTION_LIKE);
					intent.putExtra(Constants.EXTRA_NAME_LIKED_POST_ID, basicInfoItem.getPid());
					intent.putExtra(Constants.EXTRA_NAME_POST_IS_LIKED, basicInfoItem.isLiked());
					intent.putExtra(Constants.EXTRA_NAME_POST_LIKE_COUNT, basicInfoItem.getLikeCount());
					context.sendBroadcast(intent);
				}
			});

			View commentImageView = convertView
					.findViewById(R.id.imageView_comment);
			if (basicInfoItem.isCommented()) {
				commentImageView.setBackgroundResource(R.drawable.ic_commented);
			} else {
				commentImageView.setBackgroundResource(R.drawable.ic_comment);
			}

			return convertView;
		}

		private View createPostContentView(LayoutInflater inflater,
				ShowPostContentItem postContentItem) {
			View convertView = inflater.inflate(R.layout.post_content_item,
					null);
			TextView postTagsTextView = (TextView) convertView
					.findViewById(R.id.textView_postTags);
			TextView postTextView = (TextView) convertView
					.findViewById(R.id.textView_postText);

			postTagsTextView.setText(Utils.labelTags(postContentItem
					.getPostTags()));
			postTextView.setText(postContentItem.getPostText());

			return convertView;
		}

		private View createPostCommentView(LayoutInflater inflater,
				final ShowPostCommentItem showPostCommentItem, final int position) {
			View convertView = inflater.inflate(R.layout.post_comment_item,
					null);

			final int uid = showPostCommentItem.getAuthorUID();
			final ImageView portraitImageView = (ImageView) convertView
					.findViewById(R.id.imageView_portrait);
			portraitImageView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					GetUserByIdParam getUserByIdParam = new GetUserByIdParam();
					getUserByIdParam.setId(uid);
					Connector.getInstance().sendAsynRequest(getUserByIdParam,
							new Callback() {
						@Override
						public void onSuccess(Response response) {
							User user = (User) response.getData();
							if (user != null) {
								UIUserBasicInfo uiUserBasicInfo = Converters
										.convertILUserToUIUserBasicInfo(user);
								Intent intent = new Intent(
										getApplicationContext(),
										UserBasicInfoActivity.class);
								intent.putExtra(
										Constants.EXTRA_NAME_USER_BASIC_INFO,
										uiUserBasicInfo);
								startActivity(intent);
							}
						}

						@Override
						public void onFailed(int errorCode,
								String message) {
						}
					});
				}
			});

			if (showPostCommentItem.getPortrait() == null) {
				final int authorId = showPostCommentItem.getAuthorUID();
				Bitmap portrait = PortraitsHolder.getInstance().getPortrait(authorId);
				if (portrait != null) {
					portraitImageView.setImageBitmap(portrait);
				} else if (!Utils.isValidUrl(showPostCommentItem.getPortraitUrl())) {
					showPostCommentItem.setPortrait(defaultPortrait);
					portraitImageView.setImageBitmap(defaultPortrait);
				} else {
					GetImageByUrlParam getImageByUrlParam = new GetImageByUrlParam();
					getImageByUrlParam.setUrl(showPostCommentItem
							.getPortraitUrl());
					Connector.getInstance().sendAsynRequest(getImageByUrlParam,
							new Callback() {

						@Override
						public void onSuccess(Response response) {
							Bitmap bitmap = (Bitmap) response.getData();
							showPostCommentItem.setPortrait(bitmap);
							portraitImageView.setImageBitmap(bitmap);
							PortraitsHolder.getInstance().addEntry(authorId, bitmap);
						}

						@Override
						public void onFailed(int errorCode,
								String message) {
							portraitImageView
							.setImageBitmap(defaultPortrait);
						}
					});
				}
			} else {
				portraitImageView.setImageBitmap(showPostCommentItem
						.getPortrait());
			}

			TextView userNameTextView = (TextView) convertView
					.findViewById(R.id.textView_userName);
			TextView commentDateTextView = (TextView) convertView
					.findViewById(R.id.textView_commentDate);
			TextView commentTextView = (TextView) convertView
					.findViewById(R.id.textView_comment);
			userNameTextView.setText(showPostCommentItem.getUserName());
			commentDateTextView.setText(
					Time.getTimeSpanString(showPostCommentItem.getCommentDate()));
			commentTextView.setText(showPostCommentItem.getComment());
			return convertView;
		}
	}

	private class LikeBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Constants.ACTION_LIKE)) {
				int id = intent.getIntExtra(Constants.EXTRA_NAME_LIKED_POST_ID, -1);
				System.out.println("id : " + id);
				if (id != -1) {
					if (basicInfoItem.getPid() == id) {
						System.out.println("p, id : " + id);
						boolean isLiked = intent.getBooleanExtra(Constants.EXTRA_NAME_POST_IS_LIKED, false);
						ShowPostBasicInfoItem postBasicInfoItem = (ShowPostBasicInfoItem) listItems.get(0);
						postBasicInfoItem.setLiked(isLiked);
						int likeCount = intent.getIntExtra(Constants.EXTRA_NAME_POST_LIKE_COUNT, -1);
						if (likeCount != -1) {
							postBasicInfoItem.setLikeCount(likeCount);
						}
						listItems.set(0, postBasicInfoItem);
						listAdapter.notifyDataSetChanged();
					}
				}
			}
		}
	}

	private class CommentBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Constants.ACTION_COMMENT)) { // FIXME 
				int id = intent.getIntExtra(Constants.EXTRA_NAME_COMMENTED_POST_ID, -1);
				//System.out.println("id : " + id);
				if (id != -1) {
					if (basicInfoItem.getPid() == id) {
						int commCount = intent.getIntExtra(Constants.EXTRA_NAME_POST_COMMENT_COUNT, -1);
						if (commCount != -1) {
							basicInfoItem.setCommCount(commCount);
						}
						listAdapter.notifyDataSetChanged();
					}
				}
			}
		}
	}
}
