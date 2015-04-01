package com.gedeng.ui;

import android.graphics.Bitmap;
import com.gedeng.client.entity.User;

public class FollowMessageListItem {
	private Bitmap portrait;
	private String date;
    private User fansUser;

    public FollowMessageListItem(User user) {
        fansUser = user;
    };
	
	public Bitmap getPortrait() {
		return portrait;
	}
	public void setPortrait(Bitmap portrait) {
		this.portrait = portrait;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
