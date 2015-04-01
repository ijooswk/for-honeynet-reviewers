package com.gedeng.client.connector.params;

import android.graphics.Bitmap;

public class CreatePostParam extends Param {

	public CreatePostParam() {
		super(PARAM_CREATE_POST);
		setType(1);
		setImages(null);
	}
	
	public void setText(String text) {
		params[0] = text;
	}
	public void setType(int type) {
		params[1] = type;
	}
	public void setTag(String tag) {
		params[2] = tag;
	}
	public void setImages(Bitmap[] images) {
		params[3] = images;
	}
	
	public String getText() {
		return (String)(params[0]);
	}
	public int getType() {
		return (Integer)(params[1]);
	}
	public String getTag() {
		return (String)(params[2]);
	}
	public Bitmap[] getImages() {
		return (Bitmap[])(params[3]);
	}

}
