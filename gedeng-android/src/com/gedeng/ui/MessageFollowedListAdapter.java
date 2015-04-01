package com.gedeng.ui;


import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;
import com.gedeng.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.gedeng.client.adaptor.QXNetwork.Messages.QxMessage;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.GetImageByUrlParam;
import com.gedeng.client.connector.params.GetUserByIdParam;
import com.gedeng.client.entity.User;
import com.gedeng.ui.basic.Converters;
import com.gedeng.ui.basic.UIUserBasicInfo;

public class MessageFollowedListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<QxMessage> messages;

	public MessageFollowedListAdapter(Context context, ArrayList<FollowMessageListItem> followMessageListItems) {
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
			convertView = mInflater.inflate(R.layout.follow_message_list_item, null);
		}

		//ImageView portraitImageView = (ImageView) convertView.findViewById(R.id.imageView_portrait);
		//TextView usernameTextView = (TextView) convertView.findViewById(R.id.textView_userName);
		//usernameTextView.setText(followListItems.get(position).getUserName());

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
                    portraitImageView.setOnClickListener(new View.OnClickListener() {
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


        return convertView;
	}

}
