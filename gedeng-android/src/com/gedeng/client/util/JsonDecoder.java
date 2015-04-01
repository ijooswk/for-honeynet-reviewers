 package com.gedeng.client.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gedeng.client.entity.*;

public class JsonDecoder {
	public static User parseUser(JSONObject json)  throws JSONException {
		User user = new User();
		user.setId(json.getInt("uid"));
		user.setNickname(json.getString("nickname"));
		user.setPortraitUrl(json.getString("avatar"));
		user.setDescription(json.getString("description"));
		user.setGender(json.getInt("gender"));
		user.setPostCount(json.getInt("post_count"));
		user.setFollowingCount(json.getInt("friend_count"));
		user.setFollowedCount(json.getInt("follower_count"));
		user.setLastLogin(Time.Iso2DateTime(json.getString("last_login")));
		user.setSource(json);
		return user;
	}
	
	public static Post parsePost(JSONObject json)  throws JSONException {
		Post post =  null;
		if (json.getBoolean("active")) {
			post = new Post();
			post.setId(json.getInt("id"));
			post.setActive(true);
			post.setText(json.getString("text"));
			JSONArray tagsArray = json.getJSONArray("tag");
			post.setTags(new String[tagsArray.length()]);
			for (int i = 0 ; i < tagsArray.length() ; i++) {
				post.getTags()[i] = tagsArray.getString(i);
			}
			post.setType(json.getInt("type"));
			post.setVoteCount(json.getInt("vote_count"));
			post.setCommentCount(json.getInt("comment_count"));
			JSONArray imageArray = json.getJSONArray("image");
			post.setImagesUrl(new String[imageArray.length()]);
			for (int i = 0 ; i < imageArray.length() ; i++) {
				post.getImagesUrl()[i] = imageArray.getJSONObject(i).getString("url");
			}
			post.setCommented(false);
			post.setVoted(false);
			post.setCreateTime(Time.Iso2DateTime(json.getString("created")));
			post.setSource(json);
		}
		return post;
	}
	
	public static Comment parseComment(JSONObject json) throws JSONException {
		Comment comment = null;
		if (json.getBoolean("active")) {
			comment = new Comment();
			comment.setId(json.getInt("id"));
			comment.setActive(true);
			comment.setPostId(json.getInt("post_id"));
			comment.setText(json.getString("text"));
			comment.setCreateTime(Time.Iso2DateTime(json.getString("created")));
			comment.setSource(json);
		}
		return comment;
	}
	
	public static String getArrayElements(JSONArray array,String key)  throws JSONException {
		StringBuilder buf = new StringBuilder();
		for (int i = 0 ; i < array.length() ; i++) {
			JSONObject obj = array.getJSONObject(i);
			if (obj.has("active") && !obj.getBoolean("active")) {
				continue;
			}
			if (buf.length() != 0) {
				buf.append(',');
			}
			buf.append(array.getJSONObject(i).getString(key));
		}
		return buf.toString();
	}
}
