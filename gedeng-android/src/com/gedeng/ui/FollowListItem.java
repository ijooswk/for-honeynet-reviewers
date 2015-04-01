package com.gedeng.ui;

import android.graphics.Bitmap;

public class FollowListItem {
	
	public static final int NOTFOLLOW 	= 0;
	public static final int FOLLOW 		= 1;
	
	private Bitmap portrait;
	private String userName;
	private int followState;
	
	public FollowListItem() {
		userName = "Alice";
	}
	
	public Bitmap getPortrait() {
		return portrait;
	}
	public void setPortrait(Bitmap portrait) {
		this.portrait = portrait;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getFollowState() {
		return followState;
	}

	public void setFollowState(int followState) {
		this.followState = followState;
	}

}
