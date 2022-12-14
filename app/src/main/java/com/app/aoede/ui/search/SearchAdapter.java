package com.app.aoede.ui.search;

import static com.app.aoede.MainActivity.playerBtn;
import static com.app.aoede.MediaplayerActivity.addToQueue;
import static com.app.aoede.MediaplayerActivity.playSong;
import static com.app.aoede.MediaplayerActivity.songQueue;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aoede.MainActivity;
import com.app.aoede.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Track;

public class SearchAdapter extends RecyclerView.Adapter<SearchBarView> {

    public SearchAdapter(List<Track> songs){
        this.songs = songs;
    }

    Context context;
    List<Track> songs;
    public static Track currentSong;
    SharedPreferences listShared;
    ArrayList<Track> libraryList = new ArrayList<>();

    @NonNull
    @Override
    public SearchBarView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View songView = inflater.inflate(R.layout.item_search,parent,false);
        SearchBarView viewHolder = new SearchBarView(songView);

        listShared = context.getSharedPreferences("libraryList",Context.MODE_PRIVATE);
        String songsInLibrary = listShared.getString("library", "");
        if(!songsInLibrary.equals("")){
            TypeToken<ArrayList<Track>> token = new TypeToken<ArrayList<Track>>(){};
            Gson gson = new Gson();
            libraryList = gson.fromJson(songsInLibrary, token.getType());
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchBarView holder, int position) {
        TextView title = holder.txtTitle;
        TextView artist = holder.txtArtist;


        //check if list is null
        if(songs == null){
            Log.d("spotAuthent", "songs null");
        }else{
            Track song = songs.get(position);
            String songTitle = song.name;
            String songArtist = song.artists.get(0).name;
            Picasso.get().load(song.album.images.get(0).url).into(holder.imgView);

            title.setText(songTitle);
            artist.setText(songArtist);
        }

        //Play song on click
        ImageView clickable = holder.clickable;
        clickable.setOnClickListener(v -> {
            currentSong = songs.get(position);
            String url = currentSong.preview_url;
            //checks if url is null
            if(url == null){
                Toast.makeText(clickable.getContext(), "Link Unavailable",Toast.LENGTH_SHORT).show();
            }else{
                if(songQueue.isEmpty()){
                    playSong(url);
                    addToQueue(currentSong);
                    displaySong();
                }else if(!songQueue.isEmpty()){
                    playSong(url);
                    addToQueue(currentSong);
                    displaySong();
                    songQueue.remove(1);
                }


            }

        });

        ImageView addToLibrary = holder.addToLibrary;
        addToLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("spotAuthent", String.valueOf(libraryList));
                listShared = context.getSharedPreferences("libraryList",Context.MODE_PRIVATE);
                Track selectedSong = songs.get(position);
                libraryList.add(selectedSong);
                addToLibrary.setImageResource(R.drawable.ic_baseline_check_24);
                Gson gson = new Gson();
                String json = gson.toJson(libraryList);
                SharedPreferences.Editor editor = listShared.edit();
                editor.putString("library",json);
                editor.apply();

                    Log.d("spotAuthent", selectedSong.name);
                }
            });
        }


    @Override
    public int getItemCount() {
        if(songs == null){
            return 0;
        }else{
            return songs.size();
        }
    }

    public void displaySong(){
        String img = currentSong.album.images.get(0).url;
        String name = currentSong.name;
        MainActivity.playerTitle.setText(name);
        Picasso.get().load(img).into(MainActivity.playerArt);
        playerBtn.setImageResource(R.drawable.pause);

    }

}

