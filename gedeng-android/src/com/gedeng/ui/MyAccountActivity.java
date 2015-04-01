/**
 * modified by Bella
 */
package com.gedeng.ui;

import com.gedeng.R;
import com.gedeng.client.GDClient;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.UpdateDescriptionParam;
import com.gedeng.client.connector.params.UpdateGenderParam;
import com.gedeng.client.connector.params.UpdateNicknameParam;
import com.gedeng.client.connector.params.UpdatePortraitParam;
import com.gedeng.client.connector.params.GetImageByUrlParam;
import com.gedeng.client.entity.User;
import com.gedeng.ui.basic.Converters;
import com.gedeng.ui.basic.UIUserBasicInfo;
import com.gedeng.ui.utils.Utils;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;

public class MyAccountActivity extends Activity {

	private static final int PICK_IMAGE = 1;
	private static final int CROP_IMAGE = 2;

	private ImageView portraitImageView;
	private ImageView portraitImageViewPrevView;
	private ImageView genderImageView;
	private TextView userNameTextView;
	private Bitmap portrait = null;
	private TextView signatureTextView;
	private EditText signatrueEditText;

	private User currentUser;
	private UIUserBasicInfo uiUserBasicInfo = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_account);
		// Show the Up button in the action bar.
		setupActionBar();

		portraitImageViewPrevView = (ImageView) findViewById(R.id.imageView_portrait);
		portraitImageView = (ImageView) findViewById(R.id.imageView_editPortrait);
		portraitImageView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pickImage();
			}
		});

		userNameTextView = (TextView) findViewById(R.id.textView_userName);
		final EditText userNameEditText = (EditText) findViewById(R.id.editText_userName);
		userNameEditText.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			public void afterTextChanged(Editable s) {
				String text = userNameEditText.getText().toString();
				userNameTextView.setText(text);
			}
		});	

		signatureTextView = (TextView) findViewById(R.id.textView_signature);
		signatrueEditText = (EditText) findViewById(R.id.editText_signature);
		signatrueEditText.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			public void afterTextChanged(Editable s) {
				String text = signatrueEditText.getText().toString();
				if (text.equals("")) {
					signatureTextView.setVisibility(View.GONE);
				} else {
					signatureTextView.setVisibility(View.VISIBLE);
					signatureTextView.setText(signatrueEditText.getText().toString());
					//System.out.println("Change Text");
				}
			}
		});

		genderImageView = (ImageView) findViewById(R.id.imageView_gender);
		final RadioGroup genderRadioGroup = (RadioGroup) findViewById(R.id.radioGroup_gender);
		genderRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_male:
					genderImageView.setImageResource(R.drawable.ic_male);
					uiUserBasicInfo.setGender(UIUserBasicInfo.GENDER_MALE);
					break;
				case R.id.radio_female:
					genderImageView.setImageResource(R.drawable.ic_female);
					uiUserBasicInfo.setGender(UIUserBasicInfo.GENDER_FEMALE);
					break;
				case R.id.radio_unknown_gender:
					genderImageView.setImageResource(R.drawable.ic_unknown_gender);
					uiUserBasicInfo.setGender(UIUserBasicInfo.GENDER_UNKNOWN);
					break;
				default:
					genderImageView.setImageResource(R.drawable.ic_unknown_gender);
					uiUserBasicInfo.setGender(UIUserBasicInfo.GENDER_UNKNOWN);
					break;
				}
			}
		});

		View setSchoolsView = findViewById(R.id.layout_setSchools);
		setSchoolsView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), SetSchoolsActivity.class));
			}
		});

		View resetPasswordView = findViewById(R.id.layout_resetPassword);
		resetPasswordView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
			}
		});

		currentUser = GDClient.getInstance().getCurrentUser();
		uiUserBasicInfo = Converters.convertILUserToUIUserBasicInfo(currentUser);
		if (uiUserBasicInfo != null) {
			Bitmap portrait = PortraitsHolder.getInstance().getPortrait(uiUserBasicInfo.getUid());
			if (portrait == null) {
				String portraitUrl = uiUserBasicInfo.getPortraitUrl();
				if (!Utils.isValidUrl(portraitUrl)) {
					portraitImageView.setImageResource(R.drawable.default_portrait);
					portraitImageViewPrevView.setImageResource(R.drawable.default_portrait);
				} else {
					GetImageByUrlParam getImageByUrlParam = new GetImageByUrlParam();
					getImageByUrlParam.setUrl(uiUserBasicInfo.getPortraitUrl());
					Connector.getInstance().sendAsynRequest(getImageByUrlParam, new Callback() {
						public void onSuccess(Response response) {
							Bitmap bitmap = (Bitmap) response.getData();
							portraitImageView.setImageBitmap(bitmap);
							portraitImageViewPrevView.setImageBitmap(bitmap);
							PortraitsHolder.getInstance().addEntry(uiUserBasicInfo.getUid(), bitmap);
						}
						public void onFailed(int errorCode, String message) {
							System.out.println("Get Image Failed : MyAccountActivity");
						}					
					});
				}
			} else {
				portraitImageView.setImageBitmap(portrait);
				portraitImageViewPrevView.setImageBitmap(portrait);
			}
			userNameEditText.setText(uiUserBasicInfo.getUserName());
			userNameTextView.setText(uiUserBasicInfo.getUserName());
			signatrueEditText.setText(uiUserBasicInfo.getSignature());
			signatureTextView.setText(uiUserBasicInfo.getSignature());
			System.out.println(uiUserBasicInfo.getGender());
			switch (uiUserBasicInfo.getGender()) {
			case UIUserBasicInfo.GENDER_MALE:
				genderImageView.setImageResource(R.drawable.ic_male);
				genderRadioGroup.check(R.id.radio_male);
				break;
			case UIUserBasicInfo.GENDER_FEMALE:
				genderImageView.setImageResource(R.drawable.ic_female);
				genderRadioGroup.check(R.id.radio_female);
				break;
			default:
				genderImageView.setImageResource(R.drawable.ic_unknown_gender);
				genderRadioGroup.check(R.id.radio_unknown_gender);
				break;
			}
		}
	}

	private void pickImage() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, PICK_IMAGE);
	}


	private void onSave() {
		if (uiUserBasicInfo == null) {
			Utils.showShortToast(this, R.string.message_save_failed);
			return;
		}

		String userName = userNameTextView.getText().toString();
		if (userName.equals("") != true) {
			UpdateNicknameParam updateNicknameParam = new UpdateNicknameParam();
			updateNicknameParam.setNickname(userName);
			Connector.getInstance().sendAsynRequest(updateNicknameParam, new Callback() {
				public void onSuccess(Response response) {
					System.out.println("Success : set user name");
				}

				public void onFailed(int errorCode, String message) {
					System.out.println("Failed : set user name");
				}
			});
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.notice);
			builder.setMessage(R.string.message_invalid_user_name_or_password);
			builder.show();
			return;
		}

		if (portrait != null) {
			UpdatePortraitParam updatePortraitParam = new UpdatePortraitParam();
			updatePortraitParam.setPortrait(portrait);
			Connector.getInstance().sendAsynRequest(updatePortraitParam, new Callback() {
				public void onSuccess(Response response) {
					System.out.println("Success : set portrait name");
				}

				public void onFailed(int errorCode, String message) {
					System.out.println("Failed : set portrait name");
				}
			});
		}

		String signature = signatrueEditText.getText().toString(); 
		UpdateDescriptionParam updateDescriptionParam = new UpdateDescriptionParam();
		updateDescriptionParam.setDescription(signature);
		System.out.println(signature);
		Connector.getInstance().sendAsynRequest(updateDescriptionParam, new Callback() {
			public void onSuccess(Response response) {
				System.out.println("Success : set signature");
			}

			public void onFailed(int errorCode, String message) {
				System.out.println("Failed : set signature");
			}
		});

		int gender = User.GENDER_UNKNOWN;
		if (uiUserBasicInfo.getGender() == UIUserBasicInfo.GENDER_MALE) {
			gender = User.GENDER_MALE;
		} else if (uiUserBasicInfo.getGender() == UIUserBasicInfo.GENDER_FEMALE) {
			gender = User.GENDER_FEMALE;
		}
		UpdateGenderParam updateGenderParam = new UpdateGenderParam();
		updateGenderParam.setGender(gender);
		Connector.getInstance().sendAsynRequest(updateGenderParam, new Callback() {
			public void onSuccess(Response response) {
				System.out.println("Success : set gender");
			}

			public void onFailed(int errorCode, String message) {
				System.out.println("Failed : set gender");
			}
		});

		Utils.showShortToast(this, R.string.message_save_success);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.my_account, menu);
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/*
		 * 选择图片返回之后，启动自定义圆形图片裁剪器选择图片
		 * 从自定义图片裁剪器返回之后，获取和设置图片
		 */
		if (requestCode == PICK_IMAGE) {
			if (data != null) {
				Uri uri = data.getData();
				ContentResolver content_resolver = this.getContentResolver();
				Cursor cursor = content_resolver.query(uri, null, null, null, null);
				cursor.moveToFirst();
				String path = cursor.getString(1); 
				Bitmap bmp = BitmapHandlerTools.getBitmap(path);	//采用这个方法获取图片是为了避免内存溢出
				CropBitmapCache.getBitmapCache().setInputBitmap(bmp);
				Intent intent = new Intent();
				intent.setClass(this,
						CropCircularImageActivity.class);
				startActivityForResult(intent, CROP_IMAGE);
			}
		} else if (requestCode == CROP_IMAGE) {
			Bitmap bitmap = CropBitmapCache.getBitmapCache().getOutputBitmap();
			if (bitmap != null) {
				portraitImageView.setImageBitmap(bitmap);
				portraitImageViewPrevView.setImageBitmap(bitmap);
				CropBitmapCache.getBitmapCache().setOutputBitmap(null);
				portrait = bitmap;
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_save:
			onSave();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
