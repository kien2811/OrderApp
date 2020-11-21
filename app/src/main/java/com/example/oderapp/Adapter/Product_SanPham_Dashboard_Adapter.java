package com.example.oderapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oderapp.Model.Product_suggestion;
import com.example.oderapp.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class Product_SanPham_Dashboard_Adapter extends RecyclerView.Adapter<Product_SanPham_Dashboard_Adapter.ViewHolder> {
    Context context;
    int layout;
    List<Product_suggestion> list;

    public Product_SanPham_Dashboard_Adapter(Context context, int layout, List<Product_suggestion> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,null);
        Product_SanPham_Dashboard_Adapter.ViewHolder viewHolder = new Product_SanPham_Dashboard_Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product_suggestion p = list.get(position);
        holder.txtvName.setText(p.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtvPrice.setText("Giá: "+decimalFormat.format(p.getPrice())+"đ");
        Picasso.get().load(p.getImage())
                .placeholder(R.drawable.loader)
                .error(R.drawable.noimage)
                .into(holder.imgAvatar);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtvPrice,txtvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtvPrice = itemView.findViewById(R.id.txtvPrice);
            txtvName = itemView.findViewById(R.id.txtvName);
        }
    }
}
