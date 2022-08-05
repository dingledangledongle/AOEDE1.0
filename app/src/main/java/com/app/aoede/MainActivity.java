package com.app.aoede;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.app.aoede.ui.search.SearchAdapter;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.app.aoede.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import kaaes.spotify.webapi.android.models.Track;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    //UI ELEMENTS
    BlurView blurView;
    public static ImageView playerArt;
    public static TextView playerTitle;
    public static ImageButton playerBtn;

    static Track currentSong = SearchAdapter.currentSong;

    //GOOGLE
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    //SPOTIFY
    public static AuthenticateSpotify authenticateSpotify = new AuthenticateSpotify();
    MediaPlayer player = MediaplayerActivity.player;
    SearchAdapter searchAdapter = getSearchAdapter();

    public SearchAdapter getSearchAdapter() {
        return searchAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setting up bottom navigation bar and top app bar
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_search,R.id.navigation_library,R.id.navigation_home,R.id.navigation_settings)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //blurring
        blurView = findViewById(R.id.blurViewMain);
        blurBackground();

        //GOOGLE SIGN OUT
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(googleAccount != null){
            Log.d("ACCOUNT HANDLER", googleAccount + "");
        }

        //Spotify Authentication
       authenticateSpotify.authenticate(this);

        //UI ELEMENTS
        playerArt = findViewById(R.id.albumArtMediaplayer);
        playerTitle = findViewById(R.id.txtTitleMediaplayer);
        playerBtn = findViewById(R.id.btnPlayMediaplayer);

        if(currentSong != null){
            String url = currentSong.album.images.get(0).url;
            Picasso.get().load(url).into(playerArt);
        }

        if (player.isPlaying()){
            playerBtn.setImageResource(R.drawable.pause);
        }else{
            playerBtn.setImageResource(R.drawable.play_arrow);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        authenticateSpotify.getAccessToken(requestCode,resultCode,data);
    }

    //METHODS

    private void blurBackground() {
        float radius = 21f;

        View decorView = getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);


        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView, new RenderScriptBlur(this))
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius)
                .setBlurEnabled(true);
    }

    //logging user out
    public void logOut(View view) {
        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleAccount != null){
            googleSignOut();
            Log.d("ACCOUNT HANDLER", "GOOGLE SIGN OUT");
        }else{
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(MainActivity.this,"SUCCESSFULLY LOGGED OUT",Toast.LENGTH_SHORT).show();
            Log.d("ACCOUNT HANDLER", "FIREBASE SIGN OUT");
            startActivity(new Intent(MainActivity.this,FirebaseLogIn.class),
                    ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
        }
    }

    //Google sign out
    private void googleSignOut() {
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(getApplicationContext(),FirebaseLogIn.class));
            }
        });
    }

    //start MediaplayerActivity
    public void goToMediaplayer(View view) {
        startActivity(new Intent(MainActivity.this,MediaplayerActivity.class),
                ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
    }

    public void mediaPlayPause(View view) {
        if(player.isPlaying()){
            player.pause();
            playerBtn.setImageResource(R.drawable.play_arrow);
        }else{
            player.start();
            playerBtn.setImageResource(R.drawable.pause);
        }
    }

}