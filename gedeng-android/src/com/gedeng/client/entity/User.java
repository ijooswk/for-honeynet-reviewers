package com.gedeng.client.entity;

import org.joda.time.DateTime;
import org.json.JSONObject;

public class User {
	public static final int GENDER_UNKNOWN = 0;
	public static final int GENDER_MALE = 1;
	public static final int GENDER_FEMALE = 2;
	
	public static final int FOLLOWSHIP_BLANK = 0;
	public static final int FOLLOWSHIP_FOLLOWING = 1;
	public static final int FOLLOWSHIP_FOLLOWED = 2;
	public static final int FOLLOWSHIP_BIFOLLOWING = 3;
	
	private int id;
	private String nickname;
	private String portraitUrl;
	private String description;
	private int gender;	//GENDER_UNKNOWN,GENDER_MALE,GENDER_FEMALE
	private int postCount;
	private int followingCount;
	private int followedCount;
	private DateTime lastLogin;
	private JSONObject source;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPortraitUrl() {
		return portraitUrl;
	}
	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getGender() {
		return this.gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getPostCount() {
		return postCount;
	}
	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}
	public int getFollowingCount() {
		return followingCount;
	}
	public void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}
	public int getFollowedCount() {
		return followedCount;
	}
	public void setFollowedCount(int followedCount) {
		this.followedCount = followedCount;
	}
	public DateTime getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(DateTime lastLogin) {
		this.lastLogin = lastLogin;
	}
	public JSONObject getSource() {
		return source;
	}
	public void setSource(JSONObject source) {
		this.source = source;
	}
}
