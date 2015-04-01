package de.ecspride;

import android.content.Intent;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import java.io.*;

import mymock.mockView.*;


public class ApfGenerator {

	public static void main (String [] args)
	{
		android.app.ContextImpl context = new android.app.ContextImpl();
		de.ecspride.PrivateDateLeakage act = new de.ecspride.PrivateDateLeakage();

		act.attachBaseContext(context);
		act.apf_gen = true;
        //@YQQ
        //modified the next line for my path
        //#YQQ
		//act.viewGen = new ApfViewGenerator("/Volumes/NGUYENLE/NGUYENLE/SUTD_project/DroidPF/apps/DroidBench/AndroidSpecific_PrivateDataLeak1/");
		act.viewGen = new ApfViewGenerator("/Users/yuanchun/project/callbackdroid/code/callbackdroid/android-environment/apps/DroidBench/AndroidSpecific_PrivateDataLeak1/");

		/*Resources res =	context.getResources();
		if(res == null)
			System.out.println("res is null");
		else
			System.out.println("res is not null");*/
		
		act.onCreate(null);
//		act.onRestart();


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
