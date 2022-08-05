package com.app.aoede.ui.search;

import static com.app.aoede.ui.search.SearchAdapter.currentSong;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aoede.AuthenticateSpotify;
import com.app.aoede.MainActivity;
import com.app.aoede.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchFragment extends Fragment {
    SearchView searchView;
    private RecyclerView recyclerView;
    public SearchAdapter searchAdapter;
    private SearchAdapter newSearch;
    static String songName;
    static String songId;
    static List<Track> searchResults;
    AuthenticateSpotify spotifyAuth = MainActivity.authenticateSpotify;
    SpotifyService spotify = spotifyAuth.spotifyService;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search,container,false);

        searchView = view.findViewById(R.id.searchBar);
        recyclerView = view.findViewById(R.id.searchRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        searchAdapter = new SearchAdapter(searchResults);
        recyclerView.setAdapter(searchAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchSongs(query);
                newSearch = new SearchAdapter(searchResults);
                recyclerView.swapAdapter(newSearch,true);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchSongs(newText);
                newSearch = new SearchAdapter(searchResults);
                recyclerView.swapAdapter(newSearch,true);

                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchResults.clear();
                newSearch = new SearchAdapter(searchResults);
                recyclerView.swapAdapter(newSearch,true);
                return false;
            }
        });
    }

    public void searchSongs(String searchInput){
        spotify.searchTracks(searchInput, new Callback<TracksPager>() {
            @Override
            public void success(TracksPager tracksPager, Response response) {
                songName = tracksPager.tracks.items.get(0).name;
                songId = tracksPager.tracks.items.get(0).id;
                searchResults = tracksPager.tracks.items;
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("spotAuthent", error.toString());

            }

        });

    }

}