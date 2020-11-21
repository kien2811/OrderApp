package com.example.oderapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oderapp.Fragment.HomeFragment;
import com.example.oderapp.Model.Product_suggestion;
import com.example.oderapp.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public
class Product_suggestion_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_LOADING = 2;
    private List<Product_suggestion> mList_Product_suggestion;
    private boolean isLoadingAdd;
    public Product_suggestion_Adapter() {
    }
    @Override
    public int getItemViewType(int position) {
        if(mList_Product_suggestion != null && position == mList_Product_suggestion.size()-1 && isLoadingAdd){
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }
    public void setmList_Product_suggestion(List<Product_suggestion> mList_Product_suggestion) {
        this.mList_Product_suggestion = mList_Product_suggestion;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(TYPE_ITEM == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_suggestion,parent,false);
            return new Suggestion_ViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return  new LoadingViewHolder(view);
        }
    }
    public class Suggestion_ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtproduct_suggestion_price,txtproduct_suggestion_name;
        private ImageView imgproduct_suggestion;
        public Suggestion_ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtproduct_suggestion_price = itemView.findViewById(R.id.txtproduct_suggestion_price);
            txtproduct_suggestion_name = itemView.findViewById(R.id.txtproduct_suggestion_name);
            imgproduct_suggestion = itemView.findViewById(R.id.imgproduct_suggestion);
        }
    }
    public class LoadingViewHolder extends RecyclerView.ViewHolder{
        private ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType()==TYPE_ITEM){
            Product_suggestion product_suggestion = mList_Product_suggestion.get(position);
            Suggestion_ViewHolder suggestion_viewHolder = (Suggestion_ViewHolder) holder;
            suggestion_viewHolder.txtproduct_suggestion_name.setText(product_suggestion.getName());

            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            suggestion_viewHolder.txtproduct_suggestion_price.setText("Giá: "+decimalFormat.format(product_suggestion.getPrice())+"đ");


            Picasso.get().load(product_suggestion.getImage())
                    .placeholder(R.drawable.loader)
                    .error(R.drawable.noimage)
                    .into(((Suggestion_ViewHolder) holder).imgproduct_suggestion);
        }
    }

    @Override
    public int getItemCount() {
        if(mList_Product_suggestion != null){
            return mList_Product_suggestion.size();
        }
        return 0;
    }
    public  void addFooterLoading(){
        isLoadingAdd = true;
    }
    public  void  removeFooterLoading(){
        isLoadingAdd = false;
        int posstion = mList_Product_suggestion.size()-1;
        Product_suggestion product_suggestion = mList_Product_suggestion.get(posstion);
        if (product_suggestion!= null){
            mList_Product_suggestion.remove(posstion);
            notifyItemRemoved(posstion);
        }
    }
}
