package com.nitesh.movie360;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;


import com.google.android.exoplayer2.ExoPlayerFactory;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;

import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ContentPlayer extends AppCompatActivity {



    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;
    private String videolink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        getSupportActionBar().hide();



        setContentView(R.layout.activity_content_player);


        //setFullScreen();



        Intent i = getIntent();

        videolink = i.getExtras().getString("detailvideo");


        initExoPlayer();




    }

    private void setFullScreen() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



    }

    private void initExoPlayer() {

        playerView = findViewById(R.id.contentexoplayer);

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this);

        playerView.setPlayer(simpleExoPlayer);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "appname"));



        MediaSource videosource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(videolink));


        simpleExoPlayer.prepare(videosource);

        simpleExoPlayer.setPlayWhenReady(true);




    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        simpleExoPlayer.release();
    }




}
