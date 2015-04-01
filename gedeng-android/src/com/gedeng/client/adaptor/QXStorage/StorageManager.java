package com.gedeng.client.adaptor.QXStorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.WindowId;
import com.gedeng.client.GDClient;
import com.gedeng.client.adaptor.QXNetwork.Messages.QxMessage;
import com.gedeng.ui.CommentMessageListItem;
import com.gedeng.ui.FollowMessageListItem;
import com.gedeng.ui.LikeMessageListItem;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by yuanchun on 7/17/14.
 * Package: bangyoung-android
 */
public class StorageManager {
    private static String TAG = "StorageManager";
    private static Context context = null;
    private static SharedPreferences sharedPreferences;
    private static boolean loaded = false;

    public static void init(Context context) {
        try {
            StorageManager.context = context;
            sharedPreferences = context.getSharedPreferences("configs", Context.MODE_PRIVATE);
            loaded = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void putString(String key, String value) {
        try {
            if(!loaded) return;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void putInt(String key, int value) {
        try {
            if (!loaded) return;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key, value);
            editor.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getString(String key) {
        try {
            if(!loaded) return null;
            return sharedPreferences.getString(key, null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getInt(String key) {
        try {
            if(!loaded) return -1;
            return sharedPreferences.getInt(key, -1);
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void addMessage(QxMessage message) {
        try {
            int uid = GDClient.getInstance().getCurrentUser().getId();
            String messageKey = "messages" + uid;
            ArrayList<QxMessage> messages = getMessages(-1);
            messages.add(0, message);
            FileOutputStream fos = context.openFileOutput(messageKey, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(messages);
            oos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }

    public static ArrayList<QxMessage> getMessages(int type) {
        ArrayList<QxMessage> messages = new ArrayList<QxMessage>();
        try {
            int uid = GDClient.getInstance().getCurrentUser().getId();
            String messageKey = "messages" + uid;
            FileInputStream fis = context.openFileInput(messageKey);
            ObjectInputStream ois = new ObjectInputStream(fis);

            ArrayList<QxMessage> allMessages = (ArrayList<QxMessage>) ois.readObject();
            ois.close();
            if(type == -1) {
                messages = allMessages;
            }
            else {
                for (QxMessage message : allMessages) {
                    if (message.type == type) {
                        messages.add(message);
                    }
                }
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

}
