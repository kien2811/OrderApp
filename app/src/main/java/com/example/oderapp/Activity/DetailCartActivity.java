package com.example.oderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oderapp.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailCartActivity extends AppCompatActivity {
    Toolbar toolbarCart;
    Button btnAddMuaCart,btnAddCart;
    TextView txtvDescriptionCart,txtvPriceCart,txtvNameCart;
    ImageView imgAvatarCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cart);
        mapping();
        init();
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailCartActivity.this, ""+v, Toast.LENGTH_SHORT).show();
            }
        });
        btnAddMuaCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        Intent data = getIntent();
        int id = (int) data.getSerializableExtra("id");
        String name = (String) data.getSerializableExtra("getName");
        int price = (int) data.getSerializableExtra("getPrice");
        String description = (String) data.getSerializableExtra("getDescription");

//        String categoryid = (String) data.getSerializableExtra("categoryid");

        txtvNameCart.setText(name);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtvPriceCart.setText("Giá: "+decimalFormat.format(price)+" đ");

        txtvDescriptionCart.setText(description);

        String avatar = (String) data.getSerializableExtra("getAvatar");
        Picasso.get().load(avatar)
                .placeholder(R.drawable.loader)
                .error(R.drawable.noimage)
                .into(imgAvatarCart);
        Log.d("Cart",data.toString());
//        Toast.makeText(this, "Cart"+name+avatar+description+categoryid, Toast.LENGTH_SHORT).show();

        initActionBar();
    }

    private void initActionBar() {
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết sản phẩm");
        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private void mapping() {
        txtvDescriptionCart = findViewById(R.id.txtvDescriptionCart);
        txtvNameCart = findViewById(R.id.txtvNameCart);
        txtvPriceCart = findViewById(R.id.txtvPriceCart);
        imgAvatarCart = findViewById(R.id.imgAvatarCart);
        btnAddMuaCart = findViewById(R.id.btnAddMuaCart);
        btnAddCart = findViewById(R.id.btnAddCart);

        toolbarCart = findViewById(R.id.toolbarCart);
    }
}