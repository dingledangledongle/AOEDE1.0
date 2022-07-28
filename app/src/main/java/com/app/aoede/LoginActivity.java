package com.app.aoede;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText txtLoginEmail;
    private EditText txtLoginPassword;
    private Button btnLogin;
    private CheckBox rememberMe;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkboxState = preferences.getString("remember","");
        SharedPreferences loginPreferences = getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor = loginPreferences.edit();
        String email = loginPreferences.getString("email","");
        String password = loginPreferences.getString("password","");

        txtLoginEmail = findViewById(R.id.txtLoginEmail);
        txtLoginPassword = findViewById(R.id.txtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        rememberMe = findViewById(R.id.checkBoxRemember);

        mAuth = FirebaseAuth.getInstance();

        //Immediately log the user in if they have logged in once and checked the box
        if (checkboxState.equals("true") && !email.equals("") && !password.equals("") ){
            loginUser(email,password);
        }

        //Logging the user in
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = txtLoginEmail.getText().toString();
                String passwordInput = txtLoginPassword.getText().toString();

                //putting the user login info in sharedpreferences if checkbox is ticked
                if (checkboxState.equals("true")){
                    if(email.equals("") && password.equals("")){
                        editor.putString("email",emailInput);
                        editor.putString("password",passwordInput);
                        editor.apply();
                        loginUser(emailInput,passwordInput);
                    }
                }else if (checkboxState.equals("false")){
                    loginUser(emailInput,passwordInput);
                }



            }
        });

        //Checking when the check box is ticked
        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    //saving the state of the checkbox
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                }else if (!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                }
            }
        });
    }

    //logging the user in with firebase
    private void loginUser(String emailInput, String passwordInput) {
        mAuth.signInWithEmailAndPassword(emailInput,passwordInput).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this,"LOGGED IN SUCCESSFULLY",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,MainActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this)
                                .toBundle());
            }
        });

    }
}