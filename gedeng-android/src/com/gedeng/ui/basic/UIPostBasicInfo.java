package com.gedeng.ui.basic;

import java.io.Serializable;
import java.util.ArrayList;

import org.joda.time.DateTime;

import android.graphics.Bitmap;

public class UIPostBasicInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int uid = -1;
	private int pid = -1;
	private String backgroundUrl;
	private SerializableBitmap background;
	private String portraitUrl;
	private SerializableBitmap portrait;
	private String userName;
	private DateTime date;
	private String postText;
	private ArrayList<String> postTags;
	private int likeCount;
	private int commCount;
	private String source;
	private boolean isLiked = false;
	private boolean isCommented = false;

	public UIPostBasicInfo(int uid, int pid, String backgroudUrl, String portraitUrl,
			String userName, DateTime date, String postText,
			ArrayList<String> postTags, int likeCount, int commCount,
			boolean isLiked, boolean isCommented) {
		super();
		this.uid = uid;
		this.pid = pid;
		this.backgroundUrl = backgroudUrl;
		this.portraitUrl = portraitUrl;
		this.userName = userName;
		this.setDate(date);
		this.postText = postText;
		this.postTags = postTags;
		this.likeCount = likeCount;
		this.commCount = commCount;
		this.isLiked = isLiked;
		this.isCommented = isCommented;
	}

	public UIPostBasicInfo(UIPostBasicInfo uiPostBasicInfo) {
		this.uid = uiPostBasicInfo.getUid();
		this.pid = uiPostBasicInfo.getPid();
		this.backgroundUrl = uiPostBasicInfo.getBackgroundUrl();
		this.portraitUrl = uiPostBasicInfo.getPortraitUrl();
		this.userName = uiPostBasicInfo.getUserName();
		this.setDate(uiPostBasicInfo.getDate());
		this.postText = uiPostBasicInfo.getPostText();
		this.postTags = uiPostBasicInfo.getPostTags();
		this.likeCount = uiPostBasicInfo.getLikeCount();
		this.commCount = uiPostBasicInfo.getCommCount();
		this.isLiked = uiPostBasicInfo.isLiked();
		this.isCommented = uiPostBasicInfo.isCommented();
	}
	
	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
	}

	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public ArrayList<String> getPostTags() {
		return postTags;
	}

	public void setPostTags(ArrayList<String> postTags) {
		this.postTags = postTags;
	}

	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	public void setBackgroundUrl(String backgroudUrl) {
		this.backgroundUrl = backgroudUrl;
	}

	public String getPortraitUrl() {
		return portraitUrl;
	}

	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}

	public boolean isCommented() {
		return isCommented;
	}

	public void setCommented(boolean isCommented) {
		this.isCommented = isCommented;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getCommCount() {
		return commCount;
	}

	public void setCommCount(int commCount) {
		this.commCount = commCount;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public Bitmap getBackground() {
		if (background != null) {
		return background.getBitmap();
		} else {
			return null;
		}
	}

	public void setBackground(Bitmap background) {
		if (this.background != null) {
			this.background.setBitmap(background);
		} else {
			this.background = new SerializableBitmap(background);
		}
	}

	public Bitmap getPortrait() {
		if (portrait != null) {
			return portrait.getBitmap();
		} else {
			return null;
		}
	}

	public void setPortrait(Bitmap portrait) {
		if (this.portrait != null) {
			this.portrait.setBitmap(portrait);
		} else {
			this.portrait = new SerializableBitmap(portrait);
		}
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}
}
