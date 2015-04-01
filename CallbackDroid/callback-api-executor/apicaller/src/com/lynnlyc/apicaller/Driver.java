package com.lynnlyc.apicaller;

/**
* Created by yuanchun on 1/29/15.
* Package: apicaller
*/
public class Driver {
    public static void main(String[] args) {
        // write your code here
        android.app.ContextImpl context = new android.app.ContextImpl();
        final CallerActivity main = new CallerActivity();
        main.attachBaseContext(context);
        main.onCreate(null);
    }
}
