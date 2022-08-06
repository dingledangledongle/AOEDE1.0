package com.app.aoede;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseLogIn extends AppCompatActivity {
    private Button btnRegister;
    private Button btnLogin;
    private Button btnGoogle;
    private EditText txtLoginEmail;
    private EditText txtLoginPassword;

    private FirebaseAuth mAuth;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_log_in);

        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogIn);
        btnGoogle = findViewById(R.id.btnGoogle);
        txtLoginEmail = findViewById(R.id.txtLoginEmail);
        txtLoginPassword = findViewById(R.id.txtLoginPassword);

        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkboxState = preferences.getString("remember","");
        SharedPreferences loginPreferences = getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor = loginPreferences.edit();
        SharedPreferences.Editor checkboxEditor = preferences.edit();
        String email = loginPreferences.getString("email","");
        String password = loginPreferences.getString("password","");

        mAuth = FirebaseAuth.getInstance();
        Log.d("spotAuthent", checkboxState);

        //Immediately log the user in if they have logged in once and checked the box
        if (checkboxState.equals("true") && !email.equals("") && !password.equals("") ){
            loginUser(email,password);
        }

        //GOOGLE SIGN IN
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            startActivity(new Intent(this, MainActivity.class));
        }
        
        //Go to register page
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirebaseLogIn.this, RegisterActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(FirebaseLogIn.this).toBundle());

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = txtLoginEmail.getText().toString();
                String passwordInput = txtLoginPassword.getText().toString();

                if (emailInput.equals("")){
                    Toast.makeText(FirebaseLogIn.this, "Please input E-mail", Toast.LENGTH_SHORT).show();
                }else if (passwordInput.equals("")){
                    Toast.makeText(FirebaseLogIn.this, "Please input Password", Toast.LENGTH_SHORT).show();
                }else{
                    if(email.equals("") && password.equals("")){
                        editor.putString("email",emailInput);
                        editor.putString("password",passwordInput);
                        editor.apply();
                        checkboxEditor.putString("remember","true");
                        checkboxEditor.apply();
                        loginUser(emailInput, passwordInput);
                    }else{
                        checkboxEditor.putString("remember","true");
                        checkboxEditor.apply();
                        loginUser(emailInput, passwordInput);
                    }
                }
            }
        });


        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });

    }

    private void SignIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                HomeActivity();
            } catch (ApiException e) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void HomeActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void loginUser(String emailInput, String passwordInput) {
        mAuth.signInWithEmailAndPassword(emailInput,passwordInput).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(FirebaseLogIn.this,"LOGGED IN SUCCESSFULLY",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(FirebaseLogIn.this,MainActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(FirebaseLogIn.this)
                                .toBundle());
            }
        });

    }
}