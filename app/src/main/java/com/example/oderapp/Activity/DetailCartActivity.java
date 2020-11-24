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
    Button btnAddMuaCart;
    ImageView btnAddCart;
    TextView txtvDescriptionCart,txtvPriceCart,txtvNameCart;
    ImageView imgAvatarCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cart);
        mapping();
        init();

    }

    private void init() {
        //gọi dữ liệu từ Intent
        Intent data = getIntent();
        int id = (int) data.getSerializableExtra("id");
        String name = (String) data.getSerializableExtra("getName");
        int price = (int) data.getSerializableExtra("getPrice");
        String description = (String) data.getSerializableExtra("getDescription");

//        String categoryid = (String) data.getSerializableExtra("categoryid");
//        Log.d("AACCC",name+""+price+""+description);

        // Gán dữ liệu vào view
        txtvNameCart.setText(name);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtvPriceCart.setText("Giá: "+decimalFormat.format(price)+" đ");
        txtvDescriptionCart.setText(description);
//        String categoryid = (String) data.getSerializableExtra("categoryid");
        String avatar = (String) data.getSerializableExtra("getAvatar");
        Picasso.get().load(avatar)
                .placeholder(R.drawable.loader)
                .error(R.drawable.noimage)
                .into(imgAvatarCart);

        // xử lí sự kiện nút bấm
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddMuaCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AACCC",name+""+price+""+description);
                Toast.makeText(DetailCartActivity.this, ""+name+""+price+""+description, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DetailCartActivity.this, CartActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("getName",name);
                intent.putExtra("getPrice",price);
                intent.putExtra("getAvatar",avatar);
                intent.putExtra("getDescription",description);
//                intent.putExtra("categoryid",categoryid);
                startActivity(intent);
            }
        });

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