package com.gedeng.client.adaptor.QXNetwork.Messages;

import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import com.gedeng.client.adaptor.QXNetwork.QxHttpClient;
import com.gedeng.ui.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by yuanchun on 7/21/14.
 * Package: gedeng-android
 */
public class QxPushManager {
    private static String TAG = "PushManager";
    private static QxMessageReceivedListener messageReceivedListener = null;

    public static void bind(final int uid) {
        Log.i(TAG, "binding user to JPush" + uid);
        JPushInterface.setAlias(QxHttpClient.getHttpClientContext(), String.valueOf(uid),
                new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> strings) {
                        if (i == 0) {
                            QxPushManager.loadMessages(uid);
                            QxPushManager.setMessageReceivedListener(new QxMessageReceivedListenerImpl());
                        }
                        if (i != 0)
                            Log.i(TAG, "fail to bind: " + i);   //fail to bind
                    }
                });
    }

    public static void unbind() {
        JPushInterface.setAlias(QxHttpClient.getHttpClientContext(), "",
                new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> strings) {
                        if (i != 0)
                            Log.i(TAG, "fail to unbind: " + i); //fail to unbind
                    }
                });
    }

    public static void setMessageReceivedListener(QxMessageReceivedListener newMessageReceivedListener){
        messageReceivedListener = newMessageReceivedListener;
    }

    public static void notifyMessageReceived(String jsonString) {
        JSONObject jsonMessage = null;
        try {
            jsonMessage = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonMessage == null) {
            Log.e(TAG, "Message not found");
            return;
        }

        QxMessage qxMessage = new QxMessage(jsonMessage);

        if(messageReceivedListener == null) {
            Log.i(TAG, "No listener registered");
        }
        else {
            messageReceivedListener.onMessageReceived(qxMessage);
        }
    }

    public static void loadMessages(int uid) {

    }


}
