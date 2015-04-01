package com.gedeng.ui;

import com.gedeng.client.Storage;
import com.gedeng.client.adaptor.QXNetwork.Messages.QxMessage;
import com.gedeng.client.adaptor.QXNetwork.Messages.QxMessageReceivedListener;
import com.gedeng.client.adaptor.QXNetwork.Messages.QxPushManager;
import com.gedeng.client.adaptor.QXStorage.StorageManager;
import com.gedeng.client.connector.Callback;
import com.gedeng.client.connector.Connector;
import com.gedeng.client.connector.Response;
import com.gedeng.client.connector.params.GetPostByIdParam;
import com.gedeng.client.connector.params.GetUserByIdParam;
import com.gedeng.client.entity.Post;
import com.gedeng.client.entity.User;
import com.gedeng.ui.LikeMessageListItem;
import org.joda.time.DateTime;

/**
 * Created by yuanchun on 7/28/14.
 * Package: gedeng-android
 */
public class QxMessageReceivedListenerImpl implements QxMessageReceivedListener {
    @Override
    public void onMessageReceived(QxMessage message) {
        StorageManager.addMessage(message);
    }
}
