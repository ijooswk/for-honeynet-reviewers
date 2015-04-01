/**
 * @author: William
 * 
 * Modified by Bella
 */
package com.gedeng.ui;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.gedeng.R;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.DownVotePostParam;
import com.gedeng.client.connector.params.GetImageByUrlParam;
import com.gedeng.client.connector.params.GetUserByIdParam;
import com.gedeng.client.connector.params.UpVotePostParam;
import com.gedeng.client.entity.User;
import com.gedeng.client.util.Time;
import com.gedeng.ui.basic.Converters;
import com.gedeng.ui.basic.UIPostBasicInfo;
import com.gedeng.ui.utils.Utils;

public class PostListAdapter extends BaseAdapter {

	private Activity context;
	private ArrayList<UIPostBasicInfo> postListItems;

	private Bitmap defaultBackground = null;
	private Bitmap defaultPortrait = null;

	public PostListAdapter(Activity context, ArrayList<UIPostBasicInfo> postListItems) {
		this.context = context;
		this.postListItems = postListItems;

		Bitmap temp = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_background);
		
		/*
		 * 进行图片压缩以修正内存溢出错误
		 */
		defaultBackground = ThumbnailUtils.extractThumbnail(temp, Constants.UPLOAD_IMAGE_SIZE, Constants.UPLOAD_IMAGE_SIZE);
		defaultPortrait = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_portrait);
	}

	@Override
	public int getCount() {
		return postListItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return postListItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("CutPasteId")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater)
					context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.post_list_item, null);
			holder = new ViewHolder();
			holder.backgroundImageView = (ImageView) convertView.findViewById(R.id.imageView_background);
			holder.portraitImageView = (ImageView) convertView.findViewById(R.id.imageView_portrait);
			holder.usernameTextView = (TextView) convertView.findViewById(R.id.textView_userName);
			holder.dateTextView = (TextView) convertView.findViewById(R.id.textView_date);
			holder.postTextView = (TextView) convertView.findViewById(R.id.textView_postText);
			holder.postSourceTextView = (TextView) convertView.findViewById(R.id.textView_postSourceDiscription);
			holder.likeCountTextView = (TextView) convertView.findViewById(R.id.textView_likeCount);
			holder.commentCountTextView = (TextView) convertView.findViewById(R.id.textView_commentCount);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		// Adjust the View to adapt different screen size
		ImageView adjustView = (ImageView) convertView.findViewById(R.id.imageView_background);
		adjustView.setLayoutParams(
				new android.widget.RelativeLayout.LayoutParams(Utils.getScreenWidth(context), Utils.getScreenWidth(context)));

		final UIPostBasicInfo postListItem = postListItems.get(position);
		final int pos = position;

		convertView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, ShowPostActivity.class);
				UIPostBasicInfo uiPostBasicInfo = new UIPostBasicInfo(postListItem);
				PostBitmapCache.getInstance().setPortrait(postListItem.getPortrait());
				PostBitmapCache.getInstance().setBackground(postListItem.getBackground());
				//System.out.println("PostListAdapter: post_id: " + postListItem.getPid());
				intent.putExtra(Constants.EXTRA_NAME_POST_BASIC_INFO, uiPostBasicInfo);
				context.startActivityForResult(intent, Constants.REQUEST_CODE_POST_BASIC_INFO);
			}
		});

		final View portraitView = holder.portraitImageView;
		portraitView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				portraitView.setClickable(false);
				
				int uid = postListItem.getUid();
				GetUserByIdParam getUserByIdParam = new GetUserByIdParam();
				getUserByIdParam.setId(uid);
				Connector.getInstance().sendAsynRequest(getUserByIdParam, new Callback() {

					@Override
					public void onSuccess(Response response) {
						User user = (User) response.getData();
						Intent intent = new Intent(context, UserBasicInfoActivity.class);
						intent.putExtra(Constants.EXTRA_NAME_USER_BASIC_INFO, Converters.convertILUserToUIUserBasicInfo(user));
						context.startActivity(intent);
						
						portraitView.setClickable(true);
					}

					@Override
					public void onFailed(int errorCode, String message) {
						portraitView.setClickable(true);
					}					
				});

			}
		});

		final ImageView likeImageView = (ImageView) convertView.findViewById(R.id.imageView_like);
		if (postListItem.isLiked()) {
			likeImageView.setImageResource(R.drawable.ic_liked);
		} else {
			likeImageView.setImageResource(R.drawable.ic_like);
		}
		
		likeImageView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (postListItem.isLiked()) {
					likeImageView.setImageResource(R.drawable.ic_like);
					postListItem.setLiked(false);
					int likeCount = postListItem.getLikeCount();
					likeCount -= 1;
					postListItem.setLikeCount(likeCount);
					holder.likeCountTextView.setText(Utils.getMoreReadableCount(likeCount));
					
					DownVotePostParam downVotePostParam = new DownVotePostParam();
					downVotePostParam.setId(postListItem.getPid());
					Connector.getInstance().sendAsynRequest(downVotePostParam, new Callback() {
					});
				} else {
					likeImageView.setImageResource(R.drawable.ic_liked);
					postListItem.setLiked(true);
					int likeCount = postListItem.getLikeCount();
					likeCount += 1;
					postListItem.setLikeCount(likeCount);
					holder.likeCountTextView.setText(Utils.getMoreReadableCount(likeCount));
					System.out.println("liked " + pos);

					UpVotePostParam upVotePostParam = new UpVotePostParam();
					upVotePostParam.setId(postListItem.getPid());
					Connector.getInstance().sendAsynRequest(upVotePostParam, new Callback() {		
					});
				}
			}
		});

		final View commentImageView = convertView.findViewById(R.id.imageView_comment);
		if (postListItem.isCommented()) {
			commentImageView.setBackgroundResource(R.drawable.ic_commented);
		} else {
			commentImageView.setBackgroundResource(R.drawable.ic_comment);
		}
		
		commentImageView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, ShowPostActivity.class);
				UIPostBasicInfo uiPostBasicInfo = new UIPostBasicInfo(postListItem);
				PostBitmapCache.getInstance().setPortrait(postListItem.getPortrait());
				PostBitmapCache.getInstance().setBackground(postListItem.getBackground());
				intent.putExtra(Constants.EXTRA_NAME_POST_BASIC_INFO, uiPostBasicInfo);
				context.startActivityForResult(intent, Constants.REQUEST_CODE_POST_BASIC_INFO);
			}
		});

		View countView = convertView.findViewById(R.id.layout_likeCount_and_commentCount);
		countView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			}
		});

		if (postListItem.getUserName() != null) {
			holder.usernameTextView.setText(postListItem.getUserName());
		} 
		holder.dateTextView.setText(Time.getTimeSpanString(postListItem.getDate()));
		holder.postTextView.setText(postListItem.getPostText());
		holder.postSourceTextView.setText("");
		holder.likeCountTextView.setText(Utils.getMoreReadableCount(postListItem.getLikeCount()));
		holder.commentCountTextView.setText(Utils.getMoreReadableCount(postListItem.getCommCount()));

		if (holder.backgroundImageView != null && postListItem.getBackground() == null) {
			String backgroundUrl = postListItem.getBackgroundUrl(); 
			holder.backgroundImageView.setImageBitmap(defaultBackground);
			if (!Utils.isValidUrl(backgroundUrl)) {
				postListItem.setBackground(defaultBackground);
			} else {
				GetImageByUrlParam getImageByUrlParam = new GetImageByUrlParam();
				getImageByUrlParam.setUrl(postListItem.getBackgroundUrl());
				//Log.v("backgroundUrl", position + " : " + postListItem.getBackgroundUrl());
				Connector.getInstance().sendAsynRequest(getImageByUrlParam, new Callback(){
					public void onSuccess(Response response) {
						System.out.println(pos + " : load image 1: success");
						Bitmap bitmap = (Bitmap)response.getData();
						//Bitmap bitmap = ThumbnailUtils.extractThumbnail(temp, Constants.UPLOAD_IMAGE_SIZE, Constants.UPLOAD_IMAGE_SIZE);
						if (bitmap != null) {
							holder.backgroundImageView.setImageBitmap(bitmap);
						}
						postListItem.setBackground(bitmap);
					}
					public void onFailed(int errorCode, String message) {
						System.out.println(pos + " : load image 1: failed, url : " + postListItem.getBackgroundUrl());
						postListItem.setBackground(defaultBackground);
					}
				});
			}
		} else if(holder.backgroundImageView != null && postListItem.getBackground() != null){
			holder.backgroundImageView.setImageBitmap(postListItem.getBackground());
		}

		if (holder.portraitImageView != null && postListItem.getPortrait() == null) {
			String potraitUrl = postListItem.getPortraitUrl();
			holder.portraitImageView.setImageBitmap(defaultPortrait);
			
			Bitmap portrait = PortraitsHolder.getInstance().getPortrait(postListItem.getUid());
			if (portrait != null) {
				holder.portraitImageView.setImageBitmap(portrait);
				postListItem.setPortrait(portrait);
			} else if (!Utils.isValidUrl(potraitUrl)) {
				postListItem.setPortrait(defaultPortrait);
				PortraitsHolder.getInstance().addEntry(postListItem.getUid(), defaultPortrait);
			} else {
				GetImageByUrlParam getImageByUrlParam = new GetImageByUrlParam();
				getImageByUrlParam.setUrl(postListItem.getPortraitUrl());
				Connector.getInstance().sendAsynRequest(getImageByUrlParam, new Callback() {
					public void onSuccess(Response response) {
						System.out.println(pos + " : load image 2: success");
						Bitmap bitmap = (Bitmap)response.getData();
						if (bitmap != null) {
							holder.portraitImageView.setImageBitmap(bitmap);
						}
						postListItem.setPortrait(bitmap);
						PortraitsHolder.getInstance().addEntry(postListItem.getUid(), bitmap);
					}
					public void onFailed(int errorCode, String message) {
						System.out.println(pos + " : load image 2: failed, url : " + postListItem.getPortraitUrl());
						postListItem.setBackground(defaultPortrait);
						PortraitsHolder.getInstance().addEntry(postListItem.getUid(), defaultPortrait);
					}
				});
			}
		} else if (holder.portraitImageView != null && postListItem.getPortrait() != null){
			holder.portraitImageView.setImageBitmap(postListItem.getPortrait());
		}

		return convertView;
	}

	static	class ViewHolder {
		ImageView backgroundImageView;
		ImageView portraitImageView;
		TextView usernameTextView;
		TextView dateTextView;
		TextView postTextView;
		TextView postSourceTextView;
		TextView likeCountTextView;
		TextView commentCountTextView;
	}
}
