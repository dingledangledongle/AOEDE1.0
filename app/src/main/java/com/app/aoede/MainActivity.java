package com.app.aoede;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.app.aoede.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    BlurView blurView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

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

    }

    private void blurBackground() {
        float radius = 18f;

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
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this,"SUCCESSFULLY LOGGED OUT",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this,FirebaseLogIn.class),
                ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());

    }

    //start MediaplayerActivity
    public void goToMediaplayer(View view) {
        startActivity(new Intent(MainActivity.this,MediaplayerActivity.class));
    }
}