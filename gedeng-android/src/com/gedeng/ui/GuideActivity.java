/**
 * @author: Bella
 * @date: 2014/7/17
 * 
 * 引导页类
 */

package com.gedeng.ui;

import com.gedeng.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class GuideActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	/**
	 * 引导页张数： COUNT张
	 * 注释掉的与autoplay相关的代码用于自动播放
	 */
	SectionsPagerAdapter sectionsPagerAdapter;
	private static final int COUNT = 3;
	//private boolean autoPlay = true;
	//private Message message = new Message();
	//private AutoPlayHandler autoPlayHandler = new AutoPlayHandler();
	//private static final int AUTO_PLAY_TIME = 3000;
	private ImageView rings = null;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager viewPager;
	Button registerButton;
	Button loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		findViews();
		setListeners();
		//autoPlay();
	}

	private void findViews() {
		sectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(sectionsPagerAdapter);
		registerButton = (Button) findViewById(R.id.guide_signup_button);
		loginButton = (Button) findViewById(R.id.guide_login_button);
		rings = (ImageView) findViewById(R.id.imageView_rings);
		
		/* 
		 * 这段代码用来禁止用户自行滑动
		 * 
		viewPager.setEnabled(false);
		viewPager.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});*/
		/*
		 * 这段代码用来降低pager滑入和滑出的时间
		 * 
		 try {
		    Field mScroller;
		    mScroller = ViewPager.class.getDeclaredField("mScroller");
		    mScroller.setAccessible(true); 
		    Interpolator sInterpolator = new AccelerateInterpolator();
		    FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(), sInterpolator);
		    scroller.setFixedDuration(1000);
		    mScroller.set(viewPager, scroller);
		} catch (Exception e) {
			e.printStackTrace();
		} */
	}

	private void setListeners() {
		registerButton.setOnClickListener(registerListener);
		loginButton.setOnClickListener(loginListener);
		viewPager.setCurrentItem(3000);	//使用户可以向左滑动3000次
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			/**
			 * 在页面滑动的同时改变小圆点的显示状态
			 */
			public void onPageSelected(int arg0) {
				arg0 = arg0 % COUNT;
				switch (arg0) {
				case 0:
					rings.setImageResource(R.drawable.rings0);
					break;
				case 1:
					rings.setImageResource(R.drawable.rings1);
					break;
				case 2:
					rings.setImageResource(R.drawable.rings2);
					break;
				}
			}

		});
	}

	private Button.OnClickListener registerListener = new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(GuideActivity.this, RegisterStepOneActivity.class);
			startActivity(intent);
		}
	};

	private Button.OnClickListener loginListener = new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(GuideActivity.this, LoginActivity.class);
			startActivity(intent);
		}
	};

	/*private void autoPlay() {
		if (autoPlay == true) {
			autoPlayHandler.sendMessageDelayed(message, AUTO_PLAY_TIME);
		}
	}

	@SuppressLint("HandlerLeak")
	class AutoPlayHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if (autoPlay == true) {
				if (viewPager.getCurrentItem() == Integer.MAX_VALUE) {
					viewPager.setCurrentItem(0);
				} else {
					viewPager.setCurrentItem((viewPager.getCurrentItem() + 1));
				}
				message = autoPlayHandler.obtainMessage(0);
				sendMessageDelayed(message, AUTO_PLAY_TIME);
			}
		}
	}*/

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 * 重载的PagerAdapter
	 * 每个Pager是一个Fragment
	 * 其实就现在的设计而言可以不使用fragement，但是这样的设计有利于扩展
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			int pos = position % COUNT;
			Fragment fragment;
			Bundle args;
			switch (pos) {

			case 1:
				fragment = new GuideFragment();
				args = new Bundle();
				args.putInt("KEY_TYPE", 1);
				fragment.setArguments(args);
				break;
			case 2:
				fragment = new GuideFragment();
				args = new Bundle();
				args.putInt("KEY_TYPE", 2);
				fragment.setArguments(args);
				break;
			case 0:
			default:
				fragment = new GuideFragment();
				args = new Bundle();
				args.putInt("KEY_TYPE", 0);
				fragment.setArguments(args);
				break;
			}
			return fragment;
		}

		@Override
		/**
		 * 返回Integer.MAX_VALUE是为了保证循环滑动
		 */
		public int getCount() {
			return Integer.MAX_VALUE;
		}
	}
	
	/*private class FixedSpeedScroller extends Scroller {

	    private int mDuration = 5000;

	    public FixedSpeedScroller(Context context) {
	        super(context);
	    }

	    public void setFixedDuration(int duration) {
	    	mDuration = duration;
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator) {
	        super(context, interpolator);
	    }

	    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
	        super(context, interpolator, flywheel);
	    }


	    @Override
	    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
	        // Ignore received duration, use fixed one instead
	        super.startScroll(startX, startY, dx, dy, mDuration);
	    }

	    @Override
	    public void startScroll(int startX, int startY, int dx, int dy) {
	        // Ignore received duration, use fixed one instead
	        super.startScroll(startX, startY, dx, dy, mDuration);
	    }
	}*/
}
