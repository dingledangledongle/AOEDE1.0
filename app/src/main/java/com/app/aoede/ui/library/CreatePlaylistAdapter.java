package com.app.aoede.ui.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aoede.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Track;

public class CreatePlaylistAdapter extends RecyclerView.Adapter<CreatePlaylistView> {

    public CreatePlaylistAdapter(ArrayList<Track> songList) {
        this.songList = songList;
    }

    ArrayList<Track> songList;
    ArrayList<Track> playlistCreated = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public CreatePlaylistView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cpView = inflater.inflate(R.layout.item_playlist_create,parent,false);
        CreatePlaylistView cpHolder = new CreatePlaylistView(cpView);

        return cpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CreatePlaylistView holder, int position) {
        Track song = songList.get(position);
        String songTitle = song.name;
        String songArtist = song.artists.get(0).name;
        String songImgUrl = song.album.images.get(0).url;

        holder.songTitle.setText(songTitle);
        holder.songArtist.setText(songArtist);
        Picasso.get().load(songImgUrl).into(holder.songImg);

        ImageView addPlaylist = holder.addPlaylist;
        addPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playlistCreated.add(song);
                addPlaylist.setImageResource(R.drawable.ic_baseline_check_24);
            }
        });


    }

    @Override
    public int getItemCount() {
        return songList.size();
    }
}
