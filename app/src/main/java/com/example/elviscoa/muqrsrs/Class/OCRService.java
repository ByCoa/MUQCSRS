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
    private String avgDepth;
    private String muTps;
    private Integer Arc;
    private boolean[] chosen= new boolean[4];

    public OCRService (){
        setCone("");
        setWeightFactor("");
        setMuTps("");
        setAvgDepth("");
    }

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


                if (chosen[0]) {
                    for (int j = 0; j < Arc; j++) {
                        if (splinter[j]!=null) {
                            Log.i("Cone", splinter[j]);
                            String[] splinter2 = splinter[j].split(" ");
                            splinter2[0]= splinter2[0].replace("B", "8");
                            splinter2[0] =splinter2[0].replace("s","5");
                            splinter2[0] =splinter2[0].replace("S","5");
                            if (splinter2[0].startsWith("5"))
                                Cone = Cone + "5,";
                            else if (splinter2[0] != null && Integer.parseInt(splinter2[0].substring(0,2)) < 31)
                                Cone = Cone + "" + Integer.parseInt(splinter[2].substring(0,2)) + ",";
                        }else
                            Cone = Cone + "5,";
                    }
                }

                if (chosen[1]) {
                    String splinter2[];
                    for (int j = 0; j < Arc; j++) {
                        if(splinter[j]!=null) {
                            Log.i("Depth", splinter[j]);
                            splinter2 = splinter[j].split("\\.");
                            splinter2[0]= splinter2[0].replace("B", "8");
                            splinter2[0] =splinter2[0].replace("s","5");
                            splinter2[0] =splinter2[0].replace("S","5");
                            if (Double.parseDouble(splinter2[0]) >= 20.0)
                                avgDepth = avgDepth + "" + Double.parseDouble(splinter2[0]) + ",";
                            else
                                avgDepth = avgDepth + "20.0,";
                        }else
                            avgDepth = avgDepth + "20.0,";
                    }
                }

                if (chosen[2]) {
                    for (int j = 0; j < Arc; j++) {
                        Log.i("Weight", splinter[j]);
                        splinter[j] =splinter[j].replace("B","8");
                        splinter[j] =splinter[j].replace("s","5");
                        splinter[j] =splinter[j].replace("S","5");
                        if (splinter[j]!=null)
                            WeightFactor = WeightFactor + "" + splinter[j].substring(0,5) +",";
                        else
                            WeightFactor = WeightFactor + "1.000,";
                    }
                }

                if (chosen[3]) {
                    for (int j = 0; j < Arc; j++) {
                        Log.i("MuTps", splinter[j]);
                        splinter[j] =splinter[j].replace("B","8");
                        splinter[j] =splinter[j].replace("s","5");
                        splinter[j] =splinter[j].replace("S","5");
                        if (splinter[j]!=null)
                            muTps = muTps+ "" + splinter[j].substring(0,3) + ",";
                        else
                            muTps = muTps+ "1,";
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
    public void setAvgDepth (String i){
        this.avgDepth=i;
    }
    public void setMuTps(String i){
        this.muTps=i;
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

    public String getMuTps() {
        return muTps;
    }

    public String getAvgDepth() {
        return avgDepth;
    }

    public String getCone() {
        return Cone;
    }

    public boolean[] getChosen() {
        return chosen;
    }

    public void setChosen(boolean[] chosen) {
        this.chosen = chosen;
    }
}
