package mymock.mockView;

import android.widget.CheckBox;
import android.content.Context;

import java.io.*;

public class MockCheckBox extends CheckBox
{
	public String id; 
	public String onClickName;
	
	public MockCheckBox(String id, Context context)
	{
		super(null);
		this.id = id;
	}

	public MockCheckBox(Context context)
	{
		super(null);
	}

	public boolean isChecked()
	{
		return false;
		//return true;
	}

	public String toString()
	{
		//System.out.println(this.getClass().getName() + ": " +  id);
		return this.getClass().getName() + ": " +  id;
	}
}
