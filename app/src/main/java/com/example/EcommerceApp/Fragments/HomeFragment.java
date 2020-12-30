package com.example.EcommerceApp.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;
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
import com.example.EcommerceApp.Config;
import com.example.EcommerceApp.Model.Media;
import com.example.EcommerceApp.Model.Product;
import com.example.EcommerceApp.R;
import com.example.EcommerceApp.Service.CheckConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ProductAdapter.ContactsAdapterListener {
    Context context;
    private RecyclerView recycler_view;
    private ArrayList<Product> productArrayList;
    private ProductAdapter mAdapter;
    private SearchView searchView;
    private ProductAdapter productAdapter;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    Config config;
    public final static String URL = "https://7a577d781a6a.ngrok.io/api/customer/product/product?limit=20";
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
//    public void GetData(){
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                CheckConnection.showToast_Short(context,"OK");
//                try {
//
//                    String name = response.getString("name");
//                    String price = response.getString("price");
//                    String media = response.getString("media");
//                    Gson gson = new Gson();
//                    Media mediaa;
//                    mediaa = gson.fromJson(media,Media.class);
//                    productArrayList.add(new Product(name,mediaa,price));
//
//                    productAdapter = new ProductAdapter(context,productArrayList);
//                    recycler_view.setAdapter(productAdapter);
//                    productAdapter.notifyDataSetChanged();
//                    swipeRefreshLayout.setRefreshing(false);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.e("abc", String.valueOf(response));
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context,"Failed"+ error.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(request);
//    }


    public void GetData() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL , null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                CheckConnection.showToast_Short(context,"Welcome to Ecommerce App");
                try {
                    JSONArray jsonArray = response.getJSONArray("products");
                    for(int i = 0;i<response.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        int id = Integer.parseInt(jsonObject.getString("id"));
                        String name = jsonObject.getString("name");
                        String price = jsonObject.getString("price");

                        //convert Media
                        String media = jsonObject.getString("media");
                        Gson gson = new Gson();
                        Media mediaa;
                        mediaa = gson.fromJson(media,Media.class);


                        int categoryID = Integer.parseInt(jsonObject.getString("categoryID"));
                        String description = jsonObject.getString("description");

                        productArrayList.add(new Product(name,mediaa,price));
                        productArrayList.add(new Product(id,categoryID,name,mediaa,description,price));
                        Log.e("Response", String.valueOf(response));

//                        Intent intent = new Intent(getActivity(), ActivityProductDetail.class);
//                            intent.putExtra("name", name);
//                            intent.putExtra("media", media);
//                            intent.putExtra("price",price);
//                            intent.putExtra("description", description);
//                            intent.putExtra("categoryId", categoryID);
//                            startActivity(intent);
                    }



//                    productAdapter = new ProductAdapter(context,productArrayList,this);
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
//            productAdapter = new ProductAdapter(context,productArrayList);
//            recycler_view.setAdapter(productAdapter);
//        }catch (Exception e){
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

        productAdapter = new ProductAdapter(getActivity(),productArrayList,this);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onContactSelected(Product product) {
        Intent intent = new Intent(getActivity(), ActivityProductDetail.class);
        intent.putExtra("id", product.getId());
        intent.putExtra("name", product.getName());
        intent.putExtra("media", product.getMedia().getUrl());
        intent.putExtra("price", product.getPrice());
        intent.putExtra("description", product.getDescription());
        intent.putExtra("categoryId", product.getCategoryID());
        startActivity(intent);
    }
}