package com.app.aoede.ui.library;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aoede.R;

public class LibraryView extends RecyclerView.ViewHolder {
    public ImageView albumArt;
    public TextView albumTitle;
    public TextView albumArtist;

    public LibraryView(@NonNull View itemView) {
        super(itemView);

        albumArt = itemView.findViewById(R.id.libraryAlbumArt);
        albumTitle = itemView.findViewById(R.id.libraryAlbumTitle);
        albumArtist = itemView.findViewById(R.id.libraryAlbumArtist);
    }
}
