package com.app.aoede;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirebaseLogIn extends AppCompatActivity {
    private Button btnRegister;
    private Button btnLogin;
    private Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_log_in);

        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogIn);
        btnTest = findViewById(R.id.btnTest);

        //Go to register page
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirebaseLogIn.this, RegisterActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(FirebaseLogIn.this).toBundle());

            }
        });

        //Go to login page
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirebaseLogIn.this,LoginActivity.class));

            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirebaseLogIn.this,MediaplayerActivity.class));
            }
        });
    }


}