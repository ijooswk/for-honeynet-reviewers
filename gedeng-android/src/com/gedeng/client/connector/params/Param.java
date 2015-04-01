package com.gedeng.client.connector.params;

public abstract class Param {
	
	public final static int PARAM_UPDATE_DESCRIPTION = 1;
	public final static int PARAM_UPDATE_GENDER = 2;
	public final static int PARAM_UPDATE_NICKNAME = 3;
	public final static int PARAM_UPDATE_PASSWORD = 4;
	public final static int PARAM_UPDATE_PORTRAIT = 5;
	public final static int PARAM_UPDATE_TELEPHONE = 6;
	
	public final static int PARAM_CREATE_COMMENT = 8;
	public final static int PARAM_CREATE_FOLLOWSHIP = 9;
	public final static int PARAM_CREATE_POST = 10;
	
	public final static int PARAM_DELETE_COMMENT_BY_ID = 11;
	public final static int PARAM_DELETE_POST_BY_ID = 12;
	public final static int PARAM_DESTROY_FOLLOWSHIP = 13;
	
	public final static int PARAM_CHECK_FOLLOWSHIP = 7;
	public final static int PARAM_GET_COMMENTS_BY_POSTID = 14;
	public final static int PARAM_GET_FOLLOWINGS = 15;
	public final static int PARAM_GET_IMAGE_BY_URL = 16;
	public final static int PARAM_GET_POST_BY_ID = 17;
	public final static int PARAM_GET_POSTS_BY_AUTHORID = 18;
	public final static int PARAM_GET_POSTS = 19;
	public final static int PARAM_GET_USER_BY_ID = 20;
	public final static int PARAM_GET_USER_BY_TELEPHONE = 21;
	public final static int PARAM_GET_CONTACTS = 31;
	
	public final static int PARAM_FEEDBACK = 22;
	public final static int PARAM_LOGIN= 23;
	public final static int PARAM_LOGOUT = 24;
	public final static int PARAM_RESET_PASSWORD = 25;
	public final static int PARAM_SEND_CAPTCHA = 26;
	public final static int PARAM_VERIFY_CAPTCHA = 27;
	public final static int PARAM_SIGNUP = 28;
	
	public final static int PARAM_UP_VOTE_POST = 29;
	public final static int PARAM_DOWN_VOTE_POST = 30;
	
	public final static int PARAM_INTERNAL = 900;
	
	
	protected Object[] params;
	protected final int apiType;
	protected Param(int type) {
		apiType = type;
		params = new Object[6];
	};
	public int getApiType() {
		return apiType;
	}
}
