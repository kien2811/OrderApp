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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.oderapp.Model.Cart_Model;
import com.example.oderapp.Model.DashboardSanPham;
import com.example.oderapp.R;
import com.example.oderapp.SessionManage.SessionManagement;
import com.example.oderapp.util.Api;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class DetailCartActivity extends AppCompatActivity {
    Toolbar toolbarCart;
    Button btnAddMuaCart,btnMinus,btnPlus;
    ImageView btnAddCart;
    TextView txtvDescriptionCart,txtvPriceCart,txtvNameCart,txtvQuantity;
    ImageView imgAvatarCart;
    SliderLayout silder;
    SessionManagement sessionManagement;
    int id_Product;

    MyDatabase myDatabase;
    DashboardSanPham dashboardSanPham;
    Cursor cursor;
    static final String DB_NAME = "db_shop";
    static final String TABLE_NAME = "tbl_seen";
    static final String ID_FIELD = "id";
    static final String NAME_FIELD = "name";
    static final String PRICE_FIELD = "price";
    static final String AVATAR_FIELD = "avatar";
    static final String DESCRIPTION_FIELD = "description";
    static final String CATEGORYID_FIELD = "categoryid";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cart);
        mapping();
        initDatabase();
        init();

        silder.setIndicatorAnimation(IndicatorAnimations.FILL);
        silder.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        silder.setScrollTimeInSec(5);
        data_slider(id_Product);
    }

    private void data_slider(int id) {
        JsonArrayRequest arrayRequest_slider = new JsonArrayRequest(Request.Method.GET, Api.URl_SLIDER_PRODUCT+id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject_slider;
                silder.clearSliderViews();
                for (int i = 0 ; i < response.length();i ++){
                    try {
                        jsonObject_slider = response.getJSONObject(i);
                        DefaultSliderView sliderView = new DefaultSliderView(getApplicationContext());
                        sliderView.setImageUrl(Api.URL_IMG_PROFILE+""+jsonObject_slider.getString("img"));
                        sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                        silder.addSliderView(sliderView);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), "error"+error, Toast.LENGTH_SHORT).show();
//                Log.d("error",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(arrayRequest_slider);
    }

    private void initDatabase() {
        //gọi database
        myDatabase = new MyDatabase(DetailCartActivity.this, DB_NAME,null, 1);

        //tạo table
        String url_creadte = "create table if not exists "+TABLE_NAME+" ("+ID_FIELD+" integer, "+NAME_FIELD+" text, "+PRICE_FIELD+" integer, "+AVATAR_FIELD+" text, "+DESCRIPTION_FIELD+" text, "+CATEGORYID_FIELD+" integer)";
        myDatabase.creadteTable(url_creadte);
    }
    private boolean checkSeen(int id_Product) {
        cursor = myDatabase.selectData("select * from "+TABLE_NAME+" where id="+id_Product);
        if (cursor.moveToNext()){
                Toast.makeText(this, cursor.getString(1)+" đã có trong đã xem!", Toast.LENGTH_LONG).show();
                Log.d("aaa",cursor.getString(1));
            return true;
        }
        return false;
    }
    private void addSeenDatabase(int id_Product, String getName,int getPrice,String getAvatar ,String getDescription ,int categoryid) {
        if (!checkSeen(id_Product)){
            ContentValues cv = new ContentValues();
            cv.put(ID_FIELD,id_Product);
            cv.put(NAME_FIELD,getName);
            cv.put(PRICE_FIELD, getPrice);
            cv.put(AVATAR_FIELD, getAvatar);
            cv.put(DESCRIPTION_FIELD,getDescription);
            cv.put(CATEGORYID_FIELD,categoryid);

            long check = myDatabase.insertData(TABLE_NAME, null, cv);
            if (check > 0){
                Toast.makeText(this, getName+" được thêm vào đã xem!", Toast.LENGTH_LONG).show();
                Log.d("aaa",getName);
            }else {
                Toast.makeText(this, "Lỗi !", Toast.LENGTH_LONG).show();
                Log.d("loi",getName);
            }
        }
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
        id_Product = (int) intent.getSerializableExtra("id");
        String getName = (String) intent.getSerializableExtra("getName");
        int getPrice = (int) intent.getSerializableExtra("getPrice");
        String getAvatar = (String) intent.getSerializableExtra("getAvatar");
        String getDescription = (String) intent.getSerializableExtra("getDescription");
        int categoryid = (int) intent.getSerializableExtra("categoryid");

        addSeenDatabase(id_Product, getName, getPrice, getAvatar , getDescription ,categoryid);
//        Picasso.get().load(getAvatar).into(imgAvatarCart);
        txtvNameCart.setText(getName);

        DecimalFormat formatter = new DecimalFormat("###,###,###");
//        String price = formatter.format(Double.parseDouble(getPrice+""))+" VNĐ";
        txtvPriceCart.setText("Giá " + getPrice);

        txtvDescriptionCart.setText(getDescription);
//        Picasso.get().load(getAvatar)
//                .placeholder(R.drawable.loader)
//                .error(R.drawable.noimage)
//                .into(imgAvatarCart);


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
                addCartDatabase();

            }
        });

        initActionBar();
    }

    private void addCartDatabase() {
        sessionManagement = new SessionManagement(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,Api.URL_CHECK_ID_PRODUCT_ODER_USER+sessionManagement.getIduser()+"&id_product="+id_Product, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {

                        jsonObject = response.getJSONObject(i);
//                        Log.d("aac", jsonObject.toString());
                        int quantily_db = jsonObject.getInt("amount_user_oder");
                        int amount = jsonObject.getInt("amount");
                        int id_product = jsonObject.getInt("id_product");
                        if (quantily_db != amount){
                            updateCart(id_product,quantily_db);
                        }else {
                            Toast.makeText(DetailCartActivity.this, "Số lượng sản phẩm đã đạt giới hạn!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DetailCartActivity.this,CartActivity.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(DetailCartActivity.this, "thêm vào giỏ hàng lỗi !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(DetailCartActivity.this, "error"+error, Toast.LENGTH_SHORT).show();
                Log.d("error",error.toString());
                insertCart(id_Product);
                }
            });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }
    private  void updateCart(int id_Product ,int update_quantily){
        sessionManagement = new SessionManagement(getApplicationContext());
        update_quantily += Integer.parseInt(txtvQuantity.getText().toString());
        RequestQueue requestQueue = Volley.newRequestQueue(DetailCartActivity.this);
        int finalUpdate_quantily = update_quantily;
        StringRequest request = new StringRequest(Request.Method.POST, Api.URL_UPDATE_ID_PRODUCT_ORDER_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String user_oder = jsonObject.getString("user_oder");
                    Toast.makeText(DetailCartActivity.this, ""+user_oder, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailCartActivity.this,CartActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    Toast.makeText(DetailCartActivity.this, "lỗi chưa thêm được giỏ hàng", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){@Override
        public Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> map = new HashMap<>();
            map.put("id_user", sessionManagement.getIduser()+"");
            map.put("id_product", id_Product+"");
            map.put("quantily", finalUpdate_quantily+"");
            return map;
        }};
        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        requestQueue1.add(request);
    }
    private void  insertCart(int id_Product){
        sessionManagement = new SessionManagement(getApplicationContext());
        int quantily = Integer.parseInt(txtvQuantity.getText().toString());
        StringRequest request = new StringRequest(Request.Method.POST, Api.URL_INSERT_TO_CART_ORDER_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Intent intent = new Intent(DetailCartActivity.this,CartActivity.class);
                    startActivity(intent);
                    JSONObject jsonObject = new JSONObject(response);
                    String user_oder = jsonObject.getString("user_oder");
                    Toast.makeText(DetailCartActivity.this, ""+user_oder, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(DetailCartActivity.this, "lỗi chưa thêm được giỏ hàng", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailCartActivity.this, "Lỗi thêm giỏ hàng insertCart"+error, Toast.LENGTH_SHORT).show();
                Log.d("eross",error.toString());
            }
        }){@Override
        public Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> map = new HashMap<>();
            map.put("id_user", sessionManagement.getIduser()+"");
            map.put("id_product", id_Product+"");
            map.put("quantily", quantily+"");
            return map;
        }};
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
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
//        imgAvatarCart = findViewById(R.id.imgAvatarCart);
        btnAddMuaCart = findViewById(R.id.btnAddMuaCart);
        btnAddCart = findViewById(R.id.btnAddCart);

        toolbarCart = findViewById(R.id.toolbarCart);

        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        txtvQuantity = findViewById(R.id.txtvQuantity);
        silder = findViewById(R.id.silder);
    }
}