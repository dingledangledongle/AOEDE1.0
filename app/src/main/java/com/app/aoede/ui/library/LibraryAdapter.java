package com.app.aoede.ui.library;

import static com.app.aoede.ui.library.LibraryFragment.albumArtistMap;
import static com.app.aoede.ui.library.LibraryFragment.albumMap;
import static com.app.aoede.ui.library.LibraryFragment.libraryList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aoede.AlbumActivity;
import com.app.aoede.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.Track;


public class LibraryAdapter extends RecyclerView.Adapter<LibraryView> {

    public LibraryAdapter(ArrayList<String> albums){

        this.albums = albums;
    }

    ArrayList<String> albums;
    Context context;
    HashMap<String, AlbumSimple> albumHashMap = albumMap;
    HashMap<String, String> albumArtistHashMap = albumArtistMap;
    ArrayList<Track> songInAlbum = new ArrayList<>();

    @NonNull
    @Override
    public LibraryView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View libraryView = inflater.inflate(R.layout.item_library,parent,false);
        LibraryView viewHolder = new LibraryView(libraryView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryView holder, int position) {
        String albumId = albums.get(position);
        AlbumSimple albumSelected = albumHashMap.get(albumId);


        String title = albumSelected.name;
        String artist = albumArtistHashMap.get(albumId);
        String artUrl = albumSelected.images.get(0).url;

        ImageView albumArt = holder.albumArt;
        TextView albumTitle = holder.albumTitle;
        TextView albumArtist = holder.albumArtist;

        albumTitle.setText(title);
        albumArtist.setText(artist);
        Picasso.get().load(artUrl).into(albumArt);

        albumArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songInAlbum.clear();
                for (int i = 0; i < libraryList.size() ; i++) {
                    if(libraryList.get(i).album.id.equals(albumId)){
                        songInAlbum.add(libraryList.get(i));
                    }

                }
                Intent intent = new Intent(context, AlbumActivity.class);
                intent.putExtra("song",songInAlbum);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

}


