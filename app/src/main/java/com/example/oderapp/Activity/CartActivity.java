package com.example.oderapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.oderapp.Adapter.CartAdapter;
import com.example.oderapp.Adapter.Product_Dashboard_sanPham_Adapter;
import com.example.oderapp.Model.Cart_Model;
import com.example.oderapp.R;
import com.example.oderapp.SessionManage.SessionManagement;
import com.example.oderapp.util.Api;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    List<Cart_Model> list;
    CartAdapter adapter;
    Button btnThanhtoan,btnMinus,btnPlus;
    RecyclerView recyclerViewCart;
    TextView txtvTotal;
    SessionManagement sessionManagement;
    int quantity = 0;
    int total;
    int product_id;

    NotificationManagerCompat notificationManagerCompat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mapping();
        initActionBar();
        getDataCart();
        initButton();
        initNotification();


        RecyclerView.ItemDecoration  itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerViewCart.addItemDecoration(itemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                deleteCart(1);
                Toast.makeText(CartActivity.this, ""+1, Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewCart);
        adapter.notifyDataSetChanged();


    }

    public void UpdateQuantityCart(Cart_Model cart_model, String action) {


        sessionManagement = new SessionManagement(this);
        String token = sessionManagement.getToken();
        int id_user = sessionManagement.getIduser();
//        Log.d("aac", cart_model.toString());
        String url = "http://192.168.1.11:8888/OderApp_OOP/public/?controller=index&action=select_id_product_order_user&id_user="+id_user+"&id_product="+cart_model.getId()+"&token="+token+" ";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0 ; i < response.length();i ++) {
                    try {
                        jsonObject = response.getJSONObject(i);
//                        Log.d("aac", jsonObject.toString());
                        int quantily_db = jsonObject.getInt("amount_user_oder");

                        if (action.equals("minus")) {
                                quantity = (quantily_db - 1);
                                Log.d("aac", quantity+"");
                                if (quantity == 0) {
                                    deleteCart(cart_model.getId());
                                    return;
                                } else if (quantity > 0) {
                                    total -= cart_model.getPrice();
                                    cart_model.setQuantity(quantity);
                                    updateQuantily(cart_model.getId(),id_user,quantity,token);
                                }
                                adapter.notifyDataSetChanged();
                        }
                        if (action.equals("plus")) {
                                quantity = (quantily_db + 1);
                                Log.d("aac", quantity+"");
                                total += cart_model.getPrice();
                                cart_model.setQuantity(quantity);
                                updateQuantily(cart_model.getId(),id_user,quantity,token);
                        }
                        getDataCart();
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(CartActivity.this, "thêm số lượng lỗi !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CartActivity.this, "error"+error, Toast.LENGTH_SHORT).show();
                Log.d("error",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void updateQuantily(int id, int id_user, int quantity,String token){
        String url = "http://192.168.1.11:8888/OderApp_OOP/public/?controller=index&action=update_id_product_order_user&id_user="+id_user+"&id_product="+id+"&quantily="+quantity+"&token="+token+" ";
        RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String user_oder = response.getString("user_oder");
//                    Toast.makeText(CartActivity.this, ""+user_oder, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    Toast.makeText(CartActivity.this, "lỗi chưa thêm được giỏ hàng", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    public void deleteCart(int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setMessage("Xóa sản phẩm khỏi giỏ hàng ???");
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String token = sessionManagement.getToken();
                int id_user = sessionManagement.getIduser();
                String url = "http://192.168.1.11:8888/OderApp_OOP/public/?controller=index&action=delete_id_product_order_user&id_user="+id_user+"&id_product="+id+"&token="+token+"";
                RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String user_oder = response.getString("user_oder");
                            Toast.makeText(CartActivity.this, ""+user_oder, Toast.LENGTH_SHORT).show();
                            getDataCart();

                        } catch (JSONException e) {
                            Toast.makeText(CartActivity.this, "lỗi chưa thêm được giỏ hàng", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest);

            }
        });
        builder.show();
    }


    private void initNotification() {
        this.notificationManagerCompat = NotificationManagerCompat.from(this);
    }

    private void initButton() {
        btnThanhtoan.setOnClickListener(this);
    }


    private void initActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Giỏ hàng");
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
        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        toolbar = findViewById(R.id.toolbar);
        txtvTotal = findViewById(R.id.txtvTotal);
        btnThanhtoan = findViewById(R.id.btnThanhtoan);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
    }
    private void getDataCart() {
        total = 0;

        list = new ArrayList<>();
        adapter = new CartAdapter(this,R.layout.line_cart,list);
        recyclerViewCart.setHasFixedSize(true);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCart.setAdapter(adapter);



        sessionManagement = new SessionManagement(this);
        String token = sessionManagement.getToken();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URI_TOKEN_CART+token, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0 ; i < response.length();i ++){
                    try {
                        jsonObject = response.getJSONObject(i);
//                        Log.d("aac",jsonObject.toString());
                        int price = jsonObject.getInt("pirce");
                        int quatily = jsonObject.getInt("amount_user_oder");
                        total += (price * quatily);
                        list.add(new Cart_Model(jsonObject.getInt("id_product"),
                                jsonObject.getString("name"),
                                jsonObject.getInt("pirce"),
                                jsonObject.getString("image"),
                                jsonObject.getString("details"),
                                jsonObject.getInt("amount_user_oder"),
                                jsonObject.getInt("product_id")
                                ));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(CartActivity.this, "Giỏ hàng trống !", Toast.LENGTH_SHORT).show();
                    }

                    DecimalFormat formatter = new DecimalFormat("###,###,###");
                    String price1 = formatter.format(Double.parseDouble(total+""))+" VNĐ";
                    txtvTotal.setText(" "+price1);
                    adapter.notifyDataSetChanged();



                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), "error"+error, Toast.LENGTH_SHORT).show();
                Log.d("error",error.toString());
                Toast.makeText(CartActivity.this, "Giỏ hàng của bạn trống !", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

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