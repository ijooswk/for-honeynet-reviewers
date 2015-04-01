package mymock.mockView;

import android.widget.ImageView;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Editable;
import android.view.MenuItem;

import android.view.SubMenu;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;

import com.android.internal.view.menu.MenuView.ItemView;
import com.android.internal.view.menu.MenuItemImpl; 

import java.io.*;

public class MockMenuItem extends MenuItemImpl
{
	public String id;
	public String text;
	
	public int itemId;

	public MockMenuItem(int group, int id, int categoryOrder, CharSequence title)
	{
		super(group, id, categoryOrder, title);
		this.itemId = id;
	}

	public MenuItem setIcon(int iconId)
	{
		return this;
	}
	
	public int getItemId() 
	{
		return itemId;
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
