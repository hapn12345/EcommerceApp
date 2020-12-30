package com.example.EcommerceApp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.EcommerceApp.Activities.ActivityProduct;
import com.example.EcommerceApp.Adapter.CategoryAdapter;
import com.example.EcommerceApp.Config;
import com.example.EcommerceApp.Model.Category;
import com.example.EcommerceApp.R;
import com.example.EcommerceApp.Service.CheckConnection;
import com.example.EcommerceApp.Service.MyDividerItemDecoration;
import com.example.EcommerceApp.Service.RestMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Categories_fragment extends Fragment implements CategoryAdapter.ContactsAdapterListener {

    private RecyclerView recyclerView;
    private ArrayList<Category> categoryArrayList;
    private CategoryAdapter mAdapter;
//    private SwipeRefreshLayout swipeRefreshLayout = null;
    Config config;
    Context context;
    public static final String URL = "https://7a577d781a6a.ngrok.io/api/customer/product/categories?limit=20\n";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GetData();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        setHasOptionsMenu(true);
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout_home);

        recyclerView = view.findViewById(R.id.recycler_view_categories);
        categoryArrayList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new CategoryAdapter(getActivity(),categoryArrayList,this);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

//        onRefresh();

         return view;
    }


    private void GetData() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("categories");
                    for (int i = 0; i<response.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        categoryArrayList.add(new Category(id,name));

                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
//                        swipeRefreshLayout.setRefreshing(false);
                        Log.e("JSON" , String.valueOf(response));
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
//    public void onRefresh(){
//        categoryArrayList.clear();
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                GetData();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        GetData();
//    }

    @Override
    public void onContactSelected(Category category) {
        Intent intent = new Intent(getActivity(),ActivityProduct.class);
        intent.putExtra("id",category.getCategoryID());
        intent.putExtra("name",category.getCategory_name());
        startActivity(intent);

    }
}