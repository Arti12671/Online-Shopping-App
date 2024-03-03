package com.example.e_shop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_shop.R;
import com.example.e_shop.activities.DetailedActivity;
import com.example.e_shop.models.CategoryModel;
import com.example.e_shop.models.NewProductsModel;

import java.util.List;

public class NewProductsAdapter extends RecyclerView.Adapter<NewProductsAdapter.ViewHolder> {

    private Context context;
    private List<NewProductsModel> list;

    public NewProductsAdapter(Context context, List<NewProductsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate
                (R.layout.new_products,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.new_img);
        holder.new_product_name.setText(list.get(position).getName());
        holder.new_price.setText(String.valueOf(list.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed",list.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView new_img;
        TextView new_product_name,dollar,new_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            new_img = itemView.findViewById(R.id.new_img);
            new_product_name = itemView.findViewById(R.id.new_product_name);
            dollar = itemView.findViewById(R.id.dollar);
            new_price = itemView.findViewById(R.id.new_price);
        }
    }
}
