package mymock.mockView;

import android.widget.LinearLayout;
import android.view.View;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Editable;

import java.io.*;
import java.util.*;

public class MockLinearLayout extends LinearLayout
{
	public String id;
	public String text;
	
	public ArrayList<View> viewGrp; 
	
	public MockLinearLayout(String id, Context context)
	{
		super(null);
		this.id = id;
		viewGrp = new ArrayList<View>();
	}

	public MockLinearLayout(Context context)
	{
		super(null);
	}
	
	/*todo: should generate another textview in the table here*/
	public void addView(View child) 
	{
		/*do nothing temporally*/
		viewGrp.add(child);
	}	

	public String toString()
	{
		//System.out.println(this.getClass().getName() + ": " + id);
		return this.getClass().getName() + ": " + id;
	}
}
