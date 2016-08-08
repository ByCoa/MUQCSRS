package com.example.elviscoa.muqrsrs.Class;

import android.content.Context;
import android.util.Log;

import com.example.elviscoa.muqrsrs.Library.AppRestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by soluciones on 8/2/2016.
 */
public class OCRService {
    private static final String APIKEY = "a940a58b7488957";
    private static final Boolean OVERLAYREQUIRED = false;
    private static final String LANGUAGE = "eng";
    private static final String ENDPOINT = "https://api.ocr.space/parse/image";
    private String Cone;
    private String WeightFactor;
    private Integer Arc;

    public void callOCRAPI (final Context context,File myURI){
        RequestParams params = new RequestParams();
        params.put("apikey", APIKEY);
        params.put("isOverlayRequired", OVERLAYREQUIRED);
        try {
            params.put("file", myURI);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        params.put("language", LANGUAGE);
        AppRestClient.post(ENDPOINT, params, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("response", new String(responseBody));
                String response = new String(responseBody);
                String splinter[] = response.split("\"ParsedText\":\"");
                splinter = splinter[1].split("\",\"ErrorMessage\"");
                Log.i("response", splinter[0]);
                splinter = splinter[0].split("\\Wr\\Wn");
                Log.i("response", String.valueOf(splinter.length));
                setArc(0);
                setWeightFactor("");
                setCone("");
                Arc =0;
                for (int i=0; i<splinter.length;i++){
                    if (splinter[i].startsWith("Campo")){
                        Arc=Arc+1;
                    }
                }

                for (int i=0; i<splinter.length;i++){
                    if (splinter[i].startsWith("Factor")){
                        for (int j=0;j<Arc;j++){
                            Log.i("Factor", splinter[i + j + 1]);
                            if (splinter[i+j+1].startsWith("\\d"))
                                WeightFactor = WeightFactor+","+splinter[i+j+1];
                            else
                                WeightFactor = WeightFactor+",1.000";
                        }
                        break;
                    }
                }

                for (int i=0; i<splinter.length;i++){
                    if (splinter[i].startsWith("Cone")){
                        for (int j=0;j<Arc;j++){
                            Log.i("Cone", splinter[i + j + 1]);
                            if (splinter[i+j+1].startsWith("\\d"))
                                Cone = Cone+","+splinter[i+j+1];
                            else
                                Cone = Cone+",5";
                        }
                        break;
                    }

                }



            }
                @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("Status code", String.valueOf(statusCode));
            }
        });


    }

    public void setArc (Integer i){
        this.Arc=i;
    }
    public void setCone(String i){
        this.Cone=i;
    }
    public void setWeightFactor(String i){
        this.WeightFactor=i;
    }
    public Integer getArc() {
        return Arc;
    }

    public String getWeightFactor() {
        return WeightFactor;
    }

    public String getCone() {
        return Cone;
    }
}
