package com.example.EcommerceApp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EcommerceApp.Model.Product;
import com.example.EcommerceApp.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> arraySanpham;
    private ContactsAdapterListener listener;

    public ProductAdapter(Context context, ArrayList<Product> arraySanpham,ContactsAdapterListener listener) {
        this.context = context;
        this.arraySanpham = arraySanpham;
        this.listener = listener;
    }

    @NonNull
    //init view products_recycler_view
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.products_recycler_view, parent, false);

        return new ViewHolder(itemView) ;
    }
    // set get thuoc tinh tu product -> layout
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = arraySanpham.get(position);
        Picasso.with(context).load(product.getImageUrl())
                .fit()
                .centerInside()
                .placeholder(R.drawable.icon_fb)
                .error(R.drawable.error_img)
                .into(holder.img_product);
        holder.txt_name_product.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txt_price.setText("Giá: " + decimalFormat.format(product.getPrice())+ "Đ");

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
        }
    }
    public interface ContactsAdapterListener {
        void onContactSelected(Product product);
    }
}
