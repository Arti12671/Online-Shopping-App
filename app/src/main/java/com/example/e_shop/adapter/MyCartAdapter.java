package com.example.e_shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_shop.R;
import com.example.e_shop.models.MyCartModel;
import com.example.e_shop.models.NewProductsModel;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    private Context context;
    private List<MyCartModel> list;
    int totalAmount = 0;

    public MyCartAdapter(Context context, List<MyCartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate
                (R.layout.my_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.date.setText(list.get(position).getCurrentDate());
        holder.time.setText(list.get(position).getCurrentTime());
        holder.price.setText(list.get(position).getProductPrice()+"$");
        holder.name.setText(list.get(position).getProductName());
        holder.total_quantity.setText(list.get(position).getTotalQuantity());
        holder.total_price.setText(String.valueOf(list.get(position).getTotalPrice()));

        //Total amount pass to a cart
        totalAmount = totalAmount + list.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",totalAmount);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date,time,price,name,total_quantity,total_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.current_date);
            time = itemView.findViewById(R.id.current_time);
            price = itemView.findViewById(R.id.product_price);
            name = itemView.findViewById(R.id.product_name);
            total_quantity = itemView.findViewById(R.id.total_quantity);
            total_price = itemView.findViewById(R.id.total_price);
        }
    }
}
