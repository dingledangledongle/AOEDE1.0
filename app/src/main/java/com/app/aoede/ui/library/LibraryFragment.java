package com.app.aoede.ui.library;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.aoede.R;
import com.app.aoede.databinding.FragmentLibraryBinding;


public class LibraryFragment extends Fragment {

    private FragmentLibraryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_library,container,false);

        return view;

        }

    }
