package com.example.oderapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oderapp.Activity.DonMuaActivity;
import com.example.oderapp.Fragment.DonMuaFragment.ChoXacNhanFragment;
import com.example.oderapp.Model.Cart_Model;
import com.example.oderapp.Model.DonHang;
import com.example.oderapp.R;
import com.example.oderapp.inteface.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ChoXacNhanAdapter extends RecyclerView.Adapter<ChoXacNhanAdapter.ViewHolder> {
    ChoXacNhanFragment context;
    int layout;
    List<DonHang> list;

    public ChoXacNhanAdapter(ChoXacNhanFragment context, int layout, List<DonHang> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getContext()).inflate(layout,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHang donHang = list.get(position);
        Picasso.get().load(donHang.getAvatar())
                .placeholder(R.drawable.loader)
                .error(R.drawable.noimage)
                .into(holder.imgChoxacnhan);
        holder.txtvCartName.setText(donHang.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtvCartPrice.setText(decimalFormat.format(donHang.getPrice())+"đ");
        holder.txtvCartQuantity.setText("x"+donHang.getQuantity());
        holder.txtvStatus.setText(donHang.getStatus());
        holder.txtvSize.setText("Size : "+donHang.getName_size());
        holder.txtvsosanpham.setText(donHang.getQuantity()+" sản phẩm");
        int gia = donHang.getPrice();
        int soluong = donHang.getQuantity();
        int tong = gia * soluong;
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean islongClick) {
                context.ChuyenTrang(donHang);
            }
        });
        holder.txtvTongPrice.setText(decimalFormat.format(tong)+"đ");
        holder.HuyChoSANPHAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.UpdateData(donHang.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        ImageView imgChoxacnhan;
        TextView txtvCartName,txtvCartPrice,txtvCartQuantity,txtvStatus,txtvsosanpham,txtvTongPrice,txtvSize;
        Button deleteCart,HuyChoSANPHAM;

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
            imgChoxacnhan = itemView.findViewById(R.id.imgChoxacnhan);
            txtvCartName = itemView.findViewById(R.id.txtvCartName);
            txtvCartPrice = itemView.findViewById(R.id.txtvCartPrice);
            txtvCartQuantity = itemView.findViewById(R.id.txtvCartQuantity);
            txtvStatus = itemView.findViewById(R.id.txtvStatus);
            txtvsosanpham = itemView.findViewById(R.id.txtvsosanpham);
            txtvTongPrice = itemView.findViewById(R.id.txtvTongPrice);
            deleteCart = itemView.findViewById(R.id.deleteCart);
            HuyChoSANPHAM = itemView.findViewById(R.id.HuyChoSANPHAM);
            txtvSize = itemView.findViewById(R.id.txtvSize);

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
