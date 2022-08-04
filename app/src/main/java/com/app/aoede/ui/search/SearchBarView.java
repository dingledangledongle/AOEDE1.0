package com.app.aoede.ui.search;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aoede.R;

public class SearchBarView extends RecyclerView.ViewHolder {
    public ImageView imgView;
    public ImageView clickable;
    public TextView txtTitle;
    public TextView txtArtist;


    public SearchBarView(@NonNull View itemView) {
        super(itemView);
        imgView = itemView.findViewById(R.id.imgSearchAlbum);
        txtTitle = itemView.findViewById(R.id.txtSearchTitle);
        txtArtist = itemView.findViewById(R.id.txtSearchArtist);
        clickable = itemView.findViewById(R.id.clickable);
    }
}
