package com.gedeng.client.adaptor.QXNetwork;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

/**
 * Created by yuanchun on 7/12/14.
 */
public class QxHttpResponse implements Serializable {
    private int ret;
    private JSONObject body;
    public int status;
    public QxHttpResponse(HttpResponse response)  {
        ret = -1;
        body = null;

        if (response.getStatusLine().getStatusCode() != 200){
            status = response.getStatusLine().getStatusCode();
        }
        
        HttpEntity entity = response.getEntity();

        BufferedReader streamReader = null;
        try {
            streamReader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
        } catch (IOException e) {
            QxHttpUtil.log(e.getMessage());
        }

        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        if (streamReader != null) {
            try {
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);
            } catch (IOException e) {
                QxHttpUtil.log(e.getMessage());
            }
        }

        inputStr = responseStrBuilder.toString();
        try {
            JSONObject jsonRet = new JSONObject(inputStr);
            ret = jsonRet.getInt("ret");
            body = jsonRet;
        } catch (JSONException e) {
            QxHttpUtil.log(e.getMessage());
        }
    }

    public int getRet(){
        return ret;
    }

    public void setRet(int ret_){
        ret = ret_;
    }

    public JSONObject getBody(){
        return body;
    }

    public void setBody(JSONObject body_){
        body = body_;
    }

    public JSONArray getBodyArray() throws JSONException {
        return body.getJSONArray("body");
    }

    public String getBodyString() throws JSONException {
        return body.getString("body");
    }

    public JSONObject getBodyObject() throws JSONException {
        return body.getJSONObject("body");
    }

    public int getBodyInt() throws JSONException {
        return body.getInt("body");
    }
}
