package com.example.EcommerceApp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EcommerceApp.Model.Category;
import com.example.EcommerceApp.Model.Product;
import com.example.EcommerceApp.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private ContactsAdapterListener listener;
    private Context context;
    private ArrayList<Category> categoryArrayList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView category_name;
//        public ImageView category_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            category_name = itemView.findViewById(R.id.category_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onContactSelected(categoryArrayList.get(getAdapterPosition()));
                }
            });
        }
    }
    public CategoryAdapter(Context context, ArrayList<Category> categoryArrayList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.categoryArrayList = categoryArrayList;
    }
    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        final Category category = categoryArrayList.get(position);
        holder.category_name.setText(category.getCategory_name());
//        holder.product_count.setText(category.getProduct_count()+ " " + "sản phẩm");
//        String url = category.getMedia().getUrl();

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(6)
                .oval(false)
                .build();

//        Picasso.with(context)
//                .load(url)
//                .placeholder(R.drawable.fill_heart)
//                .resize(250, 250)
//                .centerCrop()
//                .transform(transformation)
//                .into(holder.category_image);

    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Category category);
    }
}
