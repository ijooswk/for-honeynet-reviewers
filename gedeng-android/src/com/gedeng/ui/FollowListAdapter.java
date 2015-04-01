package com.gedeng.ui;


import java.util.ArrayList;

import com.gedeng.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class FollowListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<FollowListItem> followListItems;

	public FollowListAdapter(Context context, ArrayList<FollowListItem> followListItems) {
		this.context = context;
		this.followListItems = followListItems;
	}

	@Override
	public int getCount() {
		return followListItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return followListItems.get(position);
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
			convertView = mInflater.inflate(R.layout.follow_list_item, null);
		}

		ImageView portraitImageView = (ImageView) convertView.findViewById(R.id.imageView_portrait);
		//TextView usernameTextView = (TextView) convertView.findViewById(R.id.textView_userName);
		//usernameTextView.setText(followListItems.get(position).getUserName());
		
		portraitImageView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			}
		});

		return convertView;
	}

}
