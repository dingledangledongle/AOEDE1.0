package com.app.aoede;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.google.android.material.imageview.ShapeableImageView;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class MediaplayerActivity extends AppCompatActivity {
    BlurView blurView;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);
        
        blurView = findViewById(R.id.blurView);
        blurBackground();

        seekBar = findViewById(R.id.progressBar);




    }

    private void blurBackground() {
        float radius = 21f;

        View decorView = getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);


        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView, new RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setBlurEnabled(true);

    }

    public void returnToPrevious(View view) {
        finish();
    }
}