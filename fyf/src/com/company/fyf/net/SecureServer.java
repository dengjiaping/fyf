package com.company.fyf.net;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liyaxing on 2016/8/22.
 */
public class SecureServer extends AbstractHttpServer {

    public SecureServer() {
        super();
    }

    public SecureServer(Context context) {
        super(context);
    }

    @Override
    protected String getModule() {
        // TODO Auto-generated method stub
        return "secure";
    }

    public void seccode(final CallBack<String> back){
        addParam("act", "seccode");

        addParam("code_len", "4");
        addParam("font_size", "12");
        addParam("width", "290");
        addParam("height", "140");
        doGet(new FilterAjaxCallBack(back) {
            @Override
            public void onSuccess(String data) {
                try {
                    String seccode = new JSONObject(data).getString("seccode");
                    back.onSuccess(seccode);
                } catch (JSONException e) {
                    e.printStackTrace();
                    showAnalyticalException(back);
                }
            }
        });
    }
}
