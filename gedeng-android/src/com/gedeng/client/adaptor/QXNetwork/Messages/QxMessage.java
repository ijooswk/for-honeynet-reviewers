package com.gedeng.client.adaptor.QXNetwork.Messages;

import com.gedeng.client.adaptor.QXStorage.StorageManager;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.GetPostByIdParam;
import com.gedeng.client.connector.params.GetUserByIdParam;
import com.gedeng.client.entity.Post;
import com.gedeng.client.entity.User;
import com.gedeng.ui.FollowMessageListItem;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by yuanchun on 7/22/14.
 * Package: gedeng-android
 */
public class QxMessage implements Serializable {
    private static int contentLength = 20;
    public static int MESSAGE_TYPE_NOTIFICATION = 1;
    public static int MESSAGE_TYPE_VOTED = 2;
    public static int MESSAGE_TYPE_REPLIED = 3;
    public int type;
    public int actor_uid;
    public int post_id;
    public int comment_id;

    public DateTime time;

    public String defaultIconUrl = "";
    public String defaultActorName = "";
    public String defaultText = "";


    public QxMessage(JSONObject jsonObject) {
        type = -1;
        actor_uid = -1;
        post_id = -1;
        comment_id = -1;

        time = DateTime.now();

        JSONObject jsonContent = null;
        try {
            type = jsonObject.getInt("type");
            jsonContent = jsonObject.getJSONObject("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonContent == null) {
            return;
        }

        // Message: user is followed
        if(type == 1) {
            type = MESSAGE_TYPE_NOTIFICATION;
            try {
                actor_uid = jsonContent.getInt("actor_uid");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Message: post is voted
        else if (type == 2) {
            type = MESSAGE_TYPE_VOTED;
            try {
                actor_uid = jsonContent.getInt("actor_uid");
                post_id = jsonContent.getInt("post_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Message: post is replied or comment is replied
        else if (type == 3 || type == 4) {
            type = MESSAGE_TYPE_REPLIED;
            try {
                actor_uid = jsonContent.getInt("actor_uid");
                post_id = jsonContent.getInt("post_id");
                comment_id = jsonContent.getInt("comment_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Message: notification from official
        else if (type == 5) {
            type = MESSAGE_TYPE_NOTIFICATION;
            try {
                actor_uid = jsonContent.getInt("actor_uid");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public QxMessage(){
        type = -1;
        actor_uid = -1;
        post_id = -1;
        comment_id = -1;
        time = DateTime.now();
    }
}
