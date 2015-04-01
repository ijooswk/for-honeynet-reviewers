package mymock.mockView;

import android.widget.ImageView;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Editable;
import android.view.MenuItem;
import android.view.Menu;

import android.view.SubMenu;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;

import com.android.internal.view.menu.MenuView.ItemView;
import com.android.internal.view.menu.MenuBuilder;

import java.io.*;
import java.util.*;

public class MockMenu extends MenuBuilder 
{
	public String id;
	public String text;

	public ArrayList<MenuItem> mItems;
	
	public MockMenu()
	{
		mItems = new ArrayList<MenuItem>();
	}

	public MenuItem add(int group, int id, int categoryOrder, CharSequence title) 
	{
		MockMenuItem item = new MockMenuItem(group, id, categoryOrder, title); 
		mItems.add(item);
		return item;
	}
	
	public void setQwertyMode(boolean isQwerty)
	{
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
