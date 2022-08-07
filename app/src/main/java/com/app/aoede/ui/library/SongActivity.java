package com.app.aoede.ui.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.app.aoede.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Track;

public class SongActivity extends AppCompatActivity {
    RecyclerView songRecycler;
    SongAdapter songAdapter;
    ArrayList<Track> songList = new ArrayList<>();
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        sp = getSharedPreferences("libraryList",MODE_PRIVATE);
        String library = sp.getString("library","");
        if(!library.equals("")){
            TypeToken<ArrayList<Track>> token = new TypeToken<ArrayList<Track>>(){};
            Gson gson = new Gson();
            songList = gson.fromJson(library, token.getType());
        }

        songRecycler = findViewById(R.id.songRecycler);
        songAdapter = new SongAdapter(songList);
        songRecycler.setAdapter(songAdapter);
        songRecycler.setLayoutManager(new LinearLayoutManager(this));


    }
}