/**
 * @author: Bella Duan
 * @date:2014/7/16
 * 
 * modified by william, date 2014/7/19
 * 方形图像裁剪器
 * 用于各种不同手机上的裁剪器可能有所不同，所以决定使用自定义组件
 * update by Bella, date 2014/7/24
 */

package com.gedeng.ui;

import com.gedeng.R;
import com.gedeng.ui.customview.CropSquareImageView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CropSquareImageActivity extends Activity {
	private CropSquareImageView canvas = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crop_square_image_activity);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		Button confirm = (Button) findViewById(R.id.button_confirm);
		confirm.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				CropBitmapCache.getBitmapCache()
						.setOutputBitmap(canvas.getSubsetBitmap());
				Log.v("confirmfunction", "here");
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
		canvas = (CropSquareImageView) findViewById(R.id.canvas_crop);
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