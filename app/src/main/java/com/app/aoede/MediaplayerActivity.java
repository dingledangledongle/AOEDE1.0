package com.app.aoede;


import static com.app.aoede.MainActivity.playerArt;
import static com.app.aoede.MainActivity.playerBtn;
import static com.app.aoede.MainActivity.playerTitle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aoede.ui.search.SearchAdapter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import kaaes.spotify.webapi.android.models.Track;

public class MediaplayerActivity extends AppCompatActivity {
    BlurView blurView;
    static SeekBar seekBar;
    Handler handler = new Handler(Looper.myLooper());
    static ImageView background;
    static ImageView albumArt;
    static ImageView playBtn;
    static TextView songTitle;
    static TextView songArtist;
    Track currentSong = SearchAdapter.currentSong;
    static int currentIndex;
    public static MediaPlayer player = new MediaPlayer();

    public static ArrayList<Track> songQueue = new ArrayList<>();
    ArrayList<Track> originalQueue = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //Set Blur
        blurView = findViewById(R.id.blurViewMediaplayer);
        blurBackground();

        //finding all ui elements
        seekBar = findViewById(R.id.progressBar);
        background = findViewById(R.id.imageBackground);
        albumArt = findViewById(R.id.imageAlbumArt);
        songTitle = findViewById(R.id.txtSongTitle);
        songArtist = findViewById(R.id.txtArtist);
        playBtn = findViewById(R.id.btnPlay);

        currentIndex = songQueue.indexOf(currentSong);

        //seekbar dragging
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(p_bar);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(player != null && player.isPlaying()){
                    player.seekTo(seekBar.getProgress());
                    handler.postDelayed(p_bar,10);
                }
            }
        });

        //if songs is playing, displayInfo and seekbar
        if (currentSong != null){
            displaySongInfo();

            seekBar.setMax(player.getDuration());
            handler.removeCallbacks(p_bar);
            handler.postDelayed(p_bar,10);
        }
        //change play or pause image accordingly
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

    //goes to next song when music ends
    public static void stopWhenMusicEnds(){
        player.setOnCompletionListener(mp -> {
            if(songQueue.size() == 1){
                playerBtn.setImageResource(R.drawable.play_arrow);
                playBtn.setImageResource(R.drawable.play_arrow);
                seekBar.setProgress(0);
                songQueue.remove(0);
            }else if (songQueue.size() > 1){
                currentIndex += 1;
                String url = songQueue.get(currentIndex).preview_url;
                SearchAdapter.currentSong = songQueue.get(currentIndex);
                Log.d("spotAuthent", currentIndex + " " + songQueue.get(currentIndex).name);
                playSong(url);
                displaySongInfo();
            }else{
                seekBar.setProgress(0);
                playerBtn.setImageResource(R.drawable.play_arrow);
                playBtn.setImageResource(R.drawable.play_arrow);

            }

        });
    }

    public static void addToQueue(Track song){
        songQueue.add(0,song);
    }

    //display song info both here and mediaPlayer in mainActivity
    public static void displaySongInfo(){
        String title = SearchAdapter.currentSong.name;
        String imgUrl = SearchAdapter.currentSong.album.images.get(0).url;
        String artist = SearchAdapter.currentSong.artists.get(0).name;

        songTitle.setText(title);
        songArtist.setText(artist);
        playerTitle.setText(title);
        Picasso.get().load(imgUrl).into(playerArt);
        Picasso.get().load(imgUrl).into(albumArt);
        Picasso.get().load(imgUrl).into(background);

    }


    //play or pause
    public void playPause(View view) {
        if(player.isPlaying()){
            player.pause();
            playerBtn.setImageResource(R.drawable.play_arrow);
            playBtn.setImageResource(R.drawable.play_arrow);
        }else{
            player.start();
            handler.removeCallbacks(p_bar);
            handler.postDelayed(p_bar,10);
            playerBtn.setImageResource(R.drawable.pause);
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

    //removes the callbacks
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(p_bar);
    }

    public void previousSong(View view) {
        if(currentIndex == 0){
            String url = songQueue.get(currentIndex).preview_url;
            SearchAdapter.currentSong = songQueue.get(currentIndex);
            Log.d("spotAuthent", currentIndex + " " + songQueue.get(currentIndex).name);
            playSong(url);
            displaySongInfo();
        }else{
            currentIndex -= 1;
            String url = songQueue.get(currentIndex).preview_url;
            SearchAdapter.currentSong = songQueue.get(currentIndex);
            Log.d("spotAuthent", currentIndex + " " + songQueue.get(currentIndex).name);
            playSong(url);
            displaySongInfo();
        }

    }

    public void nextSong(View view) {
        if(currentIndex == (songQueue.size() - 1)){
            String url = songQueue.get(currentIndex).preview_url;
            SearchAdapter.currentSong = songQueue.get(currentIndex);
            Log.d("spotAuthent", currentIndex + " " + songQueue.get(currentIndex).name);
            playSong(url);
            displaySongInfo();
        }else{
            currentIndex += 1;
            String url = songQueue.get(currentIndex).preview_url;
            SearchAdapter.currentSong = songQueue.get(currentIndex);
            Log.d("spotAuthent", currentIndex + " " + songQueue.get(currentIndex).name);
            playSong(url);
            displaySongInfo();
        }
    }

}