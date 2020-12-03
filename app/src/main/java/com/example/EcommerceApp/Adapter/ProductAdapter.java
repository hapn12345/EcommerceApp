package com.example.EcommerceApp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EcommerceApp.Activities.ActivityProductDetail;
import com.example.EcommerceApp.Model.Media;
import com.example.EcommerceApp.Model.Product;
import com.example.EcommerceApp.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Media media;
    Product product;
    private Context context;
    private ArrayList<Product> arraySanpham;
    private ContactsAdapterListener listener;

    public ProductAdapter(Context context, ArrayList<Product> arraySanpham) {
        this.context = context;
        this.arraySanpham = arraySanpham;
    }

    @NonNull
    //init view products_recycler_view
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.products_recycler_view, parent, false);

        return new ViewHolder(itemView) ;
    }
    // set thuoc tinh tu product -> layout
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = arraySanpham.get(position);
        String url = product.getMedia().getUrl();

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(10)
                .oval(false)
                .build();

        Picasso.with(context).load(url)
                .fit()
                .centerInside()
                .placeholder(R.color.colorLoginPrimaryDark)
                .error(R.drawable.error_img)
                .transform(transformation)
                .into(holder.img_product);
        holder.txt_name_product.setText(product.getName());
        holder.txt_price.setText(currencyFormatter(product.getPrice())+ " Đ");
    }

    @Override
    public int getItemCount() {
        return arraySanpham.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView img_product;
        public TextView txt_name_product,txt_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_product = itemView.findViewById(R.id.img_product);
            txt_name_product = itemView.findViewById(R.id.txt_name_product);
            txt_price = itemView.findViewById(R.id.txt_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityProductDetail.class);
//                    intent.putExtra("ID", product.getId());
//                    intent.putExtra("name", product.getName());
//                    intent.putExtra("imageURL", product.getImageUrl());
//                    intent.putExtra("price", product.getPrice());
//                    intent.putExtra("description", product.getDescription());
//                    intent.putExtra("categoryId", product.getCategoryID());
                    context.startActivity(intent);
                }
            });
        }
    }
    public interface ContactsAdapterListener {
        void onContactSelected(Product product);
    }
    public String currencyFormatter(String num) {
        double m = Double.parseDouble(num);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(m);
    }
}
