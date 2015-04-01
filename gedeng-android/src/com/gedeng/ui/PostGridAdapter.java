package com.gedeng.ui;

import java.util.ArrayList;

import com.gedeng.R;
import com.gedeng.client.GDClient;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.DeletePostByIdParam;
import com.gedeng.client.connector.params.GetImageByUrlParam;
import com.gedeng.client.util.Time;
import com.gedeng.ui.basic.UIPostBasicInfo;
import com.gedeng.ui.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PostGridAdapter extends BaseAdapter {
	
	private static final int GRID_VIEW_NUM_COLUMNS = 2;

	private Context context;
	private ArrayList<UIPostBasicInfo> postListItems;

	public PostGridAdapter(Context context,
			ArrayList<UIPostBasicInfo> postListItems) {
		this.context = context;
		this.postListItems = postListItems;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null || convertView.getTag() == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.my_post_item, null);
			holder = new ViewHolder();
			holder.backgroundImageView = (ImageView) convertView
					.findViewById(R.id.imageView_background);
			holder.dateTextView = (TextView) convertView
					.findViewById(R.id.textView_date);
			holder.postTextView = (TextView) convertView
					.findViewById(R.id.textView_postText);
			holder.likeCountTextView = (TextView) convertView
					.findViewById(R.id.textView_likeCount);
			holder.commentCountTextView = (TextView) convertView
					.findViewById(R.id.textView_commentCount);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		View adjustView = convertView.findViewById(R.id.imageView_background);
		int width = Utils.getScreenWidth(context) / GRID_VIEW_NUM_COLUMNS;
		adjustView.setLayoutParams(
				new android.widget.RelativeLayout.LayoutParams(width, width));

		final UIPostBasicInfo postBasicInfo = postListItems.get(position);
		final int pos = position;
		convertView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, ShowPostActivity.class);
				UIPostBasicInfo uiPostBasicInfo = new UIPostBasicInfo(postBasicInfo);
				PostBitmapCache.getInstance().setBackground(postBasicInfo.getBackground());
				PostBitmapCache.getInstance().setPortrait(postBasicInfo.getPortrait());
				intent.putExtra(Constants.EXTRA_NAME_POST_BASIC_INFO,
						uiPostBasicInfo);
				context.startActivity(intent);
			}
		});
		
		convertView.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				//System.out.println("OnLongClick : " + pos);
				if (GDClient.getInstance().getCurrentUser().getId() != postBasicInfo.getUid()) {
					return false;
				}
				
				DeletePostByIdParam deletePostByIdParam = new DeletePostByIdParam();
				deletePostByIdParam.setId(postBasicInfo.getPid());
				Connector.getInstance().sendAsynRequest(deletePostByIdParam, new Callback() {
					public void onSuccess(Response response) {
						System.out.println("Delete Success");
						postListItems.remove(pos);
						notifyDataSetChanged();
					}
					public void onFailed(int errorCode, String message) {
					}
				});
				return true;
			}
		});

		holder.dateTextView.setText(Time.getTimeSpanString(postBasicInfo.getDate()));
		holder.postTextView.setText(postBasicInfo.getPostText());
		holder.likeCountTextView.setText(Utils
				.getMoreReadableCount(postBasicInfo.getLikeCount()));
		holder.commentCountTextView.setText(Utils
				.getMoreReadableCount(postBasicInfo.getCommCount()));

		if (holder.backgroundImageView != null) {
			GetImageByUrlParam getImageByUrlParam = new GetImageByUrlParam();
			getImageByUrlParam.setUrl(postBasicInfo.getBackgroundUrl());
			Connector.getInstance().sendAsynRequest(getImageByUrlParam,
					new Callback() {
						public void onSuccess(Response response) {
							Bitmap bitmap = (Bitmap) response.getData();
							if (bitmap != null) {
								holder.backgroundImageView
										.setImageBitmap(bitmap);
								// bitmap =
								// BitmapFactory.decodeResource(context.getResources(),
								// R.drawable.default_background);
							} else {
								holder.backgroundImageView
										.setImageResource(R.drawable.default_background);
							}
							postBasicInfo.setBackground(bitmap);
						}

						public void onFailed(int errorCode, String message) {
							System.out.println("load image 1: failed");
							holder.backgroundImageView
									.setImageResource(R.drawable.default_background);
							postBasicInfo.setBackground(null);
						}
					});
		}
		
		final ImageView likeImageView = (ImageView) convertView.findViewById(R.id.imageView_like);
		if (postBasicInfo.isLiked()) {
			likeImageView.setImageResource(R.drawable.ic_liked);
		} else {
			likeImageView.setImageResource(R.drawable.ic_like);
		}

		return convertView;

	}

	static class ViewHolder {
		ImageView backgroundImageView;
		TextView dateTextView;
		TextView postTextView;
		TextView likeCountTextView;
		TextView commentCountTextView;
	}
}
