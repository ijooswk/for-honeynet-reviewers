package com.gedeng.ui.basic;

import java.util.ArrayList;

import com.gedeng.client.entity.Comment;
import com.gedeng.client.entity.Post;
import com.gedeng.client.entity.User;

public class Converters {
	public static UIPostBasicInfo covertILPostToUIPostBasicInfo(Post post) {
		if (post == null) {
			return null;
		}
		
		ArrayList<String> tags = new ArrayList<String>();
		String[] strTags = post.getTags();
		for (String tag : strTags) {
			tags.add(tag);
		}
		
		String[] imageUrls = post.getImagesUrl();
		String backgroudUrl = "";
		if (imageUrls == null || imageUrls.length == 0) {
			backgroudUrl = "";
		} else {
			backgroudUrl = post.getImagesUrl()[0];
		}
		
		UIPostBasicInfo uiPostBasicInfo = new UIPostBasicInfo(
				post.getAuthor().getId(),
				post.getId(), 
				backgroudUrl, 
				post.getAuthor().getPortraitUrl(), 
				post.getAuthor().getNickname(),
				post.getCreateTime(), 
				post.getText(), 
				tags, 
				post.getVoteCount(), 
				post.getCommentCount(), 
				post.isVoted(),
				post.isCommented());
		
		return uiPostBasicInfo;
	}
	
	public static UIComment convertILCommentToUiComment(Comment comment) {
		UIComment uiComment = new UIComment(
				comment.getId(), 
				comment.getAuthor().getId(),
				comment.getAuthor().getPortraitUrl(), 
				comment.getAuthor().getNickname(),
				comment.getCreateTime(), 
				comment.getText());
		return uiComment;
	}
	
	public static UIUserBasicInfo convertILUserToUIUserBasicInfo(User user) {
		if (user == null) {
			return null;
		}
		
		int gender = UIUserBasicInfo.GENDER_UNKNOWN;
		if (user.getGender() == User.GENDER_MALE) {
			gender = UIUserBasicInfo.GENDER_MALE;
		} else if (user.getGender() == User.GENDER_FEMALE ){
			gender = UIUserBasicInfo.GENDER_FEMALE;
		}
		
		return new UIUserBasicInfo(user.getId(), user.getPortraitUrl(), user.getNickname(), gender, 
				user.getDescription(), "", user.getPostCount());
	}
	
	
	public static int convertILGenderToUIGender(int gender) {
		if (gender == User.GENDER_MALE) {
			return UIUserBasicInfo.GENDER_MALE;
		} else if (gender == User.GENDER_FEMALE) {
			return UIUserBasicInfo.GENDER_FEMALE;
		}
		return UIUserBasicInfo.GENDER_UNKNOWN;
	}
}
