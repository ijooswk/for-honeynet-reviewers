package com.gedeng.ui;

import android.graphics.Bitmap;

public class PostBitmapCache {
	private Bitmap portrait = null;
	private Bitmap background = null;
	private static PostBitmapCache instance = new PostBitmapCache();
	
	private PostBitmapCache() {
	}
	
	public static PostBitmapCache getInstance() {
		return instance;
	}
	
	public Bitmap getPortrait() {
		return portrait;
	}
	public void setPortrait(Bitmap portrait) {
		this.portrait = portrait;
	}
	public Bitmap getBackground() {
		return background;
	}
	public void setBackground(Bitmap background) {
		this.background = background;
	}
	
}
