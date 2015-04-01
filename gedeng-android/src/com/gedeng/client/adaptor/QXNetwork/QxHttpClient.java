package com.gedeng.client.adaptor.QXNetwork;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import java.io.IOException;
import java.util.List;

/**
 * Created by yuanchun on 7/12/14.
 */
public class QxHttpClient {
    private static final String TAG = "QxHttpClient";
    private HttpHost httpHost;
    private DefaultHttpClient httpClient;
    private QxImageCache imageCache;
    private static QxHttpClient qxHttpClient;
    private static Context context;

    public static int NETWORK_STATUS_DISABLE = -1;
    public static int NETWORK_STATUS_LIMITED = 0;
    public static int NETWORK_STATUS_UNLIMITED = 1;

    public QxHttpClient(Context context){
        this.context = context;
        httpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), QxHttpConfig.timecout);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), QxHttpConfig.so_timeout);
        httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
        //httpHost = new HttpHost(QxHttpConfig.test_hostname, QxHttpConfig.test_port, QxHttpConfig.test_scheme);
        httpHost = new HttpHost(QxHttpConfig.hostname, QxHttpConfig.port, QxHttpConfig.scheme);

        imageCache = new QxImageCache(context);
    }

    public static QxHttpClient getInstance(){
        if(qxHttpClient == null){
            qxHttpClient = new QxHttpClient(context);
        }
        return qxHttpClient;
    }

    public QxImageCache getImageCache(){
        return imageCache;
    }

    public String getCsrfToken(){
        BasicCookieStore cookieStore = (BasicCookieStore) httpClient.getCookieStore();
        if (cookieStore == null)
            return null;
        List<Cookie> cookies = cookieStore.getCookies();
        if (cookies == null)
            return null;
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("csrftoken"))
                return cookie.getValue();
        }

        return null;
    }

    /*
     * initialize the QxHttpClient
     * call this while booting the app
     *
     */
    public static void init(Context context){
        qxHttpClient = new QxHttpClient(context);
    }

    /*
     * send httpget request
     */
    public static QxHttpResponse get(QxHttpGetRequest request){
        QxHttpResponse response = null;

        try{
            response = new QxHttpResponse(
                    getInstance().httpClient.execute(getInstance().httpHost, request));
        }
        catch (ClientProtocolException cpe){
            QxHttpUtil.log(cpe.getMessage());
        }
        catch (IOException ioe){
            QxHttpUtil.log(ioe.getMessage());
        }
        return response;
    }

    /*
     * send httppost request
     */
    public static QxHttpResponse post(QxHttpPostRequest request){
        QxHttpResponse response = null;
        try{
            response = new QxHttpResponse(
                    getInstance().httpClient.execute(getInstance().httpHost, request));
        }
        catch (ClientProtocolException cpe){
            QxHttpUtil.log(cpe.getMessage());
        }
        catch (IOException ioe){
            QxHttpUtil.log(ioe.getMessage());
        }
        return response;
    }

    /*
     * get image by url
     */
    public static Bitmap getImageByUrl(String url){
        return getImageByUrl(url, true);
    }
    public static Bitmap getImageByUrl(String url, boolean allowCacheAccess){
        if (url == null || "".equals(url) || "null".equals(url))
            return null;
        Bitmap bitmap = null;
        HttpGet request = new HttpGet(url);
        HttpResponse response;

        // check cache first
        if (allowCacheAccess){
            bitmap = getInstance().imageCache.getBitmapFromCache(url);
            if (bitmap != null){
                return bitmap;
            }
        }

        try{
            response = getInstance().httpClient.execute(request);
            bitmap = QxHttpUtil.getImageFromResponse(response);
            getInstance().imageCache.addBitmapToCache(url, bitmap);
        }
        catch (ClientProtocolException cpe){
            QxHttpUtil.log(cpe.getMessage());
        }
        catch (IOException ioe){
            QxHttpUtil.log(ioe.getMessage());
        }
        return bitmap;
    }

    /*
     * get current network connection
     *
     */
    public static int getNetworkStatus(){
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo == null) {
                return QxHttpClient.NETWORK_STATUS_DISABLE;
            }
            else {
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return QxHttpClient.NETWORK_STATUS_LIMITED;
                }
                else
                    return QxHttpClient.NETWORK_STATUS_UNLIMITED;
            }
        }
        catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return QxHttpClient.NETWORK_STATUS_DISABLE;
        }
    }

    public static Context getHttpClientContext(){
        return context;
    }
}
