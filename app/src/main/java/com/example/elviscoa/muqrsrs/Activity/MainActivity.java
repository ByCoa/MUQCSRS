package com.example.elviscoa.muqrsrs.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.elviscoa.muqrsrs.Class.IOCRCallBack;
import com.example.elviscoa.muqrsrs.Class.OCRAsyncTask;
import com.example.elviscoa.muqrsrs.Library.DownloadFile;
import com.example.elviscoa.muqrsrs.R;

/**
 * Created by elvis on 10/07/16.
 */
public class MainActivity extends AppCompatActivity implements IOCRCallBack {

    private String mAPiKey = ""; //TODO Add your own Registered API key
    private boolean isOverlayRequired;
    private String mImageUrl;
    private String mLanguage;
    private TextView mTxtResult;
    private IOCRCallBack mIOCRCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadFile.Download();

    }

    private void init() {
        mTxtResult = (TextView) findViewById(R.id.actual_result);
        TextView btnCallAPI = (TextView) findViewById(R.id.btn_call_api);

        if (btnCallAPI != null) {
            btnCallAPI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }
    }

    @Override
    public void getOCRCallBackResult(String response) {
        mTxtResult.setText(response);
    }
}