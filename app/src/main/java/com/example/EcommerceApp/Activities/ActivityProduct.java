package com.example.EcommerceApp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import androidx.appcompat.widget.SearchView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.EcommerceApp.Adapter.ProductAdapter;
import com.example.EcommerceApp.Config;
import com.example.EcommerceApp.Model.Media;
import com.example.EcommerceApp.Model.Product;
import com.example.EcommerceApp.R;
import com.example.EcommerceApp.Service.CheckConnection;
import com.example.EcommerceApp.Service.RestMethods;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityProduct extends AppCompatActivity implements ProductAdapter.ContactsAdapterListener{

    private RecyclerView recyclerView;
    private ArrayList<Product> productArrayList;
    private ProductAdapter mAdapter;
    private SearchView searchView;
    SwipeRefreshLayout swipeRefreshLayout = null;
    private String category_id,category_name;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        category_id = intent.getStringExtra("id");
        category_name = intent.getStringExtra("name");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(category_name);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recycler_view);
        productArrayList = new ArrayList<>();
        mAdapter = new ProductAdapter(this,productArrayList,this::onContactSelected);

        recyclerView.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);

        getData();
        onRefresh();

    }

    private void onRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productArrayList.clear();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (CheckConnection.haveNetworkConnection(ActivityProduct.this)) {
                            swipeRefreshLayout.setRefreshing(false);
                            getData();
                        } else {
                            swipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(getApplicationContext(), "no internet", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, 1500);
            }
        });
    }

    private void getData() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Config.API + category_id + "/product?limit=20", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("products");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        int id = Integer.parseInt(jsonObject.getString("id"));
                        String name = jsonObject.getString("name");
                        String price = jsonObject.getString("price");

                        //convert Media
                        String media = jsonObject.getString("media");
                        Gson gson = new Gson();
                        Media mediaa;
                        mediaa = gson.fromJson(media, Media.class);


                        int categoryID = Integer.parseInt(jsonObject.getString("categoryID"));
                        String description = jsonObject.getString("description");

                        productArrayList.add(new Product(name, mediaa, price));
                        productArrayList.add(new Product(id, categoryID, name, mediaa, description, price));
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
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"FAILED",Toast.LENGTH_LONG).show();
                Log.d("Product",error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
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
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }
        @Override
    public void onContactSelected(Product product) {
        Intent intent = new Intent(getApplicationContext(), ActivityProductDetail.class);
        intent.putExtra("id", product.getId());
        intent.putExtra("name", product.getName());
        intent.putExtra("media", product.getMedia().getUrl());
        intent.putExtra("price", product.getPrice());
        intent.putExtra("description", product.getDescription());
        intent.putExtra("categoryId", product.getCategoryID());
        startActivity(intent);
    }
}