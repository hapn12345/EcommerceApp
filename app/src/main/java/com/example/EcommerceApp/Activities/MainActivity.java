package com.example.EcommerceApp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.EcommerceApp.Fragments.Categories_fragment;
import com.example.EcommerceApp.Fragments.HelpFragment;
import com.example.EcommerceApp.Fragments.HomeFragment;
import com.example.EcommerceApp.Fragments.ProfileFragment;
import com.example.EcommerceApp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private TextView titleTv;
    private ImageButton cart;
    private BottomNavigationView bottom_navigate;

    private Fragment homeFragment,categoriesFragment,helpFragment,profileFragment;
    private Fragment activeFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleTv = findViewById(R.id.titleTv);
        cart = findViewById(R.id.cart);
        bottom_navigate = findViewById(R.id.bottom_navigate);

        initFragments();
        bottom_navigate.setOnNavigationItemSelectedListener(this);

    }

    private void initFragments() {
        homeFragment = new HomeFragment();
        categoriesFragment = new Categories_fragment();
        helpFragment = new HelpFragment();
        profileFragment = new ProfileFragment();

        fragmentManager = getSupportFragmentManager();
        activeFragment = homeFragment;
        fragmentManager.beginTransaction()
                .add(R.id.frame,homeFragment,"homeFragment")
                .commit();
        fragmentManager.beginTransaction()
                .add(R.id.frame,categoriesFragment,"categoriesFragment")
                .hide(categoriesFragment)
                .commit();
        fragmentManager.beginTransaction()
                .add(R.id.frame,helpFragment,"helpFragment")
                .hide(helpFragment)
                .commit();
        fragmentManager.beginTransaction()
                .add(R.id.frame,profileFragment,"profileFragment")
                .hide(profileFragment)
                .commit();
    }
    private void loadHomeFragment(){
        titleTv.setText("Home");
        fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit();
        activeFragment = homeFragment;
    }
    private void loadCategoriesFragment(){
        titleTv.setText("Categories");
        fragmentManager.beginTransaction().hide(activeFragment).show(categoriesFragment).commit();
        activeFragment = categoriesFragment;
    }
    private void loadHelpFragment(){
        titleTv.setText("Help");
        fragmentManager.beginTransaction().hide(activeFragment).show(helpFragment).commit();
        activeFragment = helpFragment;
    }
    private void loadProfileFragment(){
        titleTv.setText("Profile");
        fragmentManager.beginTransaction().hide(activeFragment).show(profileFragment).commit();
        activeFragment = profileFragment;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                loadHomeFragment();
                return true;
            case R.id.nav_categories:
                loadCategoriesFragment();
                return true;
            case R.id.nav_help:
                loadHelpFragment();
                return true;
            case R.id.nav_profile:
                loadProfileFragment();
                return true;
        }
        return false;
    }
}