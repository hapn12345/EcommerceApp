package com.example.EcommerceApp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
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
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>implements Filterable {
    Media media;
    Product product;
    private Context context;
    private ArrayList<Product> arraySanpham;
    private ArrayList<Product> productListFiltered;
    private ContactsAdapterListener listener;
    RecyclerView recyclerView;

    public ProductAdapter(Context context, ArrayList<Product> arraySanpham,ContactsAdapterListener listener) {
        this.context = context;
        this.arraySanpham = arraySanpham;
        this.productListFiltered = arraySanpham;
        this.listener = listener;
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
        try{
            final Product product = productListFiltered.get(position);
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    productListFiltered = arraySanpham;
                } else {
                    ArrayList<Product> filteredList = new ArrayList<>();
                    for (Product row : arraySanpham) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    productListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productListFiltered = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
                    listener.onContactSelected(productListFiltered.get(getAdapterPosition()));
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
