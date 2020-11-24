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

import java.util.List;

public class Product_suggestion_Adapter extends RecyclerView.Adapter<Product_suggestion_Adapter.ViewHolder> {
    Context context;
    int layout;
    private List<Product_suggestion> productSuggestionList;

    public void Product_suggestion_Adapter(Context context, int layout, List<Product_suggestion> productSuggestionList) {
        this.context = context;
        this.layout = layout;
        this.productSuggestionList = productSuggestionList;
        notifyDataSetChanged();
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
        Product_suggestion product_suggestion=productSuggestionList.get(position);
        if(product_suggestion != null){
            holder.txtproduct_suggestion_name.setText(product_suggestion.getName());
            holder.txtproduct_suggestion_price.setText(product_suggestion.getPrice());
            Picasso.get().load(product_suggestion.getImage()).
                    placeholder(R.drawable.loader).
                    error(R.drawable.noimage).
                    into(holder.imgproduct_suggestion);
        }
    }

    @Override
    public int getItemCount() {
        return productSuggestionList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtproduct_suggestion_name,txtproduct_suggestion_price;
        ImageView imgproduct_suggestion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtproduct_suggestion_price = itemView.findViewById(R.id.txtproduct_suggestion_price);
            imgproduct_suggestion = itemView.findViewById(R.id.imgproduct_suggestion);
            txtproduct_suggestion_name = itemView.findViewById(R.id.txtproduct_suggestion_name);
        }
    }
}
