package com.gedeng.ui.basic;

import java.io.Serializable;

public class UIUserBasicInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int GENDER_UNKNOWN = 0;
	public static final int GENDER_MALE = 1;
	public static final int GENDER_FEMALE = 2;
	
	
	private int uid = -1;
	private String portraitUrl;
	private String userName;
	private int gender;
	private String signature;
	private String currentSchool;
	private int postCount;
	
	public UIUserBasicInfo(String portraitUrl, String userName, String signature) {
		super();
		this.setPortraitUrl(portraitUrl);
		this.userName = userName;
		this.setSignature(signature);
	}
	
	public UIUserBasicInfo(String portraitUrl, String userName, int gender,
			String signature, int postCount) {
		super();
		this.portraitUrl = portraitUrl;
		this.userName = userName;
		this.gender = gender;
		this.signature = signature;
		this.postCount = postCount;
	}
	

	public UIUserBasicInfo(int uid, String portraitUrl, String userName,
			int gender, String signature, String currentSchool, int postCount) {
		super();
		this.uid = uid;
		this.portraitUrl = portraitUrl;
		this.userName = userName;
		this.gender = gender;
		this.signature = signature;
		this.currentSchool = currentSchool;
		this.postCount = postCount;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getCurrentSchool() {
		return currentSchool;
	}
	public void setCurrentSchool(String currentSchool) {
		this.currentSchool = currentSchool;
	}
	public int getPostCount() {
		return postCount;
	}
	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}

	public String getPortraitUrl() {
		return portraitUrl;
	}

	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	} 
}