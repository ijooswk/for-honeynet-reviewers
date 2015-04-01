package com.gedeng.ui.basic;

import org.joda.time.DateTime;

import android.graphics.Bitmap;

public class UIComment {
	private int cid;
	private int authorUID;
	private Bitmap portrait;
	private String portraitUrl;
	private String userName;
	private DateTime date;
	private String commentContent;

	public UIComment(int cid, int authorUID, String portraitUrl,
			String userName, DateTime date, String commentContent) {
		super();
		this.cid = cid;
		this.authorUID = authorUID;
		this.portraitUrl = portraitUrl;
		this.userName = userName;
		this.setDate(date);
		this.commentContent = commentContent;
	}

	public UIComment(Bitmap portrait, String userName, DateTime date,
			String commentContent) {
		super();
		this.portrait = portrait;
		this.userName = userName;
		this.setDate(date);
		this.commentContent = commentContent;
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
	
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getPortraitUrl() {
		return portraitUrl;
	}

	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}

	public int getAuthorUID() {
		return authorUID;
	}

	public void setAuthorUID(int authorUID) {
		this.authorUID = authorUID;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}
}
