package com.app.aoede.ui.settings;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.aoede.MainActivity;
import com.app.aoede.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class SettingsFragment extends Fragment {
    FirebaseUser user = MainActivity.user;
    GoogleSignInAccount googleAccount;
    TextView email;
    ImageView profilePic;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container,false);
        email = view.findViewById(R.id.txtProfileEmail);
        profilePic = view.findViewById(R.id.profileImage);
        googleAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if (googleAccount != null){
            String gEmail = googleAccount.getEmail();
            Uri gPic = googleAccount.getPhotoUrl();

            email.setText(gEmail);
            if(gPic != null){
                Picasso.get().load(gPic).into(profilePic);
            }


        }else{
            String fEmail = user.getEmail();

            email.setText(fEmail);
        }
        return view;
    }


}