package com.lynnlyc.apicaller;

import android.os.AsyncTask;

/**
 * Created by yuanchun on 1/30/15.
 * Package: apicaller
 */
public class AppAsyncTask extends AsyncTask<Object, Object, Object> {
    @Override
    public void onPreExecute(){}

    @Override
    public Object doInBackground(Object[] objects) {
        return objects[0];
    }

    @Override
    public void onPostExecute(Object result) {
        System.out.println(result);
    }
}
