/**
 * @author Bella
 * 
 */

package com.gedeng.ui;
import com.gedeng.R;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.ResetPasswordParam;
import com.gedeng.client.connector.params.SendCaptchaParam;
import com.gedeng.client.connector.params.VerifyCaptchaParam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ResetPasswordActivity extends Activity {

	private EditText telephone;
	private EditText password;
	private EditText validCode;
	private Button getValidCode;
	private Button finish;
	private TimeCount timer;
	
	static final int CNT = 60000;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password);
		// Show the Up button in the action bar.
		setupActionBar();

		findViews();
		setListeners();

	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void findViews() {
		telephone = (EditText) findViewById(R.id.editText_phoneNumber);
		password = (EditText) findViewById(R.id.editText_password);
		getValidCode = (Button) findViewById(R.id.button_getValidCode);
		validCode = (EditText) findViewById(R.id.editText_validCode);
		finish = (Button) findViewById(R.id.button_finish);
		timer = new TimeCount(CNT, 1000);
	}

	private void setListeners() {
		finish.setOnClickListener(finishListener);
		getValidCode.setOnClickListener(getValidCodeListener);
	}

	private Button.OnClickListener finishListener = new Button.OnClickListener() {
		public void onClick(View v) {
			finish.setClickable(false);
			VerifyCaptchaParam verifyCaptchaParam = new VerifyCaptchaParam();
			verifyCaptchaParam.setTelephone(telephone.getText().toString());
			verifyCaptchaParam.setCaptcha(validCode.getText().toString());
			Connector.getInstance().sendAsynRequest(verifyCaptchaParam, new Callback() {
				@Override
				public void onSuccess(Response response) {
					ResetPasswordParam resetPasswordParam = new ResetPasswordParam();
					resetPasswordParam.setPassword(password.getText().toString());
					Connector.getInstance().sendAsynRequest(resetPasswordParam, new Callback() {

						@Override
						public void onSuccess(Response response) {
							finish();
						}

						@Override
						public void onFailed(int errorCode, String message) {
							AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
							builder.setTitle(R.string.reset_password_failed);
							builder.setMessage(message);
							builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
								}
							});
							builder.show();
							finish.setClickable(true);
						}

					});
				}
				@Override
				public void onFailed(int errorCode, String message) {
					finish.setClickable(true);
					AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
					builder.setTitle(R.string.reset_password_failed);
					builder.setMessage(message);
					builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							finish.setClickable(true);
						}
					});
				}
			});
			
		}
	};
	
	private Button.OnClickListener getValidCodeListener = new Button.OnClickListener() {
		public void onClick(View v) {
			final String phoneNumber = telephone.getText().toString();
			AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
			builder.setTitle(R.string.title_check_phone_number);
			builder.setMessage(getResources().getString(R.string.message_send_vericode_to_phone_number) + phoneNumber);
			builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					timer.start();
					SendCaptchaParam sendCaptchaParam = new SendCaptchaParam();
					sendCaptchaParam.setTelephone(phoneNumber);
					Connector.getInstance().sendAsynRequest(sendCaptchaParam, new Callback() {

						@Override
						public void onSuccess(Response response) {
							
						}

						@Override
						public void onFailed(int errorCode, String message) {
							System.out.println("Request VeriCode Failed");
						}
						
					});
				}
			});
			builder.setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			builder.show();
		}
	};
	
	private class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
		@Override
		public void onFinish() {
			getValidCode.setText(R.string.get_password_again);
			getValidCode.setClickable(true);
		}
		@Override
		public void onTick(long millisUntilFinished){
			getValidCode.setClickable(false);
			String temp = getResources().getString(R.string.reset_password_resend);
			getValidCode.setText(millisUntilFinished /1000 + temp);
		}
	}
}
