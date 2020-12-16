package com.example.oderapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oderapp.Activity.DetailCartActivity;
import com.example.oderapp.Model.Product_new;
import com.example.oderapp.R;
import com.example.oderapp.inteface.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Product_New_Adapter extends RecyclerView.Adapter<Product_New_Adapter.ViewHolder> {
    Context context;
    int layout;
    List<Product_new> list;


    public void Product_New_Adapter(Context context, int layout, List<Product_new> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,null);
        Product_New_Adapter.ViewHolder viewHolder = new Product_New_Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product_new p = list.get(position);
        holder.txtproduct_hot.setText(p.getName());
        Picasso.get().load(p.getImage())
                .placeholder(R.drawable.loader)
                .error(R.drawable.noimage)
                .into(holder.imgproduct_hot);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean islongClick) {
                if(!islongClick){
                    Intent intent = new Intent(context, DetailCartActivity.class);
                    intent.putExtra("id",p.getId());
                    intent.putExtra("getName",p.getName());
                    intent.putExtra("getPrice",p.getPrice());
                    intent.putExtra("getAvatar",p.getImage());
                    intent.putExtra("getDescription",p.getDetails());
                    intent.putExtra("categoryid",p.getProduct_id());
                    intent.putExtra("getAmount",p.getAmount());
                    context.startActivity(intent);
//                    Toast.makeText(context, "long click"+p, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, "click"+p, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
        public ViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;
        }

            ImageView imgproduct_hot;
            TextView txtproduct_hot;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imgproduct_hot = itemView.findViewById(R.id.imgproduct_hot);
                txtproduct_hot = itemView.findViewById(R.id.txtproduct_hot);
                itemView.setOnLongClickListener(this);
                itemView.setOnClickListener(this);
            }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }
        @Override
        public boolean onLongClick(View view) {
                itemClickListener.onClick(view,getAdapterPosition(),true);
            return false;
        }
    }
}
