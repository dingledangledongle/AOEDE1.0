package com.app.aoede.ui.library;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aoede.R;

public class PlaylistSelectedView extends RecyclerView.ViewHolder {
    ImageView songArt;
    TextView songTitle;
    TextView songArtist;
    ImageView clickable;
    public PlaylistSelectedView(@NonNull View itemView) {
        super(itemView);

        songArt = itemView.findViewById(R.id.playlistSelArt);
        songTitle = itemView.findViewById(R.id.txtPStitle);
        songArtist = itemView.findViewById(R.id.txtPSartist);
        clickable = itemView.findViewById(R.id.playlistClickable);
    }
}
