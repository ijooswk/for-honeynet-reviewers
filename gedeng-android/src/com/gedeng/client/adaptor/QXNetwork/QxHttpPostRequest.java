package com.gedeng.client.adaptor.QXNetwork;

import com.gedeng.client.GDClient;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by yuanchun on 7/12/14.
 */
public class QxHttpPostRequest extends HttpPost {
    private JSONObject body;
    private String api;
    public QxHttpPostRequest(String api_, JSONObject body_){
        super(QxHttpUtil.buildUrl(api_, null));
        api = api_;
        body = body_;

        String csrftoken = QxHttpClient.getInstance().getCsrfToken();
        if(csrftoken != null){
            this.addHeader("X-CSRFToken", csrftoken);
        }

        this.addHeader("accept", "application/json");
        this.addHeader("GdApiVersion", String.valueOf(GDClient.version));
        this.addHeader("Content-Type", "application/json;charset=UTF-8");


        if(body != null) {
            HttpEntity entity = null;
            try {
                entity = new StringEntity(body.toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                QxHttpUtil.log(e.getMessage());
            }

            this.setEntity(entity);
        }
    }

    public void setBody(JSONObject body_){
        body = body_;
    }

    public JSONObject getBody(){
        return body;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

}
