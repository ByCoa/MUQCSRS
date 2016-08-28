package com.example.elviscoa.muqrsrs.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.elviscoa.muqrsrs.R;

/**
 * Created by soluciones on 8/7/2016.
 */
public class SplashActivity extends AppCompatActivity {
    private VideoView mVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mVideoView = (VideoView) findViewById(R.id.player_video);
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.video;
        mVideoView.setVideoURI(Uri.parse(uri));

        mVideoView.requestFocus();

        mVideoView.start();

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(SplashActivity.this, GeneralDataActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}