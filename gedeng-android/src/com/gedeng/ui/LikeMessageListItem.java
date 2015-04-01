package com.gedeng.ui;

import android.graphics.Bitmap;

public class LikeMessageListItem {
	private int actorUID;
	private int postPID;
	private Bitmap portrait;
	private String portraitUrl;
	private String userName;
	private String date;
	private String postText;
	
	public LikeMessageListItem(int actorUID, int postPID, String portraitUrl,
			String userName, String date, String postText) {
		super();
		this.actorUID = actorUID;
		this.postPID = postPID;
		this.portraitUrl = portraitUrl;
		this.userName = userName;
		this.date = date;
		this.postText = postText;
	}

	public LikeMessageListItem(String portraitUrl, String userName,
			String date, String postText) {
		super();
		this.portraitUrl = portraitUrl;
		this.userName = userName;
		this.date = date;
		this.postText = postText;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
	}
	public String getPortraitUrl() {
		return portraitUrl;
	}
	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}

	public int getActorUID() {
		return actorUID;
	}

	public void setActorUID(int actorUID) {
		this.actorUID = actorUID;
	}

	public int getPostPID() {
		return postPID;
	}

	public void setPostPID(int postPID) {
		this.postPID = postPID;
	}
}
