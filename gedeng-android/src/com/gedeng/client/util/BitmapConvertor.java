package com.gedeng.client.util;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.util.Base64;

public class BitmapConvertor {
	
	public static String Bitmap2String(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		// baos.flush();
		// baos.close();
		byte[] bytes = baos.toByteArray();
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}

}
