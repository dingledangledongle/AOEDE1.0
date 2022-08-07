package com.app.aoede.ui.library;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aoede.R;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Track;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistView> {
    public PlaylistAdapter(ArrayList<ArrayList<Track>> playlists) {
        this.playlists = playlists;
    }
    ArrayList<ArrayList<Track>> playlists;
    Context context;

    @NonNull
    @Override
    public PlaylistView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View playlistView = inflater.inflate(R.layout.item_playlist,parent,false);
        PlaylistView pView = new PlaylistView(playlistView);

        return pView;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistView holder, int position) {
        ArrayList<Track> playlistSelected = playlists.get(position);

        ImageView art = holder.playlistArt;
        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PlaylistSelectedActivity.class);
                intent.putExtra("playlistSongs",playlistSelected);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }
}
