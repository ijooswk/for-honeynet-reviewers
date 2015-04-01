/**
 * @author william
 * 
 * Utils 类封装了一些比较常用的工具函数（或方法）
 * 
 */

package com.gedeng.ui.utils;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.WindowManager;
import android.widget.Toast;

public class Utils {
	/**
	 * 调整图片尺寸
	 * @param src
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static Bitmap imageResize(Bitmap src, int newWidth, int newHeight) {
		if (src == null) return null; 
		int width = src.getWidth();
		int height = src.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
	}

	/** 
	 *  Use for decoding camera response data. 
	 */ 
	public static Bitmap getBitmapFromCameraData(Intent data, Context context) { 
		Uri selectedImage = data.getData(); 
		String[] filePathColumn = { MediaStore.Images.Media.DATA }; 
		Cursor cursor = context.getContentResolver().query(selectedImage,filePathColumn, null, null, null); 
		cursor.moveToFirst(); 
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]); 
		String picturePath = cursor.getString(columnIndex); cursor.close(); 
		return BitmapFactory.decodeFile(picturePath); 
	}
	
	public static void showShortToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	public static void showShortToast(Context context, int messageResId) {
		String message = context.getResources().getString(messageResId);
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	public static void showLongToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	
	public static void showLongToast(Context context, int messageResId) {
		String message = context.getResources().getString(messageResId);
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 对  Tags 进行拼接
	 * @param tags
	 * @return
	 */
	public static String labelTags(ArrayList<String> tags) {
		String result = "";
		if (tags == null) return result;
		for (String tag : tags) {
			result += "#" + tag + "# ";
		}
		return result;
	}
	
	/**
	 * 将点赞或评论等的计数变成更可读字符串
	 * @param count
	 * @return
	 */
	public static String getMoreReadableCount(int count) {
		String result = "";
		if (count < 10000) {
			result += count;
		} else if (count < 1000000) {
			result += String.format("%.1fK", count / 1000.0);
		} else if (count < 1000000000) {
			result += String.format("%.1fM", count / 1000000.0);
		} else {
			result += String.format("%.1fG", count / 1000000000.0);
		}
		return result;
	}
	
	/**
	 * 简单判断 URL的有效性
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isValidUrl(String url) {
		if (url == null || url.equals("") || url.equals("null")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 获取当前屏幕宽度
	 * @param context
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return windowManager.getDefaultDisplay().getWidth();
	}
	
	/**
	 * 获取当前屏幕高度
	 * @param context
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return windowManager.getDefaultDisplay().getHeight();
	}
}
