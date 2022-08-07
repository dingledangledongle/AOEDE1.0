package com.app.aoede.ui.library;

import static com.app.aoede.MediaplayerActivity.displaySongInfo;
import static com.app.aoede.MediaplayerActivity.playSong;
import static com.app.aoede.ui.search.SearchAdapter.currentSong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aoede.MainActivity;
import com.app.aoede.MediaplayerActivity;
import com.app.aoede.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Track;

public class PlaylistSelectedAdapter extends RecyclerView.Adapter<PlaylistSelectedView> {
    Context context;

    public PlaylistSelectedAdapter(ArrayList<Track> songs) {
        this.songs = songs;
    }
    ArrayList<Track> songs;


    @NonNull
    @Override
    public PlaylistSelectedView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_playlist_selected,parent,false);
        PlaylistSelectedView playSView = new PlaylistSelectedView(view);



        return playSView;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistSelectedView holder, int position) {
        String title = songs.get(position).name;
        String artist = songs.get(position).artists.get(0).name;
        String imgUrl = songs.get(position).album.images.get(0).url;
        String songUrl = songs.get(position).preview_url;

        holder.songTitle.setText(title);
        holder.songArtist.setText(artist);
        Picasso.get().load(imgUrl).into(holder.songArt);

        ImageView clickable = holder.clickable;
        clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.playerBtn.setImageResource(R.drawable.pause);
                MainActivity.playerTitle.setText(title);
                Picasso.get().load(imgUrl).into(MainActivity.playerArt);
                currentSong = songs.get(position);
                MediaplayerActivity.songQueue = songs;
                playSong(songUrl);
                displaySongInfo();
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
