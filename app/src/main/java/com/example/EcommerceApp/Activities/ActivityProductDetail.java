package com.example.EcommerceApp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.EcommerceApp.Model.Product;
import com.example.EcommerceApp.R;
import com.example.EcommerceApp.Service.CheckConnection;
import com.example.EcommerceApp.Service.DBHelper;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class ActivityProductDetail extends AppCompatActivity {
    Context context;
    Product product;
    private int product_id,category_Id;
    private String product_name,product_image,product_description,product_price;
    private TextView txt_productName,txtPrice,show_description,txt_price;
    private ImageView ic_back,ic_like,img_detail;
    private Button btn_add_cart;
    public static DBHelper dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initComponent();
        getData();
        display();
        dbhelper = new DBHelper(this);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityProductDetail.this, CheckoutActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
    public void getData(){
        Intent i = getIntent();
        product_id = i.getIntExtra("id",0);
        product_name = i.getStringExtra("name");
        product_image = i.getStringExtra("media");
        product_price = i.getStringExtra("price");
        product_description = i.getStringExtra("description");
        category_Id = i.getIntExtra("categoryId",0);
        Log.e("Response", String.valueOf(product_name));

    }
    public void initComponent(){
        txt_productName = findViewById(R.id.txt_productName);
        txtPrice = findViewById(R.id.txtPrice);
        show_description = findViewById(R.id.show_description);
        img_detail = findViewById(R.id.img_detail);
        ic_back = findViewById(R.id.ic_back);
        ic_like = findViewById(R.id.ic_like);
        btn_add_cart = findViewById(R.id.btn_add_cart);
    }
        public void display(){
       try{
           txt_productName.setText(product_name);
           show_description.setText(product_description);

           Locale localeEN = new Locale("vi", "VN");
           NumberFormat en = NumberFormat.getInstance(localeEN);
           double product_price_db = Double.parseDouble(product_price);
           Math.ceil(product_price_db);
           en.setRoundingMode(RoundingMode.UP);
           String str1 = en.format(product_price_db);
           txtPrice.setText(str1 + " VNĐ");

           Log.e("PRICE" , String.valueOf(product_price_db));
           Log.e("Response", String.valueOf(txtPrice));
       }catch (Exception e){
           e.getMessage();
       }


        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(10)
                .oval(false)
                .build();

        Picasso.with(context).load(product_image)
                .fit()
                .centerInside()
                .placeholder(R.color.colorLoginPrimaryDark)
                .error(R.drawable.error_img)
                .transform(transformation)
                .into(img_detail);

    }
    public String currencyFormatter(String num) {
        double m = Double.parseDouble(num);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(m);
    }
    public void inputDialog() {

        try {
            dbhelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);

        View mView = layoutInflaterAndroid.inflate(R.layout.input_diaglog, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(mView);

        final EditText edtQuantity = (EditText) mView.findViewById(R.id.userInputDialog);
        alert.setCancelable(false);
        int maxLength = 3;
        edtQuantity.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        edtQuantity.setInputType(InputType.TYPE_CLASS_NUMBER);

        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String temp = edtQuantity.getText().toString();
                int quantity = 0;

                if (!temp.equalsIgnoreCase("")) {

                    quantity = Integer.parseInt(temp);

                    if (quantity <= 0) {
                        Toast.makeText(getApplicationContext(), "Số lượng trong kho không đủ", Toast.LENGTH_SHORT).show();
                    }
                     else {
                        Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();

                        if (dbhelper.isDataExist(product_id)) {
                            dbhelper.updateData(product_id, quantity, (Integer.parseInt(product_price) * quantity));
                        } else {
                            dbhelper.addData(product_id, product_name, quantity, (Integer.parseInt(product_price) * quantity), product_image);
                        }
                    }

                } else {
                    dialog.cancel();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }
    public void sendData(){
        Intent i = new Intent();
    }

}