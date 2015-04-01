package com.gedeng.ui;

import com.gedeng.R;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.GetImageByUrlParam;
import com.gedeng.client.connector.params.GetPostsByAuthorIdParam;
import com.gedeng.client.entity.Post;
import com.gedeng.ui.basic.UIUserBasicInfo;
import com.gedeng.ui.utils.Utils;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UserBasicInfoActivity extends Activity {

	UIUserBasicInfo userBasicInfo;
	Bitmap defaultPortrait = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_basic_info);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		defaultPortrait = BitmapFactory.decodeResource(getResources(), R.drawable.default_portrait);

		userBasicInfo = (UIUserBasicInfo)getIntent().getSerializableExtra(Constants.EXTRA_NAME_USER_BASIC_INFO);
		System.out.println(userBasicInfo.getUserName());
		TextView userNameTextView = (TextView) findViewById(R.id.textView_userName);
		userNameTextView.setText(userBasicInfo.getUserName());
		TextView signatureTextView = (TextView) findViewById(R.id.textView_signature);
		if (userBasicInfo.getSignature().equals("")) {
			signatureTextView.setVisibility(View.GONE);
		} else {
			signatureTextView.setText(userBasicInfo.getSignature());
		}
		final ImageView portraitImageView = (ImageView) findViewById(R.id.imageView_portrait);
		String portraitURL = userBasicInfo.getPortraitUrl();
		Bitmap portrait = PortraitsHolder.getInstance().getPortrait(userBasicInfo.getUid());
		if (portrait == null) {
			if (!Utils.isValidUrl(portraitURL)) {
			} else {
				GetImageByUrlParam getImageByUrlParam = new GetImageByUrlParam();
				getImageByUrlParam.setUrl(userBasicInfo.getPortraitUrl());
				Connector.getInstance().sendAsynRequest(getImageByUrlParam, new Callback() {
					public void onSuccess(Response response) {
						Bitmap bitmap = (Bitmap) response.getData();
						portraitImageView.setImageBitmap(bitmap);
						PortraitsHolder.getInstance().addEntry(userBasicInfo.getUid(), bitmap);
					}
					public void onFailed(int errorCode, String message) {
						portraitImageView.setImageBitmap(defaultPortrait);
						PortraitsHolder.getInstance().addEntry(userBasicInfo.getUid(), defaultPortrait);
					}
				});
			}
		} else {
			portraitImageView.setImageBitmap(portrait);
		}

		ImageView genderImageView = (ImageView) findViewById(R.id.imageView_gender);
		if (userBasicInfo.getGender() == UIUserBasicInfo.GENDER_MALE) {
			genderImageView.setImageResource(R.drawable.ic_male);
		} else if (userBasicInfo.getGender() == UIUserBasicInfo.GENDER_FEMALE) {
			genderImageView.setImageResource(R.drawable.ic_female);
		} else {
			genderImageView.setImageResource(R.drawable.ic_unknown_gender);
		}

		final ImageView[] previews = new ImageView[4];
		previews[0] = (ImageView) findViewById(R.id.imageView_postPreview1);
		previews[1] = (ImageView) findViewById(R.id.imageView_postPreview2);
		previews[2] = (ImageView) findViewById(R.id.imageView_postPreview3);
		previews[3] = (ImageView) findViewById(R.id.imageView_postPreview4);
		ImageView moreImageView = (ImageView) findViewById(R.id.imageView_more);
		if (userBasicInfo != null) {
			if (userBasicInfo.getPostCount() < 4) {
				int count = 4 - userBasicInfo.getPostCount();
				for (int i = 0; i < count; i++) {
					previews[3-i].setVisibility(View.GONE);
				}
			}
			if (userBasicInfo.getPostCount() <= 4) {
				moreImageView.setVisibility(View.GONE);
			}

			GetPostsByAuthorIdParam getPostsByAuthorIdParam = new GetPostsByAuthorIdParam();
			getPostsByAuthorIdParam.setId(userBasicInfo.getUid());
			getPostsByAuthorIdParam.setCount(4);
			Connector.getInstance().sendAsynRequest(getPostsByAuthorIdParam, new Callback() {
				public void onSuccess(Response response) {
					Post[] posts = (Post[])response.getData();
					if (posts == null) return;
					for (int i = 0; i < posts.length; i++) {
						String[] urls = posts[i].getImagesUrl();
						if (urls != null && urls.length > 0) {
							GetImageByUrlParam getImageByUrlParam = new GetImageByUrlParam();
							getImageByUrlParam.setUrl(urls[0]);
							final int j = i;
							Connector.getInstance().sendAsynRequest(getImageByUrlParam, new Callback() {
								public void onSuccess(Response response) {
									Bitmap bitmap = (Bitmap) response.getData();
									if (bitmap != null) {
										previews[j].setImageBitmap(bitmap);
									} else {
										previews[j].setImageResource(R.drawable.default_background);
									}
								}
								public void onFailed(int errorCode,
										String message) {
									previews[j].setImageResource(R.drawable.default_background);
								}

							});
						} else {
							previews[i].setImageResource(R.drawable.default_background);
						}
					}
				}
				public void onFailed(int errorCode, String message) {
				}
			});			
		}

		View postsView = findViewById(R.id.layout_posts); 
		postsView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (userBasicInfo != null) {
					Intent intent = new Intent(getApplicationContext(), ShowAllPostsActivity.class);
					intent.putExtra(Constants.EXTRA_NAME_USER_BASIC_INFO, userBasicInfo);
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.user_basic_info, menu);
		return true;
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
