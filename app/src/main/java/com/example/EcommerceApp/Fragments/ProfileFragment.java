package com.example.EcommerceApp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.balysv.materialripple.MaterialRippleLayout;
import com.example.EcommerceApp.Activities.LoginActivity;
import com.example.EcommerceApp.Config;
import com.example.EcommerceApp.R;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProfileFragment extends Fragment {
    Context context;
    private TextView txt_user_name,txt_user_email,txt_user_phone,txt_user_address;
    private MaterialRippleLayout btn_edit_user,btn_order_history,btn_rate,btn_share,btn_privacy,btn_logout;
    public final static String URL = "https://7a577d781a6a.ngrok.io/customer/profile";
//    LinearLayout lyt_root;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
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
        txt_user_phone = view.findViewById(R.id.txt_user_phone);
        txt_user_address = view.findViewById(R.id.txt_user_address);
        btn_edit_user = view.findViewById(R.id.btn_edit_user);
        btn_order_history = view.findViewById(R.id.btn_order_history);
        btn_rate = view.findViewById(R.id.btn_rate);
        btn_share = view.findViewById(R.id.btn_share);
        btn_privacy = view.findViewById(R.id.btn_privacy);
        btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
//        lyt_root = view.findViewById(R.id.lyt_root);
        return view;
    }

    private void logout() {
//        SharedPreferences preferences = context.getSharedPreferences(, );
    }

    @Override
    public void onResume() {
        super.onResume();
        fetch_data();
    }
    private void fetch_data() {
        //get token
        SharedPreferences preferences = getActivity().getSharedPreferences("MY_APP",Context.MODE_PRIVATE);
        String retrivedToken  = preferences.getString("TOKEN",null);//second parameter default value.
        Log.e("Key" , String.valueOf(retrivedToken));
        //volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String name = response.getString("name");
                    String email = response.getString("email");
                    String phone = response.getString("phone");
                    String address = response.getString("address");
                    txt_user_name.setText(name);
                    txt_user_email.setText(email);
                    txt_user_phone.setText(phone);
                    txt_user_address.setText(address);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("abc",String.valueOf(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            //auth bearer
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String> ();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer "+retrivedToken);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
}