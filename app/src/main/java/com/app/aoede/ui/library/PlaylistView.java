package com.app.aoede.ui.library;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aoede.R;

public class PlaylistView extends RecyclerView.ViewHolder {
    ImageView playlistArt;
    TextView playlistName;

    public PlaylistView(@NonNull View itemView) {
        super(itemView);

        playlistArt = itemView.findViewById(R.id.playlistArt);
    }
}
