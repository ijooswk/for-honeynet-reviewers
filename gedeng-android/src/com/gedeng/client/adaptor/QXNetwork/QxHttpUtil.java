package com.gedeng.client.adaptor.QXNetwork;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by yuanchun on 7/12/14.
 */
public class QxHttpUtil {
    // response status code
    public static final int RESPONSE_SUCCESS = 0;
    public static final int PHONE_NUMBER_EXISTS = 20104;

    private static final String TAG = "QxHttpClient";
    private static final String[] UrlSeparator = {"?", "&"};

    public static String buildUrl(String api, JSONObject params) {
        String url = api;
        if (params != null)  {
            Iterator it = params.keys();
            int para_count = 0;
            while(it.hasNext()){
                String key = (String) it.next();
                String value = null;
                try {
                    value = String.valueOf( params.get(key) );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                url += UrlSeparator[(para_count > 0) ? 1:0];
                url += key + "=" + value;
                para_count++;
            }
        }
        return url;
    }

    public static void log(String message){
        Log.i(TAG, String.valueOf(message));
    }

    public static Bitmap getImageFromResponse(HttpResponse response) throws IOException {
        InputStream is = response.getEntity().getContent();
        Bitmap ret =  BitmapFactory.decodeStream(is);
        is.close();
        return ret;
    }


}
