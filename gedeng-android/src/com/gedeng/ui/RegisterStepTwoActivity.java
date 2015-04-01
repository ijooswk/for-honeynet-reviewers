/**
 * @author Bella
 */
package com.gedeng.ui;

import com.gedeng.R;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.SignupParam;
import com.gedeng.client.connector.params.VerifyCaptchaParam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterStepTwoActivity extends Activity {

	TextView protocol;
	
	private Context context = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_step_two);
		
		context = this;
		
		final String phoneNumber = getIntent().getStringExtra(Constants.EXTRA_NAME_PHONE_NUMBER); 	
		final EditText veriCodeEditText = (EditText) findViewById(R.id.editText_veriCode);
		final EditText passwordEditText = (EditText) findViewById(R.id.editText_password);
		
		final Button nextStepButton = (Button) findViewById(R.id.button_nextStep);
		nextStepButton.setEnabled(false);
		nextStepButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final String password = passwordEditText.getText().toString();
				if (password.length() < 6) {
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle(R.string.notice);
					builder.setMessage(R.string.message_invalid_password);
					builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							//startActivity(new Intent(context, RegisterStepTwoActivity.class));
						}
					});
					builder.show();
					return;
				}
				
				String veriCode = veriCodeEditText.getText().toString();
				VerifyCaptchaParam verifyCaptchaParam = new VerifyCaptchaParam();
				verifyCaptchaParam.setTelephone(phoneNumber);
				verifyCaptchaParam.setCaptcha(veriCode);
				nextStepButton.setClickable(false);
				Connector.getInstance().sendAsynRequest(verifyCaptchaParam, new Callback() {
					@Override
					public void onSuccess(Response response) {
						int status = (Integer) response.getData(); // 0 failed
						if (status == 1) {
							SignupParam signupParam = new SignupParam();
							signupParam.setTelephone(phoneNumber);
							signupParam.setPassword(password);
							Connector.getInstance().sendAsynRequest(signupParam, new Callback() {
								@Override
								public void onSuccess(Response response) {
									nextStepButton.setClickable(true);
									startActivity(new Intent(getApplicationContext(), RegisterStepThreeActivity.class));
								}

								@Override
								public void onFailed(int errorCode,
										String message) {
									nextStepButton.setClickable(true);
									AlertDialog.Builder builder = new AlertDialog.Builder(context);
									builder.setTitle(R.string.title_signup_failed);
									//builder.setMessage(R.string.message_signup_failed);
									builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											//startActivity(new Intent(context, RegisterStepTwoActivity.class));
										}
									});
									builder.show();
								}
							});
						}
					}
					@Override
					public void onFailed(int errorCode, String message) {
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						builder.setTitle(R.string.title_check_vericode_failed);
						builder.setMessage(R.string.message_check_vericode_failed);
						builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								//startActivity(new Intent(context, RegisterStepTwoActivity.class));
							}
						});
						builder.show();
					}
				});
			}
		});
		
		CheckBox agreeProtocolCheckBox = (CheckBox) findViewById(R.id.checkBox_agreeProtocol);
		agreeProtocolCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				nextStepButton.setEnabled(isChecked);
			}
		});

	}
}
