package com.gedeng.ui;

import android.graphics.Bitmap;

/**
 * @author william
 * @date 2014-7-24
 * 
 * 消息中心评论列表列表项
 */

public class CommentMessageListItem {
	private Bitmap portrait;
	private String userName;
	private String date;
	private String comment;
	private String postText;
	private String description;
	
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
