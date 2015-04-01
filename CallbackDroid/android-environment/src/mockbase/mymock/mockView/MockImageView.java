package mymock.mockView;

import android.widget.ImageView;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Editable;

import java.io.*;

public class MockImageView extends ImageView
{
	public String id;
	public String text;
	
	public MockImageView(String id, Context context)
	{
		super(null);
		this.id = id;
	}

	public MockImageView(Context context)
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
