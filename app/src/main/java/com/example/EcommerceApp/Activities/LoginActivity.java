package com.example.EcommerceApp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.EcommerceApp.Fragments.ProfileFragment;
import com.example.EcommerceApp.Model.LoginResponse;
import com.example.EcommerceApp.Service.RestClient;
import com.example.EcommerceApp.Service.RestMethods;
import com.example.EcommerceApp.R;
import com.example.EcommerceApp.Service.SharedPref;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity{
    Context context;
    public SharedPreferences.Editor loginPrefEditor;
    private String TAG = LoginActivity.class.getSimpleName();

    private EditText editTextMail, editTextPassWord;
    private TextView txt_Register,txt_forgotPassword;
    private Button btn_login;
    private TextInputLayout textInputLayout,text_input_password_toggle;
    private ProgressBar progress_;

    RestMethods restMethods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        restMethods = RestClient.buildHTTPClient();

         text_input_password_toggle = (TextInputLayout) findViewById(R.id.text_input_password_toggle);
         textInputLayout = findViewById(R.id.textInputLayout);
         editTextMail = findViewById(R.id.editTextMail);
         editTextPassWord = findViewById(R.id.editTextPassWord);
         btn_login = findViewById(R.id.btn_login);
//         loginButton = findViewById(R.id.login_button);
//         loginButton.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 facebookLogin();
//             }
//         });
         progress_ = findViewById(R.id.progress_);
         txt_forgotPassword = findViewById(R.id.txt_forgotPassword);
         txt_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ResetActivity.class);
                startActivity(i);
            }
        });
        txt_Register = findViewById(R.id.txt_Register);
        txt_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

    }
    private void doLogin(){
        String email = editTextMail.getText().toString();
        String password = editTextPassWord.getText().toString();
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9lbWFpbGFkZHJlc3MiOiJvbGdhbmljb2xhczRAeHl6LmNvbSIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvcm9sZSI6IkN1c3RvbWVyIiwianRpIjoiYjA4NGQ5OTctNWZhMi00Y2MxLWJhNmQtZTU3ZTEyM2MyM2JiIiwiZXhwIjoxNjA3NDI5MDE1LCJpc3MiOiJodHRwczovL2xvY2FsaG9zdDo1MDAxLyIsImF1ZCI6Imh0dHBzOi8vbG9jYWxob3N0OjUwMDEvIn0.lLsmV5JPJULwjoufd9AO7mSEOJ40on4DktviKCdCqxw";

//        Bundle bundle = new Bundle();
//        bundle.putString("message", email);
//        ProfileFragment fragInfo = new ProfileFragment();
//        fragInfo.setArguments(bundle);

        if (TextUtils.isEmpty(editTextMail.getText().toString())) {
            textInputLayout.setError("No Email Provided!");
            return;
        }
        if (!isValidEmail(editTextMail.getText().toString())) {
            textInputLayout.setError("Email is invalid!");
            return;
        }
        if (TextUtils.isEmpty(editTextPassWord.getText().toString())) {
            text_input_password_toggle.setError("No Password Entered!");
            return;
        }
        showLoading();
        LoginResponse loginResponse = new LoginResponse(token,email,password);
        restMethods.login("Bearer "+token,loginResponse).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    response.body();
                    String token = response.body().getToken();
                    SharedPreferences preferences = getApplication().getSharedPreferences("MY_APP",Context.MODE_PRIVATE);
                    preferences.edit().putString("TOKEN",token).apply();

                    Log.e(TAG, "Response: " + response.body().getToken());
                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);


                }

//                SharedPref prefManager = new SharedPref("myprefs",context);
//                prefManager.saveToken(token);

//                Log.i(TAG, "Response: " + response.body());
                HideLoading();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, "Response: " + t.getMessage());
                HideLoading();
            }
        });
    }
    public boolean isValidEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public void showLoading(){
        progress_.setVisibility(View.VISIBLE);
    }
    public void HideLoading(){
        progress_.setVisibility(View.GONE);
    }

}
