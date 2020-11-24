package com.example.EcommerceApp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.EcommerceApp.Activities.LoginActivity;
import com.example.EcommerceApp.R;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    private TextView txt_user_name,txt_user_email;
    private MaterialRippleLayout btn_edit_user,btn_order_history,btn_rate,btn_share,btn_privacy,btn_logout;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        txt_user_name = view.findViewById(R.id.txt_user_name);
        txt_user_email = view.findViewById(R.id.txt_user_email);
        btn_edit_user = view.findViewById(R.id.btn_edit_user);
        btn_order_history = view.findViewById(R.id.btn_order_history);
        btn_rate = view.findViewById(R.id.btn_rate);
        btn_share = view.findViewById(R.id.btn_share);
        btn_privacy = view.findViewById(R.id.btn_privacy);
        btn_logout = view.findViewById(R.id.btn_logout);

        return view;
    }
}