package com.app.aoede;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

public class SpotifyActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1337;
    private static final String REDIRECT_URI = "https://aoede.com/callback/";
    private static final String CLIENT_ID = "c868a746013949dd8586f71e70cb6ded";

    Button btnAuthenticate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify);
        btnAuthenticate = findViewById(R.id.btnAuthenticate);

        btnAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateSpotify();
            }
        });

    }

    public void authenticateSpotify(){
        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID,
                        AuthorizationResponse.Type.TOKEN,
                        REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == REQUEST_CODE){
            AuthorizationResponse response =
                    AuthorizationClient.getResponse(resultCode, intent);

            switch (response.getType()){
                case TOKEN:
                    Log.d("spotAuthent", "SPOTIFY AUTHENTICATED SUCCESSFULLY");
                    break;
                case ERROR:
                    Log.d("spotAuthent", "SPOTIFY AUTHENTICATE ERROR");
                    break;
                default:
                    Log.d("spotAuthent", "spotify default");


            }

        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Uri uri = intent.getData();
        if (uri != null) {
            AuthorizationResponse response = AuthorizationResponse.fromUri(uri);

            switch (response.getType()) {
                case TOKEN:
                    Log.d("spotAuthent", "SPOTIFY AUTHENTICATED SUCCESSFULLY");
                    break;

                case ERROR:
                    Log.d("spotAuthent", "SPOTIFY AUTHENTICATE ERROR");

                    break;

                default:
                    Log.d("spotAuthent", "spotify default");

            }
        }
    }
}