package com.example.elviscoa.muqrsrs.Class;

import android.content.Context;
import android.util.Log;

import com.example.elviscoa.muqrsrs.Library.AppRestClient;
import com.example.elviscoa.muqrsrs.Library.SyncAppRestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;


/**
 * Created by soluciones on 8/2/2016.
 */
public class OCRService {
    private static final String APIKEY = "a940a58b7488957";
    private static final Boolean OVERLAYREQUIRED = false;
    private static final String LANGUAGE = "spa";
    private static final String ENDPOINT = "https://api.ocr.space/parse/image";

    public void callOCRAPI (final Context context,String myURI){
        RequestParams params = new RequestParams();
        params.put("apikey", APIKEY);
        params.put("isOverlayRequired", OVERLAYREQUIRED);
        params.put("file", myURI);
        params.put("language", LANGUAGE);
        AppRestClient.post(ENDPOINT, params, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("Status code", String.valueOf(statusCode));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("Status code", String.valueOf(statusCode));
            }
        });


    }
}
