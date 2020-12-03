package com.example.EcommerceApp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.EcommerceApp.R;
import com.example.EcommerceApp.Service.CheckConnection;

public class ActivityProductDetail extends AppCompatActivity {
    private ImageView ic_back,ic_like;
    private Button btn_add_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ic_back = findViewById(R.id.ic_back);
        ic_like = findViewById(R.id.ic_like);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_add_cart = findViewById(R.id.btn_add_cart);
        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityProductDetail.this, CheckoutActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}