package com.example.EcommerceApp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.EcommerceApp.Model.RegisterResponse;
import com.example.EcommerceApp.R;
import com.example.EcommerceApp.Service.CheckConnection;
import com.example.EcommerceApp.Service.RestClient;
import com.example.EcommerceApp.Service.RestMethods;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextName,edt_mail,edt_password,edt_RePassword;
    private Button btn_reg;
    private ProgressBar progress_;
    private TextInputLayout textInputName,textInputEmail,textInputPassword,textInputRepassword;
    private RestMethods restMethods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        restMethods = RestClient.buildHTTPClient();

        editTextName = findViewById(R.id.editTextName);
        edt_mail = findViewById(R.id.edt_mail);
        edt_password = findViewById(R.id.edt_password);
        edt_RePassword = findViewById(R.id.edt_RePassword);
        textInputName = findViewById(R.id.textInputName);
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputRepassword = findViewById(R.id.textInputRepassword);


        btn_reg = findViewById(R.id.btn_reg);
        progress_ = findViewById(R.id.progress_);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            String name = editTextName.getText().toString();
            String mail = edt_mail.getText().toString();
            String password = edt_password.getText().toString();
            @Override
            public void onClick(View v) {
                doRegister(name,mail,password);
            }
        });

    }

    private void doRegister(String user_name,String user_mail,String user_pass) {
        if (TextUtils.isEmpty(editTextName.getText().toString())) {
            textInputName.setError("Nhap Ten");
            return;
        }
        if (!isValidEmail(edt_mail.getText().toString())) {
            textInputEmail.setError("Nhap Email");
            return;
        }
        if (TextUtils.isEmpty(edt_password.getText().toString())) {
            textInputPassword.setError("Nhap password");
            return;
        }
        if (TextUtils.isEmpty(edt_RePassword.getText().toString())) {
            textInputRepassword.setError("Nhap password!!");
            return;
        }
        showLoading();
        RegisterResponse registerResponse = new RegisterResponse(user_name,user_mail,user_pass);
        restMethods.register(registerResponse).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
                HideLoading();
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                CheckConnection c = new CheckConnection();
                c.showToast_Short(RegisterActivity.this,"Loi Server");
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