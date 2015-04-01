package com.gedeng.ui;

import java.util.ArrayList;

import com.gedeng.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v4.app.NavUtils;

public class MyFollowsActivity extends Activity {

	private ListView followListView;
	private ArrayList<FollowListItem> followListItems;
	private FollowListAdapter followListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_follows);
		// Show the Up button in the action bar.
		setupActionBar();
		
		followListView = (ListView) findViewById(R.id.listView_followList);
		
		followListItems = new ArrayList<FollowListItem>();

		followListItems.add(new FollowListItem());
		followListItems.add(new FollowListItem());
		followListItems.add(new FollowListItem());

		followListAdapter = new FollowListAdapter(this, followListItems);
		followListView.setAdapter(followListAdapter);
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
		getMenuInflater().inflate(R.menu.my_follows, menu);
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
