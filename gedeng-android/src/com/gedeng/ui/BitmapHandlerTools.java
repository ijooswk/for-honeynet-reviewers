/**
 * @author:Duan Yichun
 * @date:2014/7/16
 * Tools for dealing with image
 */
package com.gedeng.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.gedeng.ui.customview.CropCircularImageView;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class BitmapHandlerTools {
	public static Bitmap toRoundCorner(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, bitmap.getWidth() / 2,
				bitmap.getHeight() / 2, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	public static Bitmap toCover(int width, int height) {
		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, width, height);
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, width / 2, height / 2, paint);

		paint.setAlpha(CropCircularImageView.LEFT_AREA_ALPHA);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
		canvas.drawRect(0, 0, width, height, paint);
		return output;
	}

	public static Bitmap toCover(int dstWidth, int dstHeight, int left,
			int top, int width, int height) {
		Bitmap output = Bitmap.createBitmap(dstWidth, dstHeight,
				Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(left, top, left + width, top + height);
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, width / 2, height / 2, paint);

		paint.setAlpha(CropCircularImageView.LEFT_AREA_ALPHA);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
		canvas.drawRect(0, 0, dstWidth, dstHeight, paint);
		return output;
	}

	/*
	 * 质量压缩方法
	 */
	public static Bitmap compressImageQuality(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while ((baos.toByteArray().length / 1024) > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
			if (options < 0) {
				break;
			}
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/*
	 * 先进行大小压缩，再进行质量压缩
	 */
	public static Bitmap compressImageSize(Bitmap image) {

		final float GOAL_HEIGHT = 800f;// 这里设置高度为800f
		final float GOAL_WIDTH = 480f;// 这里设置宽度为480f

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();

		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int width = newOpts.outWidth;
		int height = newOpts.outHeight;

		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int ratio = 1;// be=1表示不缩放
		if (width > height && width > GOAL_WIDTH) {
			ratio = (int) (newOpts.outWidth / GOAL_WIDTH);
		} else if (width < height && height > GOAL_HEIGHT) {
			ratio = (int) (newOpts.outHeight / GOAL_HEIGHT);
		}
		if (ratio <= 0)
			ratio = 1;
		newOpts.inSampleSize = ratio;// 设置缩放比例
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImageQuality(bitmap);// 压缩好比例大小后再进行质量压缩
	}
	
	public static Bitmap compressImageSize(Bitmap image, int goal_width, int goal_height) {

		final float GOAL_HEIGHT = goal_height;// 这里设置高度为800f
		final float GOAL_WIDTH = goal_width;// 这里设置宽度为480f

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();

		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int width = newOpts.outWidth;
		int height = newOpts.outHeight;

		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int ratio = 1;// be=1表示不缩放
		if (width > height && width > GOAL_WIDTH) {
			ratio = (int) (newOpts.outWidth / GOAL_WIDTH);
		} else if (width < height && height > GOAL_HEIGHT) {
			ratio = (int) (newOpts.outHeight / GOAL_HEIGHT);
		}
		if (ratio <= 0)
			ratio = 1;
		newOpts.inSampleSize = ratio;// 设置缩放比例
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return bitmap;
	}

	/*
	 * 压缩方式读入图像，进行比例压缩
	 */
	public static Bitmap getBitmap(String path) {
		BitmapFactory.Options ops = new BitmapFactory.Options();
		ops.inJustDecodeBounds = true;
		Bitmap bm = BitmapFactory.decodeFile(path, ops);
		ops.inSampleSize = 1;
		int oHeight = ops.outHeight;
		int oWidth = ops.outWidth;

		// 控制压缩比
		int contentHeight = 1024;
		int contentWidth = 1024;
		if (((float) oHeight / contentHeight) < ((float) oWidth / contentWidth)) {
			ops.inSampleSize = (int) Math.ceil((float) oWidth / contentWidth);
		} else {
			ops.inSampleSize = (int) Math.ceil((float) oHeight / contentHeight);
		}
		ops.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeFile(path, ops);
		return bm;
	}
}