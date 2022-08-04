package com.app.aoede;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import java.io.IOException;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SpotifyActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1337;
    private static final String REDIRECT_URI = "com.app.aoede://callback";
    private static final String CLIENT_ID = "c868a746013949dd8586f71e70cb6ded";
    String ACCESS_TOKEN;
    Button btnSmth;
    TextView txtName;
    TextView txtEmail;
    SearchView textInput;
    static String output;
    static String songId;

    //SPOTIFY API
    SpotifyApi spotifyApi = new SpotifyApi();
    SpotifyService spotifyService = spotifyApi.getService();

    //MEDIAPLAYER
    private final MediaPlayer player = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify);
        btnSmth = findViewById(R.id.btnSMTH);
        authenticateSpotify();
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        textInput = findViewById(R.id.textInput);


//        textInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                searchSongs(s);
//                return false;
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        textInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchSongs(s);
                return false;
            }
        });
    }

    public void searchSongs(String songName){
        spotifyService.searchTracks(songName, new Callback<TracksPager>() {
            @Override
            public void success(TracksPager tracksPager, Response response) {
                output = tracksPager.tracks.items.get(0).name;
                songId = tracksPager.tracks.items.get(0).id;
                Log.d("spotAuthent",output );
            }

            @Override
            public void failure(RetrofitError error) {

            }

        });

    }



    //PROTECT
    public void authenticateSpotify(){
        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID,
                        AuthorizationResponse.Type.TOKEN,
                        REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this,REQUEST_CODE,request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == REQUEST_CODE){
            AuthorizationResponse response =
                    AuthorizationClient.getResponse(resultCode, intent);


            switch (response.getType()){
                case TOKEN:
                    ACCESS_TOKEN = response.getAccessToken();
                    spotifyApi.setAccessToken(ACCESS_TOKEN);
                    Log.d("spotAuthent", "ACCESS TOKEN : " + ACCESS_TOKEN);
                    Log.d("spotAuthent", "SPOTIFY AUTHENTICATED SUCCESSFULLY onActivity");
                    break;
                case ERROR:

                    Log.d("spotAuthent", "SPOTIFY AUTHENTICATE ERROR onActivity " + response.getError());
                    break;
                default:
                    Log.d("spotAuthent", "spotify default");

            }

        }
    }


    public void getAlbum(View view) {
        spotifyService.getMe(new Callback<UserPrivate>() {
            @Override
            public void success(UserPrivate userPrivate, Response response) {
                String name =  userPrivate.display_name;
                String email = userPrivate.birthdate;
                txtName.setText(name);
                txtEmail.setText(email);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("spotAuthent", error.toString());
            }
        });
    }

    public void doSmth(View view) {
        getSpotifyTrack(songId);
    }

    public void getSpotifyTrack(String songId){
        spotifyService.getTrack(songId, new Callback<Track>() {
            @Override
            public void success(Track track, Response response) {
                String previewUrl = track.preview_url;
                String url = track.external_urls.get("spotify");

                playSong(previewUrl);

                Log.d("spotAuthent", previewUrl);
                Log.d("spotAuthent", "" + url);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("spotAuthent", error.toString());
            }
        });
    }

    public void playSong(String url){
        try {
            player.reset();
            player.setDataSource(url);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}