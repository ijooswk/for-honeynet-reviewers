/**
 * @author: Bella 
 * 用于提供引导页内容的fragment
 */
package com.gedeng.ui;

import com.gedeng.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GuideFragment extends Fragment {
	private int type;
	View rootView;
	ImageView welcome;

	public GuideFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_guide, container, false);

		findViews(rootView);
		showResults();
		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type = getArguments().getInt("KEY_TYPE");
	}

	private void findViews(View rootView) {
		welcome = (ImageView) rootView.findViewById(R.id.imageView_welcome);
	}

	private void showResults() {
		switch (type) {
		case 1:
			welcome.setImageResource(R.drawable.welcome1);
			break;
		case 2:
			welcome.setImageResource(R.drawable.welcome2);
			break;
		case 0:
		default:
			welcome.setImageResource(R.drawable.welcome0);
		}
	}
}
