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
import com.example.oderapp.Model.DashboardSanPham;
import com.example.oderapp.R;
import com.example.oderapp.inteface.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHoldel> {
    Context context;
    int layout;
    List<DashboardSanPham> list;

    public SearchAdapter(Context context, int layout, List<DashboardSanPham> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoldel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,null);
        ViewHoldel viewHodel = new ViewHoldel(view);
        return viewHodel;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoldel holder, int position) {
        DashboardSanPham search = list.get(position);
        if(search != null){
            holder.txtproduct_suggestion_name.setText(search.getName());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            holder.txtproduct_suggestion_price.setText("Giá: "+decimalFormat.format(search.getPrice())+" đ");
            Picasso.get().load(search.getAvatar()).
                    placeholder(R.drawable.loader).
                    error(R.drawable.noimage).
                    into(holder.imgproduct_suggestion);
        }
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean islongClick) {
                if (!islongClick){

                    Intent intent = new Intent(context, DetailCartActivity.class);

                    intent.putExtra("id",search.getId());
                    intent.putExtra("getName",search.getName());
                    intent.putExtra("getPrice",search.getPrice());
                    intent.putExtra("getAvatar",search.getAvatar());
                    intent.putExtra("getDescription",search.getDescription());
                    intent.putExtra("categoryid",search.getCategoryid());
                    intent.putExtra("getAmount",search.getAmount());

                    context.startActivity(intent);



                }
//                else {
//                    Intent intent = new Intent(context, DetailCartActivity.class);
//                    intent.putExtra("id",search.getId());
//                    intent.putExtra("getName",search.getName());
//                    intent.putExtra("getPrice",search.getPrice());
//                    intent.putExtra("getAvatar",search.getImage());
//                    intent.putExtra("getDescription",search.getDetail());
//                    intent.putExtra("product", search);
//                    context.startActivity(intent);
//                    Toast.makeText(context, "long click"+search, Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoldel extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ItemClickListener itemClickListener;
        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
        public ViewHoldel(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;
        }


        TextView txtproduct_suggestion_name,txtproduct_suggestion_price;
        ImageView imgproduct_suggestion;
        public ViewHoldel(@NonNull View itemView) {
            super(itemView);
            txtproduct_suggestion_price = itemView.findViewById(R.id.txtproduct_suggestion_price);
            imgproduct_suggestion = itemView.findViewById(R.id.imgproduct_suggestion);
            txtproduct_suggestion_name = itemView.findViewById(R.id.txtproduct_suggestion_name);

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
