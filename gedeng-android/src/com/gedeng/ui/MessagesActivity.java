package com.gedeng.ui;


import java.util.ArrayList;
import java.util.Locale;

import com.gedeng.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.gedeng.client.adaptor.QXNetwork.Messages.QxMessage;
import com.gedeng.client.adaptor.QXStorage.StorageManager;

@SuppressLint("ValidFragment")
public class MessagesActivity extends FragmentActivity implements
ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messages);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// Show the Up button in the action bar.
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
		.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});


		//actionBar.setCustomView(R.layout.tab_layout);

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab().setTabListener(this));
		}

		for (int i = 0; i < actionBar.getTabCount(); i++) {
			ActionBar.Tab t = actionBar.getTabAt(i);
			t.setCustomView(R.layout.messages_tab_layout);
			TextView title = (TextView)t.getCustomView().findViewById(R.id.tab_title);
			title.setText(mSectionsPagerAdapter.getPageTitle(i));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.messages, menu);
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
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
			View rootView = null;
			switch (sectionNumber) {
			case 1:
				rootView = createVoteMessagesFragmentView(inflater, container);
				break;
			case 2:
				rootView = createCommentMessagesFragmentView(inflater, container); 
				break;
			case 3:
				rootView = createFollowMessagesFragmentView(inflater, container); 
				break;
			default:
				break;
			}
			return rootView;
		}
	}

	private View createVoteMessagesFragmentView (LayoutInflater inflater, ViewGroup container) {
		View rootView = inflater.inflate(R.layout.fragment_like_messages, container, false);
		ListView listView = (ListView) rootView.findViewById(R.id.listView_likeMessages);

		final ArrayList<QxMessage> messagesListItems = StorageManager.getMessages(QxMessage.MESSAGE_TYPE_VOTED);
		final MessageVotedListAdapter messageVotedListAdapter =
				new MessageVotedListAdapter(getApplicationContext(), messagesListItems);
		listView.setAdapter(messageVotedListAdapter);
		
//		QxPushManager.addMessageReceivedListener(new QxMessageReceivedListener() {
//			
//			@Override
//			public void onMessageReceived(QxMessage message) {
//				if (message.type == 2) {
//					int uid = message.actor_uid;
//					final int pid = message.post_id;
//					GetUserByIdParam getUserByIdParam = new GetUserByIdParam();
//					getUserByIdParam.setId(uid);
//					Connector.getInstance().sendAsynRequest(getUserByIdParam, new Callback() {
//						public void onSuccess(Response response) {
//							final User user = (User) response.getData();
//							GetPostByIdParam getPostByIdParam = new GetPostByIdParam();
//							getPostByIdParam.setId(pid);
//							Connector.getInstance().sendAsynRequest(getPostByIdParam, new Callback() {
//								public void onSuccess(Response response) {
//									Post post = (Post) response.getData();
//									DateTime temp1 = DateTime.now();
//									DateTime temp2 = new DateTime(temp1);
//									String date = com.gedeng.client.util.Time.getTimeSpanString(temp2);
//									System.out.println(date);
//									LikeMessageListItem likeMessageListItem = 
//											new LikeMessageListItem(
//													user.getId(),
//													post.getId(),
//													user.getPortraitUrl(), 
//													user.getNickname(), 
//													date, 
//													post.getText());
//									likeMessageListItems.add(0, likeMessageListItem);
//									likeMessageListAdapter.notifyDataSetChanged();
//								}
//								public void onFailed(int errorCode,
//										String message) {
//								}								
//							});
//						}
//
//						@Override
//						public void onFailed(int errorCode, String message) {
//						}
//						
//					});
//				}
//			}
//		});

		return rootView;
	}


	private View createFollowMessagesFragmentView (LayoutInflater inflater, ViewGroup container) {
		View rootView = inflater.inflate(R.layout.fragment_follow_massages, container, false);
		ListView listView = (ListView) rootView.findViewById(R.id.listView_followMessages);

        final ArrayList<QxMessage> messagesListItems = StorageManager.getMessages(QxMessage.MESSAGE_TYPE_NOTIFICATION);
        final MessageVotedListAdapter messageVotedListAdapter =
                new MessageVotedListAdapter(getApplicationContext(), messagesListItems);
        listView.setAdapter(messageVotedListAdapter);

		return rootView;
	}

	private View createCommentMessagesFragmentView (LayoutInflater inflater, ViewGroup container) {
		View rootView = inflater.inflate(R.layout.fragment_comment_messages, container, false);
		ListView listView = (ListView) rootView.findViewById(R.id.listView_commentMessages);

        final ArrayList<QxMessage> messagesListItems = StorageManager.getMessages(QxMessage.MESSAGE_TYPE_REPLIED);
        final MessageCommentedListAdapter messageCommentedListAdapter =
                new MessageCommentedListAdapter(getApplicationContext(), messagesListItems);
        listView.setAdapter(messageCommentedListAdapter);

		return rootView;
	}
}
