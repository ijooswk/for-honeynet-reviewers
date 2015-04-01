package com.gedeng.ui.basic;

import java.util.ArrayList;

import com.gedeng.ui.basic.UIComment;

public class UIPost {
	private UIPostBasicInfo basicInfo;
	private ArrayList<UIComment> comments;
	
	public UIPost(UIPostBasicInfo basicInfo, ArrayList<UIComment> comments) {
		super();
		this.basicInfo = basicInfo;
		this.comments = comments;
	}
	
	public UIPostBasicInfo getBasicInfo() {
		return basicInfo;
	}
	public void setBasicInfo(UIPostBasicInfo basicInfo) {
		this.basicInfo = basicInfo;
	}
	public ArrayList<UIComment> getComments() {
		return comments;
	}
	public void setComments(ArrayList<UIComment> comments) {
		this.comments = comments;
	}
}
