/**
 * @author Bella
 */
package com.gedeng.ui;

import com.gedeng.R;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.UpdateNicknameParam;
import com.gedeng.client.connector.params.UpdatePortraitParam;
import com.gedeng.ui.customview.CircularImageView;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterStepThreeActivity extends Activity {

	private CircularImageView portraitImageView;
	private ImageView collageStudent, middleSchoolStudent,
	primarySchoolStudent;



	private EditText schoolName;
//	private EditText schoolYear;
//	private View layoutSchool;



	private EditText userNameEditText;
	private Button completeSignupButton;

	//private User user = null;
	private Bitmap portrait = null;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_step_three);
		findViews();
		setListeners();

		//user = GDClient.getInstance().currentUser();

		userNameEditText = (EditText) findViewById(R.id.editText_name);
		completeSignupButton = (Button) findViewById(R.id.button_finish);
		completeSignupButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				completeSignupButton.setClickable(false);
				String userName = userNameEditText.getText().toString();
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
				completeSignupButton.setClickable(true);
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
			}
		});
	}

	private void findViews() {
		collageStudent = (ImageView) findViewById(R.id.imageView_collageStudent);
		middleSchoolStudent = (ImageView) findViewById(R.id.imageView_middleSchoolStudent);
		primarySchoolStudent = (ImageView) findViewById(R.id.imageView_primarySchoolStudent);
		schoolName = (EditText) findViewById(R.id.editText_schoolName);
//		schoolYear = (EditText) findViewById(R.id.editText_schoolYear);
		portraitImageView = (CircularImageView) findViewById(R.id.imageView_uploadPortrait);
		//layoutSchool = findViewById(R.id.layout_school);
	}

	/**
	 * 这几个listener现在的主要目的是动画设置
	 */
	private void setListeners() {
		collageStudent.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				collageStudent
				.setImageResource(R.drawable.collage_student_checked);
				middleSchoolStudent
				.setImageResource(R.drawable.middle_school_student);
				primarySchoolStudent
				.setImageResource(R.drawable.primary_school_student);
				schoolName.setText(R.string.register_input_school_collage);
			}
		});
		middleSchoolStudent.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				collageStudent.setImageResource(R.drawable.collage_student);
				middleSchoolStudent
				.setImageResource(R.drawable.middle_school_student_checked);
				primarySchoolStudent
				.setImageResource(R.drawable.primary_school_student);
				schoolName.setText(R.string.register_school_name_middle_school);
			}
		});
		primarySchoolStudent.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				collageStudent.setImageResource(R.drawable.collage_student);
				middleSchoolStudent
				.setImageResource(R.drawable.middle_school_student);
				primarySchoolStudent
				.setImageResource(R.drawable.primary_school_student_checked);
				schoolName
				.setText(R.string.register_school_name_primary_school);
			}
		});
		portraitImageView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				startActivityForResult(intent, 1);
			}
		});
	}

	
	/**
	 * 在使用系统图片选择器选择图片之后，调用自定义圆形图片裁剪器
	 * 圆形图片裁剪器返回之后，获取裁剪得到的图像
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			if (data != null) {
				Uri uri = data.getData();
				ContentResolver content_resolver = this.getContentResolver();
				Cursor cursor = content_resolver.query(uri, null, null, null, null);
		        cursor.moveToFirst();
		        String path = cursor.getString(1);
				Bitmap bmp = BitmapHandlerTools.getBitmap(path);
				CropBitmapCache.getBitmapCache().setInputBitmap(bmp);
				Intent intent = new Intent();
				intent.setClass(RegisterStepThreeActivity.this,
						CropCircularImageActivity.class);
				startActivityForResult(intent, 2);
			}
		} else if (requestCode == 2) {
			Bitmap bitmap = CropBitmapCache.getBitmapCache().getOutputBitmap();
			if (bitmap != null) {
				portrait = bitmap;
				portraitImageView.setImageBitmap(bitmap);
				CropBitmapCache.getBitmapCache().setOutputBitmap(null);
			}
		}

	}

}
