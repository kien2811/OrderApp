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
import com.example.oderapp.Model.Product_suggestion;
import com.example.oderapp.R;
import com.example.oderapp.inteface.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Product_suggestion_Adapter extends RecyclerView.Adapter<Product_suggestion_Adapter.ViewHolder> {
    Context context;
    int layout;
    private List<Product_suggestion> productSuggestionList;

    public void Product_suggestion_Adapter(Context context, int layout, List<Product_suggestion> productSuggestionList) {
        this.context = context;
        this.layout = layout;
        this.productSuggestionList = productSuggestionList;
    }

    @NonNull
    @Override
    public Product_suggestion_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,null);
        Product_suggestion_Adapter.ViewHolder viewHolder = new Product_suggestion_Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product_suggestion product_suggestion = productSuggestionList.get(position);
        holder.txtproduct_suggestion_name.setText(product_suggestion.getName());
        holder.txtproduct_suggestion_price.setText(product_suggestion.getPrice()+" d");
        Picasso.get().load(product_suggestion.getImage()).
                placeholder(R.drawable.loader).
                error(R.drawable.noimage).
                into(holder.imgproduct_suggestion);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean islongClick) {
                if(!islongClick){
                    Intent intent = new Intent(context, DetailCartActivity.class);
                    intent.putExtra("id",product_suggestion.getId());
                    intent.putExtra("getName",product_suggestion.getName());
                    intent.putExtra("getPrice",product_suggestion.getPrice());
                    intent.putExtra("getAvatar",product_suggestion.getImage());
                    intent.putExtra("getDescription",product_suggestion.getDetails());
                    intent.putExtra("categoryid",product_suggestion.getProduct_id());
                    context.startActivity(intent);                    }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productSuggestionList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView txtproduct_suggestion_name,txtproduct_suggestion_price;
        ImageView imgproduct_suggestion;
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
            txtproduct_suggestion_price = itemView.findViewById(R.id.txtproduct_suggestion_price);
            imgproduct_suggestion = itemView.findViewById(R.id.imgproduct_suggestion);
            txtproduct_suggestion_name = itemView.findViewById(R.id.txtproduct_suggestion_name);
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
