/**
 * @author: Bella
 * @date:2014/7/16
 * 
 * modified by william, date 2014/7/19
 * 一个圆形的图片裁剪器，用于各种情况下的用户头像设置和变更
 * 由于用户头像设置为圆形，使用圆形的图片裁剪器可以获得较好的用户体验
 * 图像裁剪的主要工作在CropImageView自定义控件中完成
 */

package com.gedeng.ui;

import com.gedeng.R;
import com.gedeng.ui.customview.CropCircularImageView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CropCircularImageActivity extends Activity {
	private CropCircularImageView canvas = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crop_circular_image_activity);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		Button confirm = (Button) findViewById(R.id.button_confirm);
		confirm.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				CropBitmapCache.getBitmapCache()
						.setOutputBitmap(canvas.getSubsetBitmap());
				//Log.v("confirmfunction", "here");
				finish();
			}
		});
		Button exit = (Button) findViewById(R.id.button_exit);
		exit.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				CropBitmapCache.getBitmapCache()
				.setOutputBitmap(null);
				finish();
			}
		});
		Bitmap bmp = CropBitmapCache.getBitmapCache().getInputBitmap();
		canvas = (CropCircularImageView) findViewById(R.id.canvas_crop);
		canvas.setBitmap(bmp);
		CropBitmapCache.getBitmapCache().setInputBitmap(null);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}