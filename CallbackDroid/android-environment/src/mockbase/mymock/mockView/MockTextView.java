package mymock.mockView;

import android.widget.TextView;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Editable;

import java.io.*;

public class MockTextView extends TextView
{
	public String id;
	public String text;
	
	public MockTextView(String id, Context context)
	{
		super(null);
		this.id = id;
	}

	public MockTextView(Context context)
	{
		super(null);
	}

	public void setText(String str)
	{
		text = str;
	}

	public String getText() {
		return text;
	}
	
	public String toString()
	{
		//System.out.println(this.getClass().getName() + ": " + id);
		return this.getClass().getName() + ": " + id;
	}
}
