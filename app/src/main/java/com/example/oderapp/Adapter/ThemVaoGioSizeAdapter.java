package com.example.oderapp.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oderapp.Activity.DetailCartActivity;
import com.example.oderapp.Model.SizeItem;
import com.example.oderapp.R;
import com.example.oderapp.inteface.ItemClickListener;

import java.util.List;

public class ThemVaoGioSizeAdapter extends RecyclerView.Adapter<ThemVaoGioSizeAdapter.ViewHolder> {
    DetailCartActivity context;
    int layout;
    List<SizeItem> list;

    int row_index = -1; //mặc định

    public ThemVaoGioSizeAdapter(DetailCartActivity context, int layout, List<SizeItem> list) {
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
        SizeItem sizeItem = list.get(position);

        holder.txtvName.setText(sizeItem.getSize()+"");

//        if (!sizeItem.isIschecked()){
//            holder.image.setImageResource(R.drawable.ic_baseline_clear_24);
//        }else {
//            holder.image.setImageResource(R.drawable.ic_baseline_check_24);
//        }
        if (sizeItem.getQuantity() == 0){
            holder.image.setImageResource(R.drawable.ic_baseline_clear_24);
            // các lần bấm
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean islongClick) {
                    Toast.makeText(context, "Sản phẩm đã hết hàng", Toast.LENGTH_SHORT).show();
                    holder.txtvName.setTextColor(Color.parseColor("#7C1C1B1B"));
                }
            });
        }else {
            holder.image.setImageResource(R.drawable.ic_baseline_check_24);
            // các lần bấm
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean islongClick) {
                    row_index = position;
                    notifyDataSetChanged();
//                    Toast.makeText(context, "THÊM VÀO GIỎ BÊN NÀY"+sizeItem.getQuantity(), Toast.LENGTH_SHORT).show();
                    context.AddVaoGio(sizeItem.getQuantity(),sizeItem.getId());
                }
            });
            // set color
            if (row_index == position){
                holder.txtvName.setTextColor(Color.parseColor("#FF0000"));
                holder.txtvName.setBackgroundColor(Color.parseColor("#FFEFEFEF"));
            }else {
                holder.txtvName.setTextColor(Color.parseColor("#000000"));
                holder.txtvName.setBackgroundColor(Color.parseColor("#FFEFEFEF"));
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        ImageView image;
        TextView txtvName;

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
            image = itemView.findViewById(R.id.imgViewSelect);
            txtvName = itemView.findViewById(R.id.txtViewSelect);


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
