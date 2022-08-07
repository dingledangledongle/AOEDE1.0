package com.app.aoede.ui.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.app.aoede.R;
import com.app.aoede.ui.library.AlbumAdapter;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Track;

public class AlbumActivity extends AppCompatActivity {
    RecyclerView albumRecycler;
    AlbumAdapter albumAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        //retrieving arraylist from library
        Bundle albumData = getIntent().getExtras();
        ArrayList<Track> songInAlbum = albumData.getParcelableArrayList("song");
        for (int i = 0; i < songInAlbum.size(); i++) {
            String name = songInAlbum.get(i).name;
            Log.d("spotAuthent", name);
        }

        //setting up recyclerview
        albumRecycler = findViewById(R.id.albumRecycler);
        albumAdapter = new AlbumAdapter(songInAlbum);
        albumRecycler.setAdapter(albumAdapter);
        albumRecycler.setLayoutManager(new LinearLayoutManager(this));


    }
}