package com.example.EcommerceApp.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.EcommerceApp.Adapter.ProductAdapter;
import com.example.EcommerceApp.Model.Product;
import com.example.EcommerceApp.R;
import com.example.EcommerceApp.Service.CheckConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CheckConnection.showToast_Short(context,"OK");
                handleResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setVisibility(View.GONE);
                Toast.makeText(context,"Failed"+ error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    private void handleResponse(String response){
        productArrayList = new ArrayList<>();
        productArrayList.clear();
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("products");

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("dd/MM/yyyy hh:mm a");
            Gson gson = gsonBuilder.create();

            for(int i = 0;i<jsonArray.length();i++){
                Product mProduct = gson.fromJson(jsonArray.getJSONObject(i).toString(),Product.class);
                productArrayList.add(mProduct);
                productAdapter.notifyDataSetChanged();
            }

            productAdapter = new ProductAdapter(context,productArrayList,this);
            recycler_view.setAdapter(productAdapter);
            swipeRefreshLayout.setVisibility(View.GONE);
        }catch (Exception e){
            swipeRefreshLayout.setVisibility(View.GONE);
            Toast.makeText(context,""+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout_home);
        swipeRefreshLayout.setRefreshing(false);

        recycler_view = view.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(context, 2);
        recycler_view.setLayoutManager(manager);
        productArrayList = new ArrayList<>();

        productAdapter = new ProductAdapter(getActivity(),productArrayList,this);
        recycler_view.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();


        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        GetData();
    }
    @Override
    public void onContactSelected(Product product) {

    }
}