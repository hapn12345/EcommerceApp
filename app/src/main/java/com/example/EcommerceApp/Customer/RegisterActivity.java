package com.example.EcommerceApp.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.EcommerceApp.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextName,edt_mail,edt_password,edt_RePassword;
    private TextView txtRegister;
    private ImageView logo_register;
    private Button btn_reg;
    private ProgressBar progress_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextName = findViewById(R.id.editTextName);
        edt_mail = findViewById(R.id.edt_mail);
        edt_password = findViewById(R.id.edt_password);
        edt_RePassword = findViewById(R.id.edt_RePassword);

        txtRegister = findViewById(R.id.txtRegister);
        logo_register = findViewById(R.id.logo_register);
        btn_reg = findViewById(R.id.btn_reg);
        progress_ = findViewById(R.id.progress_);

    }
}