package com.app.aoede.ui.library;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.aoede.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.Track;


public class LibraryFragment extends Fragment {

    public LibraryAdapter libraryAdapter;
    RecyclerView libraryRecycler;
    ArrayList<Track> libraryList = new ArrayList<>();
    ArrayList<String> albumList = new ArrayList<>();
    SharedPreferences libraryShared;
    public static HashMap<String, String> albumArtistMap = new HashMap<>();
    public static HashMap<String, AlbumSimple> albumMap = new HashMap<String, AlbumSimple>();
    Button goPlaylist;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_library,container,false);

        libraryShared = getActivity().getSharedPreferences("libraryList",Context.MODE_PRIVATE);
        String library = libraryShared.getString("library","");
        if(!library.equals("")){
            TypeToken<ArrayList<Track>> token = new TypeToken<ArrayList<Track>>(){};
            Gson gson = new Gson();
            libraryList = gson.fromJson(library, token.getType());
        }

        for (int i = 0; i < libraryList.size(); i++) {
           String albumId = libraryList.get(i).album.id;
           String albumArtist = libraryList.get(i).artists.get(0).name;
           if(!albumList.contains(albumId)){
               albumList.add(albumId);
           }
           albumMap.put(albumId, libraryList.get(i).album);
           albumArtistMap.put(albumId,albumArtist);
        }

        libraryRecycler = view.findViewById(R.id.libraryRecycler);
        libraryAdapter = new LibraryAdapter(albumList);

        libraryRecycler.setAdapter(libraryAdapter);
        libraryRecycler.setLayoutManager(new GridLayoutManager(view.getContext(),2));

        goPlaylist = view.findViewById(R.id.btnPlaylist);
        goPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), PlaylistActivity.class));
            }
        });

        return view;

        }

    }
