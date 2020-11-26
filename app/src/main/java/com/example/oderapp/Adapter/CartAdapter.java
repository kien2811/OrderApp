package com.example.oderapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oderapp.Model.Cart_Model;
import com.example.oderapp.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    int layout;
    List<Cart_Model> list;

    public CartAdapter(Context context, int layout, List<Cart_Model> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart_Model p = list.get(position);
        holder.txtvName.setText(p.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtvPrice.setText(decimalFormat.format(p.getPrice())+" đ");
        holder.txtvQuantity.setText(p.getQuantity()+"");
        Picasso.get().load(p.getAvatar())
                .placeholder(R.drawable.loader)
                .error(R.drawable.noimage)
                .into(holder.imgCart);
    }
    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgCart;
        TextView txtvQuantity,txtvName,txtvPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCart = itemView.findViewById(R.id.imgCart);
            txtvName = itemView.findViewById(R.id.txtvName);
            txtvPrice = itemView.findViewById(R.id.txtvPrice);
            txtvQuantity = itemView.findViewById(R.id.txtvQuantity);
        }
    }
}
