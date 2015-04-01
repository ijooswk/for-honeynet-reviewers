package com.gedeng.ui;

import com.gedeng.R;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.FeedbackParam;
import com.gedeng.ui.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

/**
 * @author William
 * @date 2014/7/24
 * 
 * Activity : 用户发送反馈意见
 * 
 * modified by Bella, 2014/7/25
 * 
 */
public class FeedbackActivity extends Activity {

	EditText feedbackEditText = null;
	Button sendButton = null; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		// Show the Up button in the action bar.
		setupActionBar();
		
		feedbackEditText = (EditText) findViewById(R.id.editText_feedback);
		sendButton = (Button) findViewById(R.id.button_sendFeedback);
		sendButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sendButton.setClickable(false);
				sendFeedback();
			}
		});
	}
	
	private void sendFeedback() {
		String feedback  =feedbackEditText.getText().toString();
		if (feedback.equals("")) {
			Utils.showShortToast(this, R.string.message_invalid_feedback);
			return;
		}
		FeedbackParam feedbackParam = new FeedbackParam();
		feedbackParam.setText(feedback);
		Connector.getInstance().sendAsynRequest(feedbackParam, new Callback() {
			public void onSuccess(Response response) {
				Utils.showShortToast(getApplicationContext(), R.string.message_send_feedback_success);
				feedbackEditText.setText("");
				sendButton.setClickable(true);
			}
			public void onFailed(int errorCode, String message) {
				Utils.showShortToast(getApplicationContext(), R.string.message_send_feedback_failed);
				sendButton.setClickable(true);
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feedback, menu);
		return true;
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
