/**
 * @author: William
 * 
 * modified by Bella, date 2014/7/24
 */

package com.gedeng.ui;

import com.gedeng.R;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.CreatePostParam;
import com.gedeng.ui.utils.Utils;

import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

// Edit a new post, and send it
public class ComposePostActivity extends Activity {

	private static final int IMAGE_PICKER_SELECT 	= 1;
	private static final int IMAGE_PICKER_CROP 		= 2;

	private View backgroundView = null;
	private View cameraView = null;
	private Bitmap postBackground = null;
	private EditText postTextEditText = null;

	private boolean isPosing = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_post);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		backgroundView = findViewById(R.id.relativeLayout_background);
		// If the camera icon is unvisible, click the backgroundView to pick and crop images.
		backgroundView.setOnClickListener(new OnClickListener() { 
			public void onClick(View v) {
				if (cameraView.getVisibility() == View.GONE) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, IMAGE_PICKER_SELECT);
				}
			}
		});
		
		// Adjust the backgroundView to adapt different screen size
		View adjustView = backgroundView;
		adjustView
				.setLayoutParams(new android.widget.RelativeLayout.LayoutParams(
						Utils.getScreenWidth(this), Utils.getScreenWidth(this)));
		cameraView = findViewById(R.id.imageView_camera);
		cameraView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, IMAGE_PICKER_SELECT);
			}
		});

		postTextEditText = (EditText) findViewById(R.id.editText_postText);
	}

	/**
	 * Processing Photo Selection Result
	 */
	@SuppressWarnings("deprecation")
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*
		 * 调用系统的图片选择器选择图片，传递参数给自定义的activity进行图片切割
		 * 该图片切割器是方形的
		 */
		if (requestCode == IMAGE_PICKER_SELECT
				&& resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			// cropImage(uri, 360, 360, IMAGE_PICKER_CROP);
			ContentResolver content_resolver = this.getContentResolver();
			Cursor cursor = content_resolver.query(uri, null, null, null, null);
			cursor.moveToFirst();
			String path = cursor.getString(1);
			Bitmap bmp = BitmapHandlerTools.getBitmap(path);
			CropBitmapCache.getBitmapCache().setInputBitmap(bmp);
			Intent intent = new Intent();
			intent.setClass(ComposePostActivity.this,
					CropSquareImageActivity.class);
			startActivityForResult(intent, IMAGE_PICKER_CROP);
		} else if (requestCode == IMAGE_PICKER_CROP) {
			Log.v("background", "return");
			/*
			 * 从自定义的图片切割器返回，将背景图设置为切割到的图片
			 * 注意在此之前需要把图片缩放到固定大小
			 */
			Bitmap bitmap = CropBitmapCache.getBitmapCache().getOutputBitmap();
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, Constants.UPLOAD_IMAGE_SIZE, Constants.UPLOAD_IMAGE_SIZE);
			if (bitmap != null) {
				postBackground = bitmap;
				Drawable drawable = new BitmapDrawable(getResources(), bitmap);
				backgroundView.setBackgroundDrawable(drawable);
				cameraView.setVisibility(View.GONE);
				CropBitmapCache.getBitmapCache().setOutputBitmap(null);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_post, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.action_send_post:
			sendPost();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void sendPost() {
		if (postTextEditText.getText().toString().equals("")) {
			Utils.showLongToast(this, R.string.message_invalid_description);
			return;
		}
		
		if (isPosing) {
			return;
		}
		isPosing = true;
		
		if (postBackground != null) {
			CreatePostParam createPostParam = new CreatePostParam();
			Bitmap[] images = new Bitmap[1];
			images[0] = postBackground;
			createPostParam.setImages(images);
			createPostParam.setTag("test");
			createPostParam.setText(postTextEditText.getText().toString());
			Connector.getInstance().sendAsynRequest(createPostParam,
					new Callback() {

						@Override
						public void onSuccess(Response response) {
							Utils.showShortToast(getApplicationContext(),
									R.string.message_send_post_success);
							System.out.println("Post Success");
							isPosing = false;
							finish();
						}

						@Override
						public void onFailed(int errorCode, String message) {
							Utils.showShortToast(getApplicationContext(),
									R.string.message_send_post_failed);
							System.out.println("Post : errorCode : "
									+ errorCode);
							isPosing = false;
						}

					});
		} else {
			isPosing = false;
		}
	}
}
