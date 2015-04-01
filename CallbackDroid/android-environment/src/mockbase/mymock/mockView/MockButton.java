package mymock.mockView;

import android.widget.Button;
import android.content.Context;

import java.io.*;

public class MockButton extends Button
{
	public String id; 
	public String onClickName;
    
    //@yqq
    public int buttonTextValue;
    //#yqq
	
	public MockButton(String id, Context context)
	{
		super(null);
		this.id = id;
	}

	public MockButton(Context context)
	{
		super(null);
	}
	
	public String toString()
	{
		//System.out.println(this.getClass().getName() + ": " +  id);
		return this.getClass().getName() + ": " +  id;
	}
	//@Le
	public void setOnClickListener(OnClickListener l)
	{
		mOnClickListener = l;
	}
	//#Le 
    
    //@yqq
    public void setButtonTextValue(int value) {
        buttonTextValue = value;
    }
    
    @Override
    public String getText() {
        return Integer.toString(buttonTextValue);
    }
    //#yqq
}
