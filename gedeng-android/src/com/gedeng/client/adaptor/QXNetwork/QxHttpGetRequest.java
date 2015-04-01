package com.gedeng.client.adaptor.QXNetwork;

import com.gedeng.client.GDClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;

/**
 * Created by yuanchun on 7/12/14.
 */
public class QxHttpGetRequest extends HttpGet {
    private String api;
    private JSONObject param;

    public QxHttpGetRequest(String api_, JSONObject param_){
        super(QxHttpUtil.buildUrl(api_, param_));
        this.api = api_;
        this.param = param_;
        this.addHeader("accept", "application/json");
        this.addHeader("GdApiVersion", String.valueOf(GDClient.version));
        this.setHeader("Content-Type", "application/json;charset=UTF-8");
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public JSONObject getParam() {
        return param;
    }

    public void setParam(JSONObject param) {
        this.param = param;
    }
}
