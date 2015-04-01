/**
 * @author: Bella
 * @date:2014/7/16
 * A bitmap to be transfered between activities
 * 是用于切割图片时传递参数的工具类
 * 全局单例模式传递图片数据
 */
package com.gedeng.ui;

import android.graphics.Bitmap;

public class CropBitmapCache {

	/*
	 * inputBitmap是传入的图片
	 * outputBitmap是切割之后的图片结果
	 */
	private Bitmap inputBitmap, outputBitmap;
	private static CropBitmapCache bitmapCache = new CropBitmapCache();

	private CropBitmapCache() {
		inputBitmap = null;
		outputBitmap = null;
	}

	public void setInputBitmap(Bitmap _bitmap) {
		inputBitmap = _bitmap;
	}

	public Bitmap getInputBitmap() {
		return inputBitmap;
	}
	
	public void setOutputBitmap(Bitmap _bitmap) {
		outputBitmap = _bitmap;
	}

	public Bitmap getOutputBitmap() {
		return outputBitmap;
	}

	public static CropBitmapCache getBitmapCache() {
		return bitmapCache;
	}
}