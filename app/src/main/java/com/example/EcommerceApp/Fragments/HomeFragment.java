package com.example.EcommerceApp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.EcommerceApp.Activities.ActivityProductDetail;
import com.example.EcommerceApp.Adapter.ProductAdapter;
import com.example.EcommerceApp.Model.Media;
import com.example.EcommerceApp.Model.Product;
import com.example.EcommerceApp.R;
import com.example.EcommerceApp.Service.CheckConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements ProductAdapter.ContactsAdapterListener {
    Context context;
    private RecyclerView recycler_view;
    private ArrayList<Product> productArrayList;
    private SearchView searchView;
    private ProductAdapter productAdapter;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    public static final String URL = "http://3.25.210.151/api/customer/product/product?limit=20";
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(CheckConnection.haveNetworkConnection(getContext())){
            GetData();
        }else {
            CheckConnection.showToast_Short(getContext(),"Check Internet");
        }

    }

    public void GetData() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                CheckConnection.showToast_Short(context,"OK");
                try {
                    JSONArray jsonArray = response.getJSONArray("products");
                    for(int i = 0;i<response.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String name = jsonObject.getString("name");
                        //convert Media
                        String media = jsonObject.getString("media");
                        Gson gson = new Gson();
                        Media mediaa;
                        mediaa = gson.fromJson(media,Media.class);

                        String price = jsonObject.getString("price");
                        //String description = jsonObject.getString("description");

                        productArrayList.add(new Product(name,mediaa,price));
                        Log.e("abc", String.valueOf(response));
                    }
                    productAdapter = new ProductAdapter(context,productArrayList);
                    recycler_view.setAdapter(productAdapter);
                    productAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                handleResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Failed"+ error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
//    private void handleResponse(String response){
//        productArrayList = new ArrayList<>();
//        productArrayList.clear();
//        try{
//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray jsonArray = jsonObject.getJSONArray("products");
//
//            GsonBuilder gsonBuilder = new GsonBuilder();
//            gsonBuilder.setDateFormat("dd/MM/yyyy hh:mm a");
//            Gson gson = gsonBuilder.create();
//
//            for(int i = 0;i<jsonArray.length();i++){
//                Product mProduct = gson.fromJson(jsonArray.getJSONObject(i).toString(),Product.class);
//                productArrayList.add(mProduct);
//                productAdapter.notifyDataSetChanged();
//            }
//
//            productAdapter = new ProductAdapter(context,productArrayList,this);
//            recycler_view.setAdapter(productAdapter);
//            swipeRefreshLayout.setVisibility(View.GONE);
//        }catch (Exception e){
//            swipeRefreshLayout.setVisibility(View.GONE);
//            Toast.makeText(context,""+ e.getMessage(),Toast.LENGTH_SHORT).show();
//        }
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout_home);
        swipeRefreshLayout.setRefreshing(true);

        recycler_view = view.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(context, 2);
        recycler_view.setLayoutManager(manager);
        productArrayList = new ArrayList<>();

        productAdapter = new ProductAdapter(getActivity(),productArrayList);
        recycler_view.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
        onRefresh();

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        GetData();
    }

    public void onRefresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onContactSelected(Product product) {
//        Intent intent = new Intent(getActivity(), ActivityProductDetail.class);
//        intent.putExtra("ID", product.getId());
//        intent.putExtra("name", product.getName());
//        intent.putExtra("imageURL", product.getImageUrl());
//        intent.putExtra("price", product.getPrice());
//        intent.putExtra("description", product.getDescription());
//        intent.putExtra("categoryId", product.getCategoryID());
//        startActivity(intent);
    }
}