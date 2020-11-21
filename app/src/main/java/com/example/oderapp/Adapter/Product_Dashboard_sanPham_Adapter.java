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

public class Product_Dashboard_sanPham_Adapter extends RecyclerView.Adapter<Product_Dashboard_sanPham_Adapter.ViewHolder> {
    Context context;
    int layout;
    List<DashboardSanPham> list;

    public Product_Dashboard_sanPham_Adapter(Context context, int layout, List<DashboardSanPham> list) {
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
        DashboardSanPham p = list.get(position);
        Picasso.get().load(p.getAvatar())
                .placeholder(R.drawable.loader)
                .error(R.drawable.noimage)
                .into(holder.imgSanPhamDashboard);
        holder.txtvName.setText(p.getName());
        holder.txtvPrice.setText(p.getPrice()+"đ");
        holder.txtvDescription.setText(p.getDescription());

        int price = p.getPrice();

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean islongClick) {
                if (islongClick){
                    Intent intent = new Intent(context, DetailCartActivity.class);
                    intent.putExtra("id",p.getId());
                    intent.putExtra("getName",p.getName());
                    intent.putExtra("getPrice",price);
                    intent.putExtra("getAvatar",p.getAvatar());
                    intent.putExtra("getDescription",p.getDescription());
                    intent.putExtra("categoryid",p.getCategoryid());
                    context.startActivity(intent);
                    Toast.makeText(context, "long click"+p, Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(context, DetailCartActivity.class);
                    intent.putExtra("id",p.getId());
                    intent.putExtra("getName",p.getName());
                    intent.putExtra("getPrice",p.getPrice());
                    intent.putExtra("getAvatar",p.getAvatar());
                    intent.putExtra("getDescription",p.getDescription());
                    intent.putExtra("categoryid",p.getCategoryid());
                    context.startActivity(intent);
                    Toast.makeText(context, "long"+p, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener {
        ImageView imgSanPhamDashboard;
        TextView txtvName,txtvPrice,txtvDescription;

        private ItemClickListener itemClickListener;
        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
        public ViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;
        }

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imgSanPhamDashboard = itemView.findViewById(R.id.imgSanPhamDashboard);
            txtvName = itemView.findViewById(R.id.txtvNameDashboard);
            txtvPrice = itemView.findViewById(R.id.txtvPriceDashboard);
            txtvDescription = itemView.findViewById(R.id.txtvDescriptionDashboard);


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
