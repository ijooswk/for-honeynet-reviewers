package com.gedeng.ui;

import java.util.ArrayList;

import org.joda.time.DateTime;

import com.gedeng.ui.basic.UIComment;
import com.gedeng.ui.basic.UIPostBasicInfo;

import android.graphics.Bitmap;

/**
 * @author William
 * 
 * @date 2014-07-15
 *
 * ShowPostActivity 中  ListView 列表项对应的实体类
 *  
 */

public abstract class ShowPostListItem {
	public static final int LIST_ITEM_TYPE_BASIC_INFO 	= 0;
	public static final int LIST_ITEM_TYPE_POST_CONTENT = 1;
	public static final int LIST_ITEM_TYPE_COMMENT		= 2;
	private int itemType;
	private String textBuffer; // 用来缓存用户在评论或回复的时候的输入内容
	
	public int getItemType() {
		return itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	public String getTextBuffer() {
		return textBuffer;
	}
	public void setTextBuffer(String textBuffer) {
		this.textBuffer = textBuffer;
	}
}

class ShowPostBasicInfoItem extends ShowPostListItem {
	private int uid;
	private int pid;
	private Bitmap background;
	private Bitmap portrait;
	private String userName;
	private DateTime date;
	private boolean isLiked;
	private boolean isCommented;
	private int likeCount;
	private int commCount;
	private String source;
	
	public ShowPostBasicInfoItem(Bitmap background, Bitmap portrait,
			String userName, DateTime date, int likeCount, int commCount,
			String source) {
		setItemType(LIST_ITEM_TYPE_BASIC_INFO);
		this.background = background;
		this.portrait = portrait;
		this.userName = userName;
		this.setDate(date);
		this.setLikeCount(likeCount);
		this.setCommCount(commCount);
		this.source = source;
	}
	
	public ShowPostBasicInfoItem(UIPostBasicInfo postBasicInfo) {
		setUid(postBasicInfo.getUid());
		setPid(postBasicInfo.getPid());
		setItemType(LIST_ITEM_TYPE_BASIC_INFO);
		background = postBasicInfo.getBackground();
		portrait = postBasicInfo.getPortrait();
		userName = postBasicInfo.getUserName();
		setDate(postBasicInfo.getDate());
		setLiked(postBasicInfo.isLiked());
		setCommented(postBasicInfo.isCommented());
		setLikeCount(postBasicInfo.getLikeCount());
		setCommCount(postBasicInfo.getCommCount());
		source = postBasicInfo.getSource();
	}
	
	public Bitmap getBackground() {
		return background;
	}
	public void setBackground(Bitmap background) {
		this.background = background;
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
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
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

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}
}

class ShowPostContentItem extends ShowPostListItem {
	private ArrayList<String> postTags;
	private String postText;
	
	public ShowPostContentItem(ArrayList<String> postTags, String postText) {
		setItemType(LIST_ITEM_TYPE_POST_CONTENT);
		this.postTags = postTags;
		this.postText = postText;
	}
	
	public ArrayList<String> getPostTags() {
		return postTags;
	}
	public void setPostTags(ArrayList<String> postTags) {
		this.postTags = postTags;
	}
	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
	}
}

class ShowPostCommentItem extends ShowPostListItem {
	private int authorUID = -1;
	private int cid = -1;
	private Bitmap portrait = null;
	private String portraitUrl;
	private String userName;
	private DateTime commentDate;
	private String commentText;
	
	public ShowPostCommentItem(UIComment comment) {
		setItemType(LIST_ITEM_TYPE_COMMENT);
		setCid(comment.getCid());
		authorUID = comment.getAuthorUID();
		portraitUrl = comment.getPortraitUrl();
		userName = comment.getUserName();
		setCommentDate(comment.getDate());
		commentText = comment.getCommentContent();
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
	
	public String getComment() {
		return commentText;
	}
	public void setComment(String comment) {
		this.commentText = comment;
	}

	public int getAuthorUID() {
		return authorUID;
	}

	public void setAuthorUID(int authorUID) {
		this.authorUID = authorUID;
	}

	public String getPortraitUrl() {
		return portraitUrl;
	}

	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}

	public DateTime getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(DateTime commentDate) {
		this.commentDate = commentDate;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}
}
