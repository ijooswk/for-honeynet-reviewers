package de.ecspride;

import android.content.Intent;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import java.io.*;

import android.view.View;
import java.util.Hashtable;

import mymock.mockView.*;

import gov.nasa.jpf.vm.Verify;


public class Driver {
	public static void checkTaint (String str)
	{
		String kkk = str;
	}

	public static void main (String [] args)
	{
		android.app.ContextImpl context = new android.app.ContextImpl();
		final de.ecspride.PrivateDateLeakage act = new de.ecspride.PrivateDateLeakage();

		act.attachBaseContext(context);
		//act.viewGen = new ApfViewGenerator("/home/baigd/Android/android-jvm-lib/apps/DroidBench/AndroidSpecific_PrivateDataLeak1/");

		/*Resources res =	context.getResources();
		if(res == null)
			System.out.println("res is null");
		else
			System.out.println("res is not null");*/

		Hashtable<String, View> id2view = new Hashtable<String, View>();
		MockButton mybt0 = new MockButton("0x7f070002", null);
		mybt0.setOnClickListener(new View.OnClickListener() {
			@Override
    		public void onClick(View v) {
											act.sendMessage(v);
										}
			});
		id2view.put(mybt0.id, mybt0);
		MockEditText myet1 = new MockEditText("0x7f070001", null);
		id2view.put(myet1.id, myet1);
		MockEditText myet2 = new MockEditText("0x7f070000", null);
		id2view.put(myet2.id, myet2);
		
		act.idViewTable = id2view;


/*		int ch = Verify.getInt(0, 4);
		switch (ch)
		{
			case 0: 
				act.onCreate(null);
				mybt0.performClick();
				break;
			case 1: 
				act.onCreate(null);
				act.onRestart();
				break;
			case 2: 
				act.onCreate(null);
				break;
			case 3: 
				act.onCreate(null);
				mybt0.performClick();
				act.onRestart();
				break;
			case 4: 
				act.onCreate(null);
				act.onRestart();
				mybt0.performClick();
				break;
			default: 
				break;
		} */
		act.onCreate(null);
		int ch1 = Verify.getInt(0, 1);
		System.out.println("ch1: " + ch1);
		if(ch1 == 0)
			act.onRestart();
		else
			mybt0.performClick();
		
		int ch2 = Verify.getInt(0, 1);
		System.out.println("ch2: " + ch2);
		if(ch1 == 0)
		if(ch2 == 0)	
			mybt0.performClick();
		else
			act.onRestart();
	}		

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
