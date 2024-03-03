package com.example.e_shop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_shop.R;
import com.example.e_shop.models.AddressModel;
import com.example.e_shop.models.NewProductsModel;

import java.util.List;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private Context context;
    private List<AddressModel> list;
    SelectedAddress selectedAddress;

    private RadioButton selectedRadioBtn;

    public AddressAdapter(Context context, List<AddressModel> list, SelectedAddress selectedAddress) {
        this.context = context;
        this.list = list;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate
                (R.layout.address_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.address_add.setText(list.get(position).getUserAddress());
        holder.select_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AddressModel address : list){
                    address.setSelected(false);
                }
                list.get(position).setSelected(true);

                if (selectedRadioBtn != null){
                    selectedRadioBtn.setChecked(false);
                }
                selectedRadioBtn = (RadioButton) v;
                selectedRadioBtn.setChecked(true);
                selectedAddress.setAddress(list.get(position).getUserAddress());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView address_add;
        RadioButton select_address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            address_add = itemView.findViewById(R.id.address_add);
            select_address = itemView.findViewById(R.id.select_address);
        }
    }

    public interface SelectedAddress{
        void setAddress(String address);
    }

}
