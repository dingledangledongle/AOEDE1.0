package com.app.aoede.ui.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.aoede.R;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Track;

public class PlaylistSelectedActivity extends AppCompatActivity {
    ArrayList<Track> songs = new ArrayList<>();
    PlaylistSelectedAdapter playlistSelectedAdapter;
    RecyclerView psRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_selected);
        Bundle songData = getIntent().getExtras();
        songs = songData.getParcelableArrayList("playlistSongs");

        psRecycler = findViewById(R.id.playlistSelectedRecycler);
        playlistSelectedAdapter = new PlaylistSelectedAdapter(songs);
        psRecycler.setAdapter(playlistSelectedAdapter);
        psRecycler.setLayoutManager(new LinearLayoutManager(this));



    }
}