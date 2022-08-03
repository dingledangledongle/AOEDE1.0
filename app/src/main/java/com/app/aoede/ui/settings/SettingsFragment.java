package com.app.aoede.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.aoede.R;

public class SettingsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container,false);
        Button btnProfile = view.findViewById(R.id.btnProfile);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment settingsProfile = new SettingsProfile();
                FragmentTransaction manager = requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction();
                manager.replace(R.id.container,settingsProfile).setReorderingAllowed(true).addToBackStack(null).commit();
            }
        });

        return view;
    }


}