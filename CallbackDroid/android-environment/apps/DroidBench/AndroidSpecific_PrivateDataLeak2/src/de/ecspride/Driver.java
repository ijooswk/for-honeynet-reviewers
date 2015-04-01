package de.ecspride;

import android.content.Intent;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import java.io.*;

import android.view.View;
import java.util.Hashtable;

import mymock.mockView.*;


public class Driver {
	public static void checkTaint (String str)
	{
		String kkk = str;
	}

	public static void main (String [] args)
	{
		android.app.ContextImpl context = new android.app.ContextImpl();
		final de.ecspride.PrivateDataLeak2 act = new de.ecspride.PrivateDataLeak2();

		act.attachBaseContext(context);
		act.viewGen = new ApfViewGenerator("/Volumes/NGUYENLE/NGUYENLE/SUTD_project/DroidPF/apps/DroidBench/AndroidSpecific_PrivateDataLeak2/");

		/*Resources res =	context.getResources();
		if(res == null)
			System.out.println("res is null");
		else
			System.out.println("res is not null");*/

		Hashtable<String, View> id2view = new Hashtable<String, View>();
		MockEditText myet0 = new MockEditText("0x7f070000", null);
		id2view.put(myet0.id, myet0);	

		act.idViewTable = id2view;

		act.onCreate(null);
		

/*


		android.app.ContextImpl context = new android.app.ContextImpl();

		
		android.content.pm.ProviderInfo pinfo = new android.content.pm.ProviderInfo();

		com.test.stealpb.LeakIMEI cp = new com.test.stealpb.LeakIMEI();
		cp.attachInfo(context, pinfo);
		boolean test = cp.onCreate();
		assert test;
		android.database.Cursor cursor = cp.query(android.net.Uri.parse("content://com.test.stealpb.cp"), null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			checkTaint(cursor.getString(0));
		}
*/
	}
}
