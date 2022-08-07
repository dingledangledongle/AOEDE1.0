package com.app.aoede.ui.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.aoede.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Track;

public class CreatePlaylistActivity extends AppCompatActivity {
    RecyclerView createRecycler;
    CreatePlaylistAdapter cpAdapter;
    SharedPreferences playlistSP;
    ArrayList<Track> songList = new ArrayList<>();
    ArrayList<ArrayList<Track>> playlists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_playlist);
        Bundle songData = this.getIntent().getExtras();
        songList = songData.getParcelableArrayList("song");

        createRecycler = findViewById(R.id.createPlaylistRecycler);
        cpAdapter = new CreatePlaylistAdapter(songList);
        createRecycler.setAdapter(cpAdapter);
        createRecycler.setLayoutManager(new LinearLayoutManager(this));

        playlistSP = getSharedPreferences("libraryList",MODE_PRIVATE);
        String playlistInLibrary = playlistSP.getString("playlist","");
        if(!playlistInLibrary.equals("")){
            TypeToken<ArrayList<ArrayList<Track>>> token =
                    new TypeToken<ArrayList<ArrayList<Track>>>(){};
            Gson gson = new Gson();
            playlists = gson.fromJson(playlistInLibrary,token.getType());
        }
    }

    public void savePlaylist(View view) {
        if(cpAdapter.playlistCreated.isEmpty()){
            Toast.makeText(this, "PLAYLIST EMPTY", Toast.LENGTH_SHORT).show();
        }else{
            playlists.add(cpAdapter.playlistCreated);
            SharedPreferences.Editor editor = playlistSP.edit();
            Gson gson = new Gson();
            String putPlaylist = gson.toJson(playlists);
            editor.putString("playlist",putPlaylist).apply();
            finish();
        }


    }
}