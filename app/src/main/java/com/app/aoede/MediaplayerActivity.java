package com.app.aoede;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class MediaplayerActivity extends AppCompatActivity {
    BlurView blurView;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);

        //Set Blur
        blurView = findViewById(R.id.blurViewMediaplayer);
        blurBackground();

        seekBar = findViewById(R.id.progressBar);

    }


    //BLUR BACKGROUND
    private void blurBackground() {
        float radius = 21f;

        View decorView = getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);


        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView, new RenderScriptBlur(this))
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius)
                .setBlurEnabled(true);

    }

    //return to previous activity
    public void returnToPrevious(View view) {
        finish();
    }
}