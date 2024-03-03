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
import com.example.e_shop.models.NewProductsModel;
import com.example.e_shop.models.ShowAllModel;

import org.w3c.dom.Text;

import java.util.List;

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllAdapter.ViewHolder> {

    private Context context;
    private List<ShowAllModel> list;

    public ShowAllAdapter(Context context, List<ShowAllModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate
                (R.layout.show_all_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.item_image);
        holder.item_name.setText(list.get(position).getName());
        holder.item_cost.setText("$"+list.get(position).getPrice());

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

        ImageView item_image;
        TextView item_cost, item_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_image = itemView.findViewById(R.id.item_image);
            item_cost = itemView.findViewById(R.id.item_cost);
            item_name = itemView.findViewById(R.id.item_nam);

        }
    }
}
