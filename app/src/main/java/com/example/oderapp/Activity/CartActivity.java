package com.example.oderapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.oderapp.Adapter.CartAdapter;
import com.example.oderapp.Model.Cart_Model;
import com.example.oderapp.R;
import com.example.oderapp.util.Api;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    RecyclerView recyclerViewCart;

    List<Cart_Model> list;
    CartAdapter adapter;

    Button btnThanhtoan, btnMuahang;
    ListView lvCart;
    TextView txtvTotal;
    Cursor cursor;

    static final String DB_NAME = "db_shop";
    static final String TABLE_NAME = "tbl_cart";
    static final String ID_FIELD = "id";
    static final String NAME_FIELD = "name";
    static final String PRICE_FIELD = "price";
    static final String AVATAR_FIELD = "avatar";
    static final String QUANTITY_FIELD = "quantity";
    static final String CATEGORYID_FIELD = "categoryid";

    int total;

    NotificationManagerCompat notificationManagerCompat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        initActionBar();
        initDatabase();
        mapping();
        initButton();
//        checkProductMysql();
        initNotification();
    }

    private void initNotification() {
        this.notificationManagerCompat = NotificationManagerCompat.from(this);
    }

    private void initButton() {
        btnMuahang.setOnClickListener(this);
        btnThanhtoan.setOnClickListener(this);
    }

    private void initDatabase() {
        //gọi database
        myDatabase = new MyDatabase(CartActivity.this, DB_NAME,null, 1);
    }

    private void initActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void mapping() {
        list = getDataCart();
        adapter = new CartAdapter(CartActivity.this, R.layout.line_cart, list);
        lvCart.setAdapter(adapter);
    }

    private List<Cart_Model> getDataCart() {
        List<Cart_Model> list = new ArrayList<>();

        total = 0;
        try {
            cursor = myDatabase.selectData("select * from "+TABLE_NAME);
            while (cursor.moveToNext()){
                Cart_Model productCart = new Cart_Model();

                productCart.setId(cursor.getInt(0));
                productCart.setName(cursor.getString(1));
                productCart.setPrice(cursor.getInt(2));
                productCart.setAvatar(cursor.getString(3));
                productCart.setQuantity(cursor.getInt(4));
                productCart.setCategoryid(cursor.getInt(5));

                list.add(productCart);
                total += (cursor.getInt(2)*cursor.getInt(4));

            }

        }catch (Exception e){
            Toast.makeText(this, "Giỏ hàng trống !", Toast.LENGTH_SHORT).show();
        }

        DecimalFormat formatter = new DecimalFormat("###,###,##0");
        String price = formatter.format(Double.parseDouble(total+""))+" VNĐ";
        txtvTotal.setText(" "+price);
        return list;
    }

//    private void checkProductMysql() {
//        for (Cart_Model productCart:list) {
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.URL_GET_CHECK_PRODUCT+productCart.getId(),
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            if (response.equals("false")){
//                                deleteCartAuto(productCart.getId());
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(CartActivity.this, ""+error, Toast.LENGTH_SHORT).show();
//                }
//            });
//            requestQueue.add(stringRequest);
//        }
//
//
//    }

    private void init() {
        lvCart = findViewById(R.id.lvCart);
        toolbar = findViewById(R.id.toolbar);
        txtvTotal = findViewById(R.id.txtvTotal);
        btnThanhtoan = findViewById(R.id.btnThanhtoan);

    }

    public void UpdateQuantityCart(Cart_Model productCart, String action){
        int quantity = 0;
        cursor = myDatabase.selectData("select * from "+TABLE_NAME+" where id="+productCart.getId());



        if (action.equals("minus")){
            if (cursor.moveToNext()){
                quantity = (cursor.getInt(4) - 1);
                if (quantity == 0){
                    deleteCart(productCart);
                    return;
                }else if(quantity > 0) {
                    total  -= cursor.getInt(2);
                    productCart.setQuantity(quantity);
                }
            }
        }
        if (action.equals("plus")){
            if (cursor.moveToNext()){
                quantity = (cursor.getInt(4) + 1);
                total +=  cursor.getInt(2);
                productCart.setQuantity(quantity);
            }
        }
        ContentValues cv = new ContentValues();
        cv.put(QUANTITY_FIELD, quantity);
        String whereClause = "id = ?";
        String[] whereArgs = {productCart.getId()+""};
        int chek = myDatabase.update(TABLE_NAME, cv, whereClause, whereArgs);
        if (chek == 1){
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            String price = formatter.format(Double.parseDouble(total+""))+" VNĐ";
            txtvTotal.setText(" "+price);
            adapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this, "Lỗi giỏ hàng !", Toast.LENGTH_LONG).show();
        }

    }

    private void deleteCart(Cart_Model productCart) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setMessage("Xóa "+productCart.getName()+" khỏi giỏ hàng ?");
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String whereClause = ID_FIELD+" = ?";
                String[] whereArgs = {productCart.getId()+""};
                int check = myDatabase.delete(TABLE_NAME, whereClause, whereArgs);
                if (check > 0){
                    total  -= productCart.getPrice();
                    list.remove(productCart);
                    DecimalFormat formatter = new DecimalFormat("###,###,##0");
                    String price = formatter.format(Double.parseDouble(total+""))+" VNĐ";
                    txtvTotal.setText(" "+price);
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(CartActivity.this, "Lỗi giỏ hàng !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.show();
    }
    private void deleteCartAuto(int id) {
        String whereClause = ID_FIELD+" = ?";
        String[] whereArgs = {id+""};
        myDatabase.delete(TABLE_NAME, whereClause, whereArgs);
        mapping();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnThanhtoan:
                if (list.size() > 0){
                    showFromThanhToan();
                }else {
                    Toast.makeText(this, "Giỏ hàng của bạn trống !", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void showFromThanhToan() {
        Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_from_thanh_toan);

        TextView txtvErrName = dialog.findViewById(R.id.txtvErrName);
        TextView txtvErrEmail = dialog.findViewById(R.id.txtvErrEmail);
        TextView txtvErrPhone = dialog.findViewById(R.id.txtvErrPhone);

        EditText edtName = dialog.findViewById(R.id.edtName);
        EditText edtEMail = dialog.findViewById(R.id.edtEMail);
        EditText edtPhone = dialog.findViewById(R.id.edtPhone);

        Button btnOK = dialog.findViewById(R.id.btnOK);
        Button btnCannel = dialog.findViewById(R.id.btnCannel);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String email = edtEMail.getText().toString();
                String phone = edtPhone.getText().toString();

                int check = 0;
                if (name.equals("")){
                    txtvErrName.setVisibility(View.VISIBLE);
                    check = 1;
                }else {
                    txtvErrName.setVisibility(View.GONE);
                    check = 0;
                }

                if (email.equals("")){
                    txtvErrEmail.setVisibility(View.VISIBLE);
                    check = 1;
                }else {
                    txtvErrEmail.setVisibility(View.GONE);
                    check = 0;
                }

                if (phone.equals("")){
                    txtvErrPhone.setVisibility(View.VISIBLE);
                    check = 1;
                }else {
                    txtvErrPhone.setVisibility(View.GONE);
                    check = 0;
                }

                if (check == 0){
                    Toast.makeText(CartActivity.this, "Thanh toán thành công !", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    sendOnChannel();
                }

            }
        });
        btnCannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void sendOnChannel() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        android.app.Notification notification = new NotificationCompat.Builder(this, com.example.oderapp.Activity.Notification.CHANNEL_ID)
                .setSmallIcon(R.drawable.kita)
                .setColor(0x169AB9)
                .setContentTitle("Giao dịch thành công !")
                .setContentText("Đơn hàng của bạn đã được xác nhận, chúng tôi sẽ liên hệ lại với bạn sớm nhất ! xin cảm ơn.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setDefaults(Notification.DEFAULT_SOUND)

                //set link nhạc onlie
//                .setSound()

                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        int notificationid = 1;
        this.notificationManagerCompat.notify(notificationid, notification);
    }
}