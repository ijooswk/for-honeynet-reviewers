/**
 * @author Bella
 */
package com.gedeng.ui;

import com.gedeng.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	TextView version;
	
	View feedbackButton;
	View notifyButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		findViews();
		setListeners();
	}

	private void findViews() {
		notifyButton = findViewById(R.id.layout_notifyButton);
		feedbackButton = findViewById(R.id.layout_feedbackButton);
		version = (TextView) findViewById(R.id.textView_version);
	}

	private void setListeners() {
		feedbackButton.setOnClickListener(feedbackListener);
		notifyButton.setOnClickListener(notifyListener);
	}

	private Button.OnClickListener feedbackListener = new Button.OnClickListener() {
		public void onClick(final View v) {
			startActivity(new Intent(getApplicationContext(), FeedbackActivity.class));
		}
	};

	private Button.OnClickListener notifyListener = new Button.OnClickListener() {
		public void onClick(final View v) {
			Intent intent = new Intent();
			intent.setClass(SettingsActivity.this, NotifySettingsActivity.class);
			startActivity(intent);
		}
	};
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}