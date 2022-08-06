package com.app.aoede.ui.library;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aoede.R;

public class CreatePlaylistView extends RecyclerView.ViewHolder {
    ImageView songImg;
    ImageView addPlaylist;
    TextView songTitle;
    TextView songArtist;
    public CreatePlaylistView(@NonNull View itemView) {
        super(itemView);

        songImg = itemView.findViewById(R.id.imgCPart);
        songTitle = itemView.findViewById(R.id.txtCPtitle);
        songArtist = itemView.findViewById(R.id.txtCPartist);
        addPlaylist = itemView.findViewById(R.id.btnCPadd);
    }
}
