package mymock.mockView;

import android.widget.EditText;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Editable;

import java.io.*;

public class MockEditText extends EditText
{
	public String id;
	public static int num = 123;
	
	public MockEditText(String id, Context context)
	{
		super(null);
		this.id = id;
		num++;
	}

	public MockEditText(Context context)
	{
		super(null);
		num++;
	}

	public Editable getText() {
	//	return new SpannableStringBuilder(this.getClass().getName() + ": " + id);
        //@yqq
        //Calculator example need to return null
		//return new SpannableStringBuilder(Integer.toString(num));
        //#yqq
        return new SpannableStringBuilder();
	}
	
	public String toString()
	{
		//System.out.println(this.getClass().getName() + ": " + id);
		return this.getClass().getName() + ": " + id;
	}
}
