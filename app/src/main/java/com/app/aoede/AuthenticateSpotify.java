package com.app.aoede;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;

public class AuthenticateSpotify {
    public static final int REQUEST_CODE = 1337;
    public static final String REDIRECT_URI = "com.app.aoede://callback";
    public static final String CLIENT_ID = "c868a746013949dd8586f71e70cb6ded";
    public static String ACCESS_TOKEN;

    public SpotifyApi spotifyApi = new SpotifyApi();
    public SpotifyService spotifyService = spotifyApi.getService();

    public void authenticate(Activity activity){
        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID,
                        AuthorizationResponse.Type.TOKEN,
                        REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(activity,REQUEST_CODE,request);

    }

    public void getAccessToken(int requestCode, int resultCode, Intent intent){
        if (requestCode == REQUEST_CODE){
            AuthorizationResponse response = AuthorizationClient
                    .getResponse(resultCode, intent);

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
}

