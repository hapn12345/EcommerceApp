package com.example.EcommerceApp.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.EcommerceApp.MainActivity;
import com.example.EcommerceApp.Model.LoginResponse;
import com.example.EcommerceApp.Service.RestClient;
import com.example.EcommerceApp.Service.RestMethods;
import com.example.EcommerceApp.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.textfield.TextInputLayout;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity{

    private String TAG = LoginActivity.class.getSimpleName();

    private EditText editTextMail, editTextPassWord;
    private TextView txt_Register,txt_forgotPassword;
    private Button btn_login;
    private TextInputLayout textInputLayout,text_input_password_toggle;
    private ProgressBar progress_;
    //private LoginButton loginButton;
    //private CallbackManager callbackManager;


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
        LoginResponse loginResponse = new LoginResponse(email,password);
        restMethods.login(loginResponse).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                Log.i(TAG, "Response: " + response.body());
                HideLoading();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
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
    }public void HideLoading(){
        progress_.setVisibility(View.GONE);
    }
//    public void facebookLogin(){
//        callbackManager = new CallbackManager.Factory().create();
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Intent i = new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(i);
//            }
//
//            @Override
//            public void onCancel() {
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//        });
//    }

}
