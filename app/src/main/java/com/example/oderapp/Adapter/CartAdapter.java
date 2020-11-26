package com.example.oderapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oderapp.Activity.CartActivity;
import com.example.oderapp.Model.Cart_Model;
import com.example.oderapp.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends  BaseAdapter {
    CartActivity context;
    int layout;
    List<Cart_Model> list;

    public CartAdapter(CartActivity context, int layout, List<Cart_Model> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        ImageView imgProduct = convertView.findViewById(R.id.imgCart);
        TextView txtvName = convertView.findViewById(R.id.txtvName);
        TextView txtvPrice = convertView.findViewById(R.id.txtvPrice);
        TextView txtvQuantity = convertView.findViewById(R.id.txtvQuantity);

        Button btnMinus = convertView.findViewById(R.id.btnMinus);
        Button btnPlus = convertView.findViewById(R.id.btnPlus);

        Cart_Model cart = list.get(position);

        Picasso.get().load(cart.getAvatar()).into(imgProduct);
        txtvName.setText(cart.getName());

        DecimalFormat formatter = new DecimalFormat("###,###,##0");
        String price = formatter.format(Double.parseDouble(cart.getPrice()+""))+" VNĐ";
        txtvPrice.setText("Giá "+price);

        txtvQuantity.setText(cart.getQuantity()+"");

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.UpdateQuantityCart(list.get(position), "minus");
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.UpdateQuantityCart(list.get(position), "plus");
            }
        });

        return convertView;
    }
}
