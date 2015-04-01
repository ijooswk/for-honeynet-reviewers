/**
 * @author Bella
 * @date 2014/7/13
 * 登陆页面
 */
package com.gedeng.ui;

import com.gedeng.R;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.LoginParam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	private Button loginButton;
	private View forgetPasswordView;

	private EditText phoneNumberEditText;
	private EditText passwordEditText;
	
	private Context context;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		context = this;
		
		findViews();
		setListeners();
	}

	private void findViews() {
		forgetPasswordView = findViewById(R.id.textView_forgetPassword);
		loginButton = (Button) findViewById(R.id.button_login);
		phoneNumberEditText = (EditText) findViewById(R.id.editText_telephone);
		passwordEditText = (EditText) findViewById(R.id.editText_password);
	}

	private void setListeners() {
		loginButton.setOnClickListener(loginListener);
		forgetPasswordView.setOnClickListener(forgetPasswordListener);
		passwordEditText.setOnTouchListener(passwordListener);
	}

	private Button.OnClickListener loginListener = new Button.OnClickListener() {
		public void onClick(final View v) {
			String phoneNumber = phoneNumberEditText.getText().toString();
			String password = passwordEditText.getText().toString();
			if (phoneNumber.equals("") || password.equals("")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle(R.string.notice);
				builder.setMessage(R.string.message_invalid_user_name_or_password);
				builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						//startActivity(new Intent(context, RegisterStepTwoActivity.class));
					}
				});
				builder.show();
				return;
			}
			loginButton.setClickable(false);
			LoginParam loginParam = new LoginParam();
			loginParam.setUsername(phoneNumber);
			loginParam.setPassword(password);
			Connector.getInstance().sendAsynRequest(loginParam, new Callback() {

				@Override
				public void onSuccess(Response response) {
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					finish();
				}

				@Override
				public void onFailed(int errorCode, String message) {
					System.out.println("Failed to log in");
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle(R.string.login_failed);
					builder.setMessage(message);
					builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					builder.show();
					loginButton.setClickable(true);
				}

			});
		}
	};

	private Button.OnClickListener forgetPasswordListener = new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, ResetPasswordActivity.class);
			startActivity(intent);
		}
	};

	private EditText.OnTouchListener passwordListener = new EditText.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			((EditText) v).setText("");
			((EditText) v).setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_PASSWORD);
			return false;
		}
	};
}