package com.example.oderapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.oderapp.Adapter.Product_DongGia_Adapter;
import com.example.oderapp.Adapter.Product_oders_Adapter;
import com.example.oderapp.Adapter.Product_suggestion_Adapter;
import com.example.oderapp.Adapter.SizeAdapter;
import com.example.oderapp.Adapter.ThemVaoGioSizeAdapter;
import com.example.oderapp.Model.Cart_Model;
import com.example.oderapp.Model.DashboardSanPham;
import com.example.oderapp.Model.Product_oders;
import com.example.oderapp.Model.Product_suggestion;
import com.example.oderapp.Model.SizeItem;
import com.example.oderapp.MySingleton.MySingleton;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailCartActivity extends AppCompatActivity {
    Toolbar toolbarCart;
    Button btnAddMuaCart;
    ImageView btnAddCart;
    TextView txtvDescriptionCart,txtvPriceCart,txtvNameCart;
    ImageView imgAvatarCart;
    SliderLayout silder;
    RecyclerView recyclerView,recyclerView_dongGia;

    Product_suggestion_Adapter product_suggestion_adapter;
    List<Product_suggestion> product_suggestions_list;


    Product_DongGia_Adapter product_dongGia_adapter;
    List<Product_oders> product_oders_List;

    List<SizeItem> sizeItemList = new ArrayList<>();

    TextView txtvkho,txtvQuantity;
    Button btnMinus,btnPlus,btnDialogMua;


    private LinearLayoutManager layoutManager;
    private int totalItemCount;
    private int firstVisibleItem;
    private int visibleItemCount;
    private int page = 1;
    private int previousTotal;
    private boolean load = true;

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
        silder.setScrollTimeInSec(10);
        data_slider(id_Product);
    }


    private void showFromChonSizeThemVaoGio() {
        Dialog dialog = new Dialog(this);

//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_form_chon_size);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);

        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ImageView imgSanPham = dialog.findViewById(R.id.imgSanPham);
        btnMinus = dialog.findViewById(R.id.btnMinus);
        btnPlus = dialog.findViewById(R.id.btnPlus);
        txtvQuantity = dialog.findViewById(R.id.txtvQuantity);
        TextView txtvPrice = dialog.findViewById(R.id.txtvPrice);
        txtvkho = dialog.findViewById(R.id.txtvkho);
        RecyclerView recyclerViewSize = dialog.findViewById(R.id.recyclerViewSelect);
        btnDialogMua = dialog.findViewById(R.id.btnDialogMua);

        Intent intent = getIntent();
        id_Product = (int) intent.getSerializableExtra("id");
        String getName = (String) intent.getSerializableExtra("getName");
        int getPrice = (int) intent.getSerializableExtra("getPrice");
        int getAmount = (int) intent.getSerializableExtra("getAmount");
        String getAvatar = (String) intent.getSerializableExtra("getAvatar");

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price = formatter.format(getPrice);
        txtvPrice.setText("Giá :" + price +"đ");
        txtvkho.setText("kho : "+getAmount);


        Picasso.get().load(getAvatar)
                .placeholder(R.drawable.loader)
                .error(R.drawable.noimage)
                .into(imgSanPham);

        layoutManager = new GridLayoutManager(getApplicationContext(),5);
        recyclerViewSize.setHasFixedSize(true);
        recyclerViewSize.setLayoutManager(layoutManager);

        getdataThemVaoGio(recyclerViewSize,id_Product);

        btnDialogMua.setText("Thêm vào giỏ");
        btnDialogMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailCartActivity.this, "Bạn chưa chọn Size", Toast.LENGTH_SHORT).show();
            }
        });


        dialog.show();
    }


    private void showFromChonSize() {
        Dialog dialog = new Dialog(this);

//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_form_chon_size);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);

        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ImageView imgSanPham = dialog.findViewById(R.id.imgSanPham);
        btnMinus = dialog.findViewById(R.id.btnMinus);
        btnPlus = dialog.findViewById(R.id.btnPlus);
        txtvQuantity = dialog.findViewById(R.id.txtvQuantity);
        TextView txtvPrice = dialog.findViewById(R.id.txtvPrice);
        txtvkho = dialog.findViewById(R.id.txtvkho);
        RecyclerView recyclerViewSize = dialog.findViewById(R.id.recyclerViewSelect);
        btnDialogMua = dialog.findViewById(R.id.btnDialogMua);

        Intent intent = getIntent();
        id_Product = (int) intent.getSerializableExtra("id");
        String getName = (String) intent.getSerializableExtra("getName");
        int getPrice = (int) intent.getSerializableExtra("getPrice");
        int getAmount = (int) intent.getSerializableExtra("getAmount");
        String getAvatar = (String) intent.getSerializableExtra("getAvatar");

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price = formatter.format(getPrice);
        txtvPrice.setText("Giá :" + price +"đ");
        txtvkho.setText("kho : "+getAmount);


        Picasso.get().load(getAvatar)
                .placeholder(R.drawable.loader)
                .error(R.drawable.noimage)
                .into(imgSanPham);

        layoutManager = new GridLayoutManager(getApplicationContext(),5);
        recyclerViewSize.setHasFixedSize(true);
        recyclerViewSize.setLayoutManager(layoutManager);

        getdata(recyclerViewSize,id_Product);


        btnDialogMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailCartActivity.this, "Bạn chưa chọn Size", Toast.LENGTH_SHORT).show();
            }
        });




        dialog.show();
    }
    private void getdataThemVaoGio(RecyclerView recyclerView, int id_Product) {
        ThemVaoGioSizeAdapter sizeAdapter = new ThemVaoGioSizeAdapter(this,R.layout.item_size,sizeItemList);
        recyclerView.setAdapter(sizeAdapter);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URl_SELECT_PRODUCT_SIZE+id_Product, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                sizeItemList.clear();
                for (int i = 0 ; i < response.length();i ++){
                    try {
                        jsonObject = response.getJSONObject(i);

                        sizeItemList.add(new SizeItem(jsonObject.getInt("id")
                                ,jsonObject.getInt("id_product")
                                ,jsonObject.getInt("size")
                                ,jsonObject.getInt("quantity")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                sizeAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Không có sản phẩm nào đồng giá", Toast.LENGTH_SHORT).show();
//                Log.d("error",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(arrayRequest);

    }

    private void getdata(RecyclerView recyclerView, int id_Product) {
        SizeAdapter sizeAdapter = new SizeAdapter(this,R.layout.item_size,sizeItemList);
        recyclerView.setAdapter(sizeAdapter);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URl_SELECT_PRODUCT_SIZE+id_Product, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                sizeItemList.clear();
                for (int i = 0 ; i < response.length();i ++){
                    try {
                        jsonObject = response.getJSONObject(i);

                        sizeItemList.add(new SizeItem(jsonObject.getInt("id")
                                ,jsonObject.getInt("id_product")
                                ,jsonObject.getInt("size")
                                ,jsonObject.getInt("quantity")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                sizeAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Không có sản phẩm nào đồng giá", Toast.LENGTH_SHORT).show();
//                Log.d("error",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(arrayRequest);

    }


    private void data_product_dong_gia(int price,int id) {
        product_dongGia_adapter = new Product_DongGia_Adapter();
        product_dongGia_adapter.Product_oders_Adapter(this,R.layout.item_product_dong_gia,product_oders_List);
        recyclerView_dongGia.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_dongGia.setLayoutManager(layoutManager);
        recyclerView_dongGia.setAdapter(product_dongGia_adapter);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URl_SELECT_PRODUCT_DONG_GIA+price+"&id_product="+id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                product_oders_List.clear();
                for (int i = 0 ; i < response.length();i ++){
                    try {

                        jsonObject = response.getJSONObject(i);
//                        Log.d("onResponse: ",jsonObject.getString("image").toString());

                        product_oders_List.add(new Product_oders(jsonObject.getInt("id")
                                ,jsonObject.getString("name")
                                ,jsonObject.getInt("pirce")
                                ,Api.URL_IMG_PROFILE+"img/"+jsonObject.getString("image")
                                ,jsonObject.getString("details")
                                ,jsonObject.getInt("product_id")
                                ,jsonObject.getInt("amount")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                product_dongGia_adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Không có sản phẩm nào đồng giá", Toast.LENGTH_SHORT).show();
//                Log.d("error",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(arrayRequest);

    }

    private void data_product_tuongtu(String name,int id) {
        product_suggestions_list = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        product_suggestion_adapter = new Product_suggestion_Adapter();
        product_suggestion_adapter.Product_suggestion_Adapter(this,R.layout.item_product_suggestion,product_suggestions_list);
        recyclerView.setAdapter(product_suggestion_adapter);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URl_SELECT_PRODUCT_TUONG_TU+name+"&id_product="+id+"&page="+page, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                try {
//                    product_suggestions_list.clear();
                    for (int i = 0 ; i < response.length();i ++){
                        jsonObject = response.getJSONObject(i);
                        Log.d("response",jsonObject.getString("name"));

                        product_suggestions_list.add(new Product_suggestion(jsonObject.getInt("id"),jsonObject.getString("name"),Api.URL_IMG_PROFILE+"img/"+jsonObject.getString("image"),jsonObject.getInt("pirce"),jsonObject.getString("details"),jsonObject.getInt("product_id"),jsonObject.getInt("amount")));
                    }
                    product_suggestion_adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "Không có sản phẩm nào tương tự", Toast.LENGTH_SHORT).show();
                Log.d("error",error.toString());
                // goi y san pham
                product_suggestions_list = new ArrayList<>();
                data_product_suggestion();
            }
        });
        pagination_suggestion(name,id);
        MySingleton.getInstance(getApplicationContext().getApplicationContext()).addToRequestQueue(arrayRequest);
    }
    private void data_product_suggestion() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        product_suggestion_adapter = new Product_suggestion_Adapter();
        product_suggestion_adapter.Product_suggestion_Adapter(this,R.layout.item_product_suggestion,product_suggestions_list);
        recyclerView.setAdapter(product_suggestion_adapter);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URl_PRODUCT_SUGGESTION+page, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                try {
//                    product_suggestions_list.clear();
                    for (int i = 0 ; i < response.length();i ++){
                        jsonObject = response.getJSONObject(i);
//                        Log.d("response",jsonObject.getString("name"));

                        product_suggestions_list.add(new Product_suggestion(jsonObject.getInt("id"),jsonObject.getString("name"),Api.URL_IMG_PROFILE+"img/"+jsonObject.getString("image"),jsonObject.getInt("pirce"),jsonObject.getString("details"),jsonObject.getInt("product_id"),jsonObject.getInt("amount")));
                    }
                    product_suggestion_adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());

            }
        });
        pagination_suggestions();
        MySingleton.getInstance(getApplicationContext().getApplicationContext()).addToRequestQueue(arrayRequest);
    }

    private void pagination_suggestions() {
        // even load data cua san pham goi y
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = layoutManager.getChildCount();
                visibleItemCount = layoutManager.getItemCount();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if(load){
                    if (totalItemCount  >  previousTotal) {
                        previousTotal = totalItemCount;
                        page++;
                        load = false;
                    }
                }
                if( !load && (firstVisibleItem + visibleItemCount) >= totalItemCount){

                    load = true;
                    data_product_suggestion();
                    Log.d("onScrolled", String.valueOf(page));
                }
            }
        });
    }




    private void pagination_suggestion(String name, int id) {
        // even load data cua san pham goi y
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = layoutManager.getChildCount();
                visibleItemCount = layoutManager.getItemCount();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if(load){
                    if (totalItemCount  >  previousTotal) {
                        previousTotal = totalItemCount;
                        page++;
                        load = false;
                    }
                }
                if( !load && (firstVisibleItem + visibleItemCount) >= totalItemCount){

                    load = true;
                    data_product_tuongtu(name,id);
                    Log.d("onScrolled", String.valueOf(page));
                }
            }
        });


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
//                Toast.makeText(this, cursor.getString(1)+" đã có trong đã xem!", Toast.LENGTH_LONG).show();
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
//                Toast.makeText(this, getName+" được thêm vào đã xem!", Toast.LENGTH_LONG).show();
                Log.d("aaa",getName);
            }else {
                Toast.makeText(this, "Lỗi !", Toast.LENGTH_LONG).show();
                Log.d("loi",getName);
            }
        }
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
        String price = formatter.format(getPrice);
        txtvPriceCart.setText("Giá :" + price +"đ");

        txtvDescriptionCart.setText(getDescription);
//        Picasso.get().load(getAvatar)
//                .placeholder(R.drawable.loader)
//                .error(R.drawable.noimage)
//                .into(imgAvatarCart);

        // san pham mua nhieu
        product_oders_List = new ArrayList<>();
        data_product_dong_gia(getPrice,id_Product);
        // goi y san pham
        product_suggestions_list = new ArrayList<>();
        data_product_tuongtu(getName,id_Product);

//
        // xử lí sự kiện nút bấm
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addCart();
//                finish();
                showFromChonSizeThemVaoGio();
            }
        });
        btnAddMuaCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addCartDatabase();
                showFromChonSize();
            }
        });

        initActionBar();
    }
    //thêm vào giỏ hàng nhưng không chuyển sang màn hình giỏ hàng
    private void addCartThemVaoGio(int id_size, int maxQuantity) {
        sessionManagement = new SessionManagement(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,Api.URL_CHECK_ID_PRODUCT_ODER_USER+sessionManagement.getIduser()+"&id_product="+id_Product+"&id_size="+id_size, null, new Response.Listener<JSONArray>() {
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
                        if (quantily_db <= maxQuantity){
                            updateAddCart(id_product,quantily_db,maxQuantity);
                        }else {
                            Toast.makeText(DetailCartActivity.this, "Số lượng sản phẩm trong giỏ đã đạt giới hạn!", Toast.LENGTH_SHORT).show();
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
                insertAddCart(id_Product,id_size);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }
    //thêm vào giỏ hàng nhưng không chuyển sang màn hình giỏ hàng
    private void updateAddCart(int id_Product ,int update_quantily,int maxQuantity){
        sessionManagement = new SessionManagement(getApplicationContext());
        update_quantily += Integer.parseInt(txtvQuantity.getText().toString());
        if (update_quantily > maxQuantity){
            Toast.makeText(DetailCartActivity.this, "Số lượng sản phẩm trong giỏ đã đạt giới hạn!", Toast.LENGTH_SHORT).show();
        }else {
            int finalUpdate_quantily = update_quantily;
            StringRequest request = new StringRequest(Request.Method.POST, Api.URL_UPDATE_ID_PRODUCT_ORDER_USER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String user_oder = jsonObject.getString("user_oder");
                        Toast.makeText(DetailCartActivity.this, "" + user_oder, Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (JSONException e) {
                        Toast.makeText(DetailCartActivity.this, "lỗi chưa thêm được giỏ hàng", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("id_user", sessionManagement.getIduser() + "");
                    map.put("id_product", id_Product + "");
                    map.put("quantily", finalUpdate_quantily + "");
                    return map;
                }
            };
            RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
            requestQueue1.add(request);
        }
    }
    //thêm vào giỏ hàng nhưng không chuyển sang màn hình giỏ hàng
    private void  insertAddCart(int id_Product, int id_size){
        sessionManagement = new SessionManagement(getApplicationContext());
        int quantily = Integer.parseInt(txtvQuantity.getText().toString());
        StringRequest request = new StringRequest(Request.Method.POST, Api.URL_INSERT_TO_CART_ORDER_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String user_oder = jsonObject.getString("user_oder");
                    Toast.makeText(DetailCartActivity.this, ""+user_oder, Toast.LENGTH_SHORT).show();
                    finish();
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
            map.put("id_size", id_size+"");
            map.put("quantily", quantily+"");
            return map;
        }};
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
    //thêm vào giỏ hàng và chuyển sang màn hình giỏ hàng
    private void addCartDatabase(int id_size,int maxQuantity) {
        sessionManagement = new SessionManagement(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,Api.URL_CHECK_ID_PRODUCT_ODER_USER+sessionManagement.getIduser()+"&id_product="+id_Product+"&id_size="+id_size, null, new Response.Listener<JSONArray>() {
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
                        if (quantily_db <= maxQuantity){
                            updateCart(id_product,quantily_db,maxQuantity);
                        }else {
                            Toast.makeText(DetailCartActivity.this, "Số lượng sản phẩm trong giỏ đã đạt giới hạn!", Toast.LENGTH_SHORT).show();
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
                insertCart(id_Product,id_size);
                }
            });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }
    //thêm vào giỏ hàng và chuyển sang màn hình giỏ hàng
    private void updateCart(int id_Product ,int update_quantily,int maxQuantity){
        sessionManagement = new SessionManagement(getApplicationContext());
        update_quantily += Integer.parseInt(txtvQuantity.getText().toString());
        if (update_quantily > maxQuantity){
            Toast.makeText(DetailCartActivity.this, "Số lượng sản phẩm trong giỏ đã đạt giới hạn!", Toast.LENGTH_SHORT).show();
        }else {
            int finalUpdate_quantily = update_quantily;
            StringRequest request = new StringRequest(Request.Method.POST, Api.URL_UPDATE_ID_PRODUCT_ORDER_USER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String user_oder = jsonObject.getString("user_oder");
                        Toast.makeText(DetailCartActivity.this, "" + user_oder, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailCartActivity.this, CartActivity.class);
                        startActivity(intent);
                    } catch (JSONException e) {
                        Toast.makeText(DetailCartActivity.this, "lỗi chưa thêm được giỏ hàng", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("id_user", sessionManagement.getIduser() + "");
                    map.put("id_product", id_Product + "");
                    map.put("quantily", finalUpdate_quantily + "");
                    return map;
                }
            };
            RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
            requestQueue1.add(request);
        }
    }
    //thêm vào giỏ hàng và chuyển sang màn hình giỏ hàng
    private void  insertCart(int id_Product,int id_size){
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
            map.put("id_size", id_size+"");
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



        silder = findViewById(R.id.silder);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView_dongGia = findViewById(R.id.recyclerView_dongGia);
    }

    public void AddMuaNgay(int quantity,int id_size) {
        txtvkho.setText("Kho : "+quantity);
        txtvQuantity.setText("1");
        //Xử lí nút số lượng
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                quantitySet("minus");
                int qtt = Integer.parseInt(txtvQuantity.getText().toString());
                if (qtt > 1){
                    qtt -= 1;
                    txtvQuantity.setText(qtt+"");
                }
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                quantitySet("plus");
                int qtt = Integer.parseInt(txtvQuantity.getText().toString());
                if (qtt != quantity) {
                    qtt += 1;
                    txtvQuantity.setText(qtt + "");
                }else {
                    Toast.makeText(DetailCartActivity.this, "Số lượng đã đạt giới hạn", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDialogMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(DetailCartActivity.this, "đã bấm vào mua", Toast.LENGTH_SHORT).show();
                addCartDatabase(id_size,quantity);
            }
        });

    }

    public void AddVaoGio(int quantity, int id_size) {
        txtvkho.setText("Kho : "+quantity);
        txtvQuantity.setText("1");
        //Xử lí nút số lượng
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                quantitySet("minus");
                int qtt = Integer.parseInt(txtvQuantity.getText().toString());
                if (qtt > 1){
                    qtt -= 1;
                    txtvQuantity.setText(qtt+"");
                }
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                quantitySet("plus");
                int qtt = Integer.parseInt(txtvQuantity.getText().toString());
                if (qtt != quantity) {
                    qtt += 1;
                    txtvQuantity.setText(qtt + "");
                }else {
                    Toast.makeText(DetailCartActivity.this, "Số lượng đã đạt giới hạn", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDialogMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(DetailCartActivity.this, "đã bấm vào mua", Toast.LENGTH_SHORT).show();
                addCartThemVaoGio(id_size,quantity);
            }
        });
    }
}