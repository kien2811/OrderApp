package com.example.oderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.oderapp.Adapter.CartAdapter;
import com.example.oderapp.Model.Cart_Model;
import com.example.oderapp.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    Toolbar toolbarCart;
    RecyclerView recyclerViewCart;
    List<Cart_Model> list;
    CartAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mapping();
        init();
    }

    private void init() {
        //gọi dữ liệu từ Intent
        Intent data = getIntent();
        if (data != null ) {
            int id = (int) data.getSerializableExtra("id");
            String name = (String) data.getSerializableExtra("getName");
            int price = (int) data.getSerializableExtra("getPrice");
            String description = (String) data.getSerializableExtra("getDescription");
            String avatar = (String) data.getSerializableExtra("getAvatar");

//        String categoryid = (String) data.getSerializableExtra("categoryid");
//        Log.d("AACCC",name+""+price+""+description);

            // Gán dữ liệu vào listview
            list = new ArrayList<>();
            adapter = new CartAdapter(CartActivity.this, R.layout.line_cart, list);
            recyclerViewCart.setHasFixedSize(true);
            recyclerViewCart.setLayoutManager(new LinearLayoutManager(CartActivity.this));
            recyclerViewCart.setAdapter(adapter);
            adapter.notifyDataSetChanged();


            list.add(new Cart_Model(id, name, price, avatar, description, 1, 1));
        }
    }

    private void mapping() {
        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        toolbarCart = findViewById(R.id.toolbarCart);
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Giỏ hàng");
        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}