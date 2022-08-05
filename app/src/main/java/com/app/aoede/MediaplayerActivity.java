package com.app.aoede;

import static com.app.aoede.MainActivity.playerBtn;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.app.aoede.ui.search.SearchAdapter;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import kaaes.spotify.webapi.android.models.Track;

public class MediaplayerActivity extends AppCompatActivity {
    BlurView blurView;
    static SeekBar seekBar;
    static Handler handler;
    ImageView background;
    ImageView albumArt;
    ImageView playBtn;
    TextView songTitle;
    TextView songArtist;
    Track currentSong = SearchAdapter.currentSong;

    public static MediaPlayer player = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
        //Set Blur
        blurView = findViewById(R.id.blurViewMediaplayer);
        blurBackground();

        seekBar = findViewById(R.id.progressBar);
        background = findViewById(R.id.imageBackground);
        albumArt = findViewById(R.id.imageAlbumArt);
        songTitle = findViewById(R.id.txtSongTitle);
        songArtist = findViewById(R.id.txtArtist);
        playBtn = findViewById(R.id.btnPlay);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(player != null && player.isPlaying()){
                    player.seekTo(seekBar.getProgress());
                }
            }
        });

        if (currentSong != null){
            String title = currentSong.name;
            String imgUrl = currentSong.album.images.get(0).url;
            String artist = currentSong.artists.get(0).name;

            songTitle.setText(title);
            songArtist.setText(artist);
            Picasso.get().load(imgUrl).into(albumArt);
            Picasso.get().load(imgUrl).into(background);

        }

        if (player.isPlaying()){
            playBtn.setImageResource(R.drawable.pause);
        }else{
            playBtn.setImageResource(R.drawable.play_arrow);
        }

    }


    //BLUR BACKGROUND
    private void blurBackground() {
        float radius = 25f;

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


    //MUSIC FUNCTIONS
    public static void playSong(String url){
        try{
            player.reset();
            player.setDataSource(url);
            player.prepare();
            player.start();

            stopWhenMusicEnds();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void playOrPause(){
        if(player.isPlaying()){
            player.pause();
            playerBtn.setImageResource(R.drawable.play_arrow);
        }else{
            player.start();
            playerBtn.setImageResource(R.drawable.pause);
        }
    }

    public static void stopWhenMusicEnds(){
        player.setOnCompletionListener(mp -> playerBtn.setImageResource(R.drawable.play_arrow));
    }

    public void playPause(View view) {
        if(player.isPlaying()){
            player.pause();
            playBtn.setImageResource(R.drawable.play_arrow);
        }else{
            player.start();
            playBtn.setImageResource(R.drawable.pause);
        }
    }

    //PROGRESS BAR
    Runnable p_bar = new Runnable() {
        @Override
        public void run() {
            if (player != null && player.isPlaying()){
                seekBar.setProgress(player.getCurrentPosition());
            }
            handler.postDelayed(this,10);
        }
    };
}