package com.example.oderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oderapp.Model.DashboardSanPham;
import com.example.oderapp.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailCartActivity extends AppCompatActivity {
    Toolbar toolbarCart;
    Button btnAddMuaCart,btnMinus,btnPlus;
    ImageView btnAddCart;
    TextView txtvDescriptionCart,txtvPriceCart,txtvNameCart,txtvQuantity;
    ImageView imgAvatarCart;
    DashboardSanPham product;
    MyDatabase myDatabase;
    Cursor cursor;
    static final String DB_NAME = "db_shop";
    static final String TABLE_NAME = "tbl_cart";
    static final String ID_FIELD = "id";
    static final String NAME_FIELD = "name";
    static final String PRICE_FIELD = "price";
    static final String AVATAR_FIELD = "avatar";
    static final String QUANTITY_FIELD = "quantity";
    static final String CATEGORYID_FIELD = "categoryid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cart);
        mapping();
        init();


    }
    private void quantitySet(String action) {
        int qtt = Integer.parseInt(txtvQuantity.getText().toString());
        if (action.equals("plus")){
            qtt += 1;
        }else if (action.equals("minus")){
            if (qtt > 1){
                qtt -= 1;
            }
        }
        txtvQuantity.setText(qtt+"");
    }

    private void init() {
        //gọi dữ liệu từ Intent
        Intent intent = getIntent();
//        DashboardSanPham  product = (DashboardSanPham) intent.getSerializableExtra("product");
//        Log.d("AAA",intent.toString());
        int id = (int) intent.getSerializableExtra("id");
        String getName = (String) intent.getSerializableExtra("getName");
        int getPrice = (int) intent.getSerializableExtra("getPrice");
        String getAvatar = (String) intent.getSerializableExtra("getAvatar");
        String getDescription = (String) intent.getSerializableExtra("getDescription");
        int categoryid = (int) intent.getSerializableExtra("categoryid");

//        Picasso.get().load(getAvatar).into(imgAvatarCart);
        txtvNameCart.setText(getName);

        DecimalFormat formatter = new DecimalFormat("###,###,###");
//        String price = formatter.format(Double.parseDouble(getPrice+""))+" VNĐ";
        txtvPriceCart.setText("Giá "+getPrice);

        txtvDescriptionCart.setText(getDescription);
        Picasso.get().load(getAvatar)
                .placeholder(R.drawable.loader)
                .error(R.drawable.noimage)
                .into(imgAvatarCart);


        //Xử lí nút số lượng
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantitySet("minus");
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantitySet("plus");
            }
        });
        // xử lí sự kiện nút bấm
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCartDatabase();
                finish();
            }
        });
        btnAddMuaCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        initActionBar();
        initDatabase();
    }
    private void initDatabase() {
        //gọi database
        myDatabase = new MyDatabase(DetailCartActivity.this, DB_NAME,null, 1);

        //tạo table
        String url_creadte = "create table if not exists "+TABLE_NAME+" ("+ID_FIELD+" integer, "+NAME_FIELD+" text, "+PRICE_FIELD+" integer, "+AVATAR_FIELD+" text, "+QUANTITY_FIELD+" integer, "+CATEGORYID_FIELD+" integer)";
        myDatabase.creadteTable(url_creadte);
    }
    private boolean checkCart() {
        cursor = myDatabase.selectData("select * from "+TABLE_NAME+" where id="+product.getId());
        if (cursor.moveToNext()){
            ContentValues cv = new ContentValues();
            int quantity = (cursor.getInt(4) + Integer.parseInt(txtvQuantity.getText().toString()));
            cv.put(QUANTITY_FIELD, quantity);
            String whereClause = "id = ?";
            String[] whereArgs = {cursor.getInt(0)+""};
            int chek = myDatabase.update(TABLE_NAME, cv, whereClause, whereArgs);
            if (chek == 1){
                Toast.makeText(this, cursor.getString(1)+" được thêm vào giỏ hàng !", Toast.LENGTH_LONG).show();
                Log.d("aaa",cursor.getString(1));
            }else {
                Toast.makeText(this, "Lỗi giỏ hàng !", Toast.LENGTH_LONG).show();
                Log.d("loi",cursor.toString());
            }
            return true;
        }
        return false;
    }
    private void addCartDatabase() {
        if (!checkCart()){
            ContentValues cv = new ContentValues();
            cv.put(ID_FIELD,product.getId());
            cv.put(NAME_FIELD,product.getName());
            cv.put(PRICE_FIELD, product.getPrice());
            cv.put(AVATAR_FIELD, product.getAvatar());
            cv.put(QUANTITY_FIELD, txtvQuantity.getText().toString());
            cv.put(CATEGORYID_FIELD, product.getCategoryid());

            long check = myDatabase.insertData(TABLE_NAME, null, cv);
            if (check > 0){
                Toast.makeText(this, product.getName()+" được thêm vào giỏ hàng !", Toast.LENGTH_LONG).show();
                Log.d("aaa",product.getName());
            }else {
                Toast.makeText(this, "Lỗi giỏ hàng !", Toast.LENGTH_LONG).show();
                Log.d("loi",product.getName());
            }
        }
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

        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        txtvQuantity = findViewById(R.id.txtvQuantity);
    }
}