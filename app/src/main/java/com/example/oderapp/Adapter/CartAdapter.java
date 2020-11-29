package com.example.oderapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oderapp.Activity.CartActivity;
import com.example.oderapp.Activity.DetailCartActivity;
import com.example.oderapp.Model.Cart_Model;
import com.example.oderapp.Model.DashboardSanPham;
import com.example.oderapp.R;
import com.example.oderapp.inteface.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    CartActivity context;
    int layout;
    List<Cart_Model> list;

    public CartAdapter(CartActivity context, int layout, List<Cart_Model> list) {
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
        Cart_Model cart_model = list.get(position);
        Picasso.get().load(cart_model.getAvatar())
                .placeholder(R.drawable.loader)
                .error(R.drawable.noimage)
                .into(holder.imgCartCart);
        holder.txtvCartName.setText(cart_model.getName());
        holder.txtvCartPrice.setText(cart_model.getPrice()+"đ");
        holder.txtvCartQuantity.setText(cart_model.getQuantity()+"");
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.UpdateQuantityCart(list.get(position), "minus");
            }
        });

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.UpdateQuantityCart(list.get(position), "plus");
            }
        });
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean islongClick) {
                if (!islongClick){
                    Toast.makeText(context, ""+cart_model.getId(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        ImageView imgCartCart;
        TextView txtvCartName,txtvCartPrice,txtvCartQuantity;
        Button btnMinus,btnPlus;

        private ItemClickListener itemClickListener;
        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
        public ViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;
        }
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCartCart = itemView.findViewById(R.id.imgCartCart);
            txtvCartName = itemView.findViewById(R.id.txtvCartName);
            txtvCartPrice = itemView.findViewById(R.id.txtvCartPrice);
            txtvCartQuantity = itemView.findViewById(R.id.txtvCartQuantity);
            btnMinus = itemView.findViewById(R.id.btnMinusCart);
            btnPlus = itemView.findViewById(R.id.btnPlusCart);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return false;
        }
    }
}
