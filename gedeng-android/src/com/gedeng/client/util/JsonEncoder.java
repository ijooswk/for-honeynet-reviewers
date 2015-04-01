package com.gedeng.client.util;

import org.json.JSONException;
import org.json.JSONObject;

import com.gedeng.client.entity.*;

public class JsonEncoder {
	public static String parseUser(User user) {
		JSONObject json = new JSONObject();
		try {
			json.put("uid",user.getId());
			json.put("nickname", user.getNickname());
			json.put("avatar", user.getPortraitUrl());
			json.put("description", user.getDescription());
			json.put("post_count", user.getPostCount());
			json.put("friend_count", user.getFollowingCount());
			json.put("follower_count", user.getFollowedCount());
			json.put("last_login",user.getLastLogin().toString());
			json.put("gender",user.getGender());
		}
		catch (JSONException e) {
			return "";
		}
		return json.toString();
	}
}
