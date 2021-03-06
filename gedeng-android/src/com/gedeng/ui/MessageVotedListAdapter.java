package com.gedeng.ui;


import java.util.ArrayList;

import com.gedeng.R;
import com.gedeng.client.adaptor.QXNetwork.Messages.QxMessage;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.GetImageByUrlParam;
import com.gedeng.client.connector.params.GetPostByIdParam;
import com.gedeng.client.connector.params.GetUserByIdParam;
import com.gedeng.client.entity.Post;
import com.gedeng.client.entity.User;
import com.gedeng.client.util.Time;
import com.gedeng.ui.basic.Converters;
import com.gedeng.ui.basic.UIPostBasicInfo;
import com.gedeng.ui.basic.UIUserBasicInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageVotedListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<QxMessage> messages;

	public MessageVotedListAdapter(Context context, ArrayList<QxMessage> messages) {
		this.context = context;
		this.messages = messages;
	}

	@Override
	public int getCount() {
		return messages.size();
	}

	@Override
	public Object getItem(int position) {		
		return messages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater)
					context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.like_message_list_item, null);
		}

		final QxMessage message = messages.get(position);
        final View finalConvertView = convertView;

        if (message.type == -1) {
            return convertView;
        }

        final ImageView portraitImageView = (ImageView) convertView.findViewById(R.id.imageView_portrait);
        portraitImageView.setImageResource(R.drawable.default_portrait);

        // render user-related view;
        if (message.actor_uid == -1) return convertView;
        GetUserByIdParam getUserByIdParam = new GetUserByIdParam();
        getUserByIdParam.setId(message.actor_uid);
        Connector.getInstance().sendAsynRequest(getUserByIdParam, new Callback() {
            @Override
            public void onSuccess(Response response) {
                User user = (User) response.getData();
                TextView usernameTextView = (TextView) finalConvertView.findViewById(R.id.textView_userName);

                //set user name
                usernameTextView.setText(user.getNickname());

                //set event listener to avatar image
                final UIUserBasicInfo uiUserBasicInfo = Converters.convertILUserToUIUserBasicInfo(user);
                if (uiUserBasicInfo != null) {
                    portraitImageView.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(context, UserBasicInfoActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constants.EXTRA_NAME_USER_BASIC_INFO, uiUserBasicInfo);
                            context.startActivity(intent);
                        }
                    });
                }

                // render avatar image
                GetImageByUrlParam getImageByUrlParam = new GetImageByUrlParam();
                getImageByUrlParam.setUrl(user.getPortraitUrl());
                Connector.getInstance().sendAsynRequest(getImageByUrlParam, new Callback() {
                    public void onSuccess(Response response) {
                        Bitmap bitmap = (Bitmap) response.getData();
                        portraitImageView.setImageBitmap(bitmap);
                    }
                });
            }
        });

		// render post-related views
        if (message.post_id == -1) return convertView;
        GetPostByIdParam getPostByIdParam = new GetPostByIdParam();
        getPostByIdParam.setId(message.post_id);
        Connector.getInstance().sendAsynRequest(getPostByIdParam, new Callback() {
            @Override
            public void onSuccess(Response response) {
                final Post post = (Post) response.getData();
                TextView postTextView = (TextView) finalConvertView.findViewById(R.id.textView_postText);

                //set user name
                postTextView.setText(post.getText());

                //set event listener to avatar image
                final UIPostBasicInfo uiPostBasicInfo = Converters.covertILPostToUIPostBasicInfo(post);
                if (uiPostBasicInfo != null) {
                    postTextView.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(context, ShowPostActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            PostBitmapCache.getInstance().setPortrait(uiPostBasicInfo.getPortrait());
                            PostBitmapCache.getInstance().setBackground(uiPostBasicInfo.getBackground());
                            intent.putExtra(Constants.EXTRA_NAME_USER_BASIC_INFO, uiPostBasicInfo);
                            context.startActivity(intent);
                        }
                    });
                }
            }
        });

        // render date string
		TextView dateTextView = (TextView) convertView.findViewById(R.id.textView_date);
        String date = Time.getTimeSpanString(message.time);
        dateTextView.setText(date);
		return convertView;
	}

}
