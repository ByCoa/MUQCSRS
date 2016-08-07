package com.example.elviscoa.muqrsrs.Library;

import android.util.Log;
import android.util.Pair;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soluciones on 8/2/2016.
 */
public class AppRestClient {
    private static final String HOST = "";

    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

    public void setBASICAUTH (String username, String password){
        client.setBasicAuth(username, password);
    }

    public static void post(String url, RequestParams params, List<Pair<String, String>> headers, AsyncHttpResponseHandler responseHandler) {
        if(headers != null){
            for(int i = 0; i < headers.size(); i++){
                client.addHeader(headers.get(i).first, headers.get(i).second);
                Log.i("headers", String.valueOf(headers.get(i).first));
            }
        }
        client.setTimeout(1000*10);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return HOST + relativeUrl;
    }
}

