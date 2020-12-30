package com.example.EcommerceApp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.EcommerceApp.Adapter.CartAdapter;
import com.example.EcommerceApp.Model.Cart;
import com.example.EcommerceApp.R;
import com.example.EcommerceApp.Service.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    View lyt_empty_cart;
    RelativeLayout lyt_order;
    DBHelper dbhelper;
    CartAdapter adapterCart;
    double total_price;
    final int CLEAR_ALL_ORDER = 0;
    final int CLEAR_ONE_ORDER = 1;
    int FLAG;
    int ID;
    double str_tax;
    String str_currency_code;
    Button btn_checkout, btn_continue;
    ArrayList<ArrayList<Object>> data;
    public static ArrayList<Integer> product_id = new ArrayList<Integer>();
    public static ArrayList<String> product_name = new ArrayList<String>();
    public static ArrayList<Integer> product_quantity = new ArrayList<Integer>();
    public static ArrayList<Double> sub_total_price = new ArrayList<Double>();
    public static ArrayList<String> product_image = new ArrayList<String>();
    List<Cart> arrayCart;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }
}