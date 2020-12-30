package com.example.EcommerceApp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.EcommerceApp.R;

public class WishLishFragment extends Fragment {
    public WishLishFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_help, container, false);
//
//        lyt_root = view.findViewById(R.id.lyt_root);
//        if (false) {
//            lyt_root.setRotationY(180);
//        }
        return view;

    }

}