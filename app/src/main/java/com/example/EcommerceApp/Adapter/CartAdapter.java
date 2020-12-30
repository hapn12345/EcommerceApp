package com.example.EcommerceApp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.EcommerceApp.Activities.ActivityProductDetail;
import com.example.EcommerceApp.Activities.CartActivity;
import com.example.EcommerceApp.Config;
import com.example.EcommerceApp.Model.Cart;
import com.example.EcommerceApp.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Cart> arrayCart;

    public CartAdapter(Context context, ArrayList<Cart> arrayCart) {
        this.context = context;
        this.arrayCart = arrayCart;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cartt, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try{
            holder.product_name.setText(CartActivity.product_name.get(position));

            Locale localeEN = new Locale("vi", "VN");
            NumberFormat en = NumberFormat.getInstance(localeEN);
            double product_price_db = Double.parseDouble(String.valueOf(CartActivity.sub_total_price));
            Math.ceil(product_price_db);
            en.setRoundingMode(RoundingMode.UP);
            String str1 = en.format(product_price_db);
            holder.product_price.setText(str1 + " VNĐ");

            Transformation transformation = new RoundedTransformationBuilder()
                    .cornerRadiusDp(10)
                    .oval(false)
                    .build();

            Picasso.with(context).load(CartActivity.product_image.get(position))
                    .fit()
                    .centerInside()
                    .placeholder(R.color.colorLoginPrimaryDark)
                    .error(R.drawable.error_img)
                    .transform(transformation)
                    .into(holder.product_image);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return CartActivity.product_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView product_name;
        TextView product_quantity;
        TextView product_price;
        ImageView product_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            product_quantity = itemView.findViewById(R.id.product_quantity);
            product_price = itemView.findViewById(R.id.product_price);
            product_image = itemView.findViewById(R.id.product_image);
        }
    }
}
