/**
 * @author Bella
 */

package com.gedeng.ui;

import com.gedeng.R;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.SendCaptchaParam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterStepOneActivity extends Activity {
	Context context;
	private Button signUpButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_step_one);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		context = this;

		final EditText phoneNumberEditText = (EditText) findViewById(R.id.editText_phoneNumber);
		signUpButton = (Button) findViewById(R.id.button_signUp);
		signUpButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("Sign Up");
				final String phoneNumber = phoneNumberEditText.getText().toString();
				if (phoneNumber.length() != 11) {
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle(R.string.notice);
					builder.setMessage(R.string.message_invalid_phone_number);
					builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							//startActivity(new Intent(context, RegisterStepTwoActivity.class));
						}
					});
					builder.show();
					return;
				}
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle(R.string.title_check_phone_number);
				String message = getResources().getString(R.string.message_send_vericode_to_phone_number) + phoneNumber;
				builder.setMessage(message);
				builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						signUpButton.setClickable(false);
						SendCaptchaParam sendCaptchaParam = new SendCaptchaParam();
						sendCaptchaParam.setTelephone(phoneNumber);
						Connector.getInstance().sendAsynRequest(sendCaptchaParam, new Callback() {

							@Override
							public void onSuccess(Response response) {
								Intent intent = new Intent(context, RegisterStepTwoActivity.class);
								intent.putExtra(Constants.EXTRA_NAME_PHONE_NUMBER, phoneNumber);
								startActivity(intent);
								signUpButton.setClickable(true);
							}

							@Override
							public void onFailed(int errorCode, String message) {
								System.out.println("Request VeriCode Failed");
								signUpButton.setClickable(true);
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
		});
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
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
