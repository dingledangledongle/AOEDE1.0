package com.app.aoede.ui.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.aoede.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Track;

public class PlaylistActivity extends AppCompatActivity {
    RecyclerView playlistRecycler;
    PlaylistAdapter playlistAdapter;

    ImageView createPlaylist;
    ArrayList<Track> songList = new ArrayList<>();
    SharedPreferences songSaved;
    SharedPreferences playlistSaved;
    ArrayList<ArrayList<Track>> playlists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        songSaved = getSharedPreferences("libraryList", MODE_PRIVATE);
        String library = songSaved.getString("library","");
        if(!library.equals("")){
            TypeToken<ArrayList<Track>> token = new TypeToken<ArrayList<Track>>(){};
            Gson gson = new Gson();
            songList = gson.fromJson(library, token.getType());
        }

        playlistSaved = getSharedPreferences("libraryList", MODE_PRIVATE);
        String playlistLibrary = playlistSaved.getString("playlist","");
        if(!playlistLibrary.equals("")){
            TypeToken<ArrayList<ArrayList<Track>>> token =
                    new TypeToken<ArrayList<ArrayList<Track>>>(){};
            Gson gson = new Gson();
            playlists = gson.fromJson(playlistLibrary,token.getType());
        }


        createPlaylist = findViewById(R.id.createPlaylist);
        playlistRecycler = findViewById(R.id.playlistRecycler);
        playlistAdapter = new PlaylistAdapter(playlists);
        playlistRecycler.setAdapter(playlistAdapter);
        playlistRecycler.setLayoutManager(new LinearLayoutManager(this));

        createPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaylistActivity.this,CreatePlaylistActivity.class);
                intent.putExtra("song", songList);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        playlistSaved = getSharedPreferences("libraryList", MODE_PRIVATE);
        String playlistLibrary = playlistSaved.getString("playlist","");
        if(!playlistLibrary.equals("")){
            TypeToken<ArrayList<ArrayList<Track>>> token =
                    new TypeToken<ArrayList<ArrayList<Track>>>(){};
            Gson gson = new Gson();
            playlists = gson.fromJson(playlistLibrary,token.getType());
        }
        playlistAdapter = new PlaylistAdapter(playlists);
        playlistRecycler.setAdapter(playlistAdapter);
        playlistRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

}