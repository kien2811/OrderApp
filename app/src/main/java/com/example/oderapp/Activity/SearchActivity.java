package com.example.oderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.oderapp.Adapter.SearchAdapter;
import com.example.oderapp.Model.DashboardSanPham;
import com.example.oderapp.MySingleton.MySingleton;
import com.example.oderapp.R;
import com.example.oderapp.util.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    Toolbar toolbarSearch;
    RecyclerView recyclerView;
    EditText edtSearch;
    SwipeRefreshLayout reseft_layout;
    SearchAdapter adapter;
    List<DashboardSanPham> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mapping();
        initActionBar();
        init();
    }

    private void init() {
        list = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this,2));
        adapter = new SearchAdapter(SearchActivity.this,R.layout.item_product_suggestion,list);
        recyclerView.setAdapter(adapter);



        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URl_SEARCH, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                try {
                    for (int i = 0 ; i < response.length();i ++){
                        jsonObject = response.getJSONObject(i);
                        Log.d("response",jsonObject.getString("name"));
                        list.add(new DashboardSanPham(jsonObject.getInt("id"),
                                jsonObject.getString("name"),
                                jsonObject.getInt("pirce"),
                                Api.URL_IMG_PROFILE+"img/"+jsonObject.getString("image"),
                                jsonObject.getString("details"),
                                jsonObject.getInt("product_id"),
                                jsonObject.getInt("amount")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(SearchActivity.this, "Không Tìm Thấy Kết Quả !", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(arrayRequest);




        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchActivity.this, ""+v.getId(), Toast.LENGTH_SHORT).show();
            }
        });


        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    keySearchView();
                }
                return false;
            }
        });
    }
    private void keySearchView(){
        String keyname = edtSearch.getText().toString().trim();
        list = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this,2));
        adapter = new SearchAdapter(SearchActivity.this,R.layout.item_product_suggestion,list);
        recyclerView.setAdapter(adapter);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URl_SEARCH+keyname, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                try {
                    for (int i = 0 ; i < response.length();i ++){
                        jsonObject = response.getJSONObject(i);
                        Log.d("response",jsonObject.getString("name"));
                        list.add(new DashboardSanPham(jsonObject.getInt("id"),
                                jsonObject.getString("name"),
                                jsonObject.getInt("pirce"),
                                Api.URL_IMG_PROFILE+"img/"+jsonObject.getString("image"),
                                jsonObject.getString("details"),
                                jsonObject.getInt("product_id"),
                                jsonObject.getInt("amount")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            
                Toast.makeText(SearchActivity.this, "Không Tìm Thấy Kết Quả", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(arrayRequest);
    }
    private void mapping() {
        recyclerView = findViewById(R.id.recyclerViewProduct);
        toolbarSearch = findViewById(R.id.toolbarSearch);
        edtSearch = findViewById(R.id.edtSearch);
        reseft_layout = findViewById(R.id.reseft_layout);
        reseft_layout.setOnRefreshListener(this);
        reseft_layout.setColorSchemeColors(getResources().getColor(R.color.purple_500));

    }
    private void initActionBar() {
        setSupportActionBar(toolbarSearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarSearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    public void onRefresh() {
        keySearchView();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reseft_layout.setRefreshing(false);
            }
        },2000);
    }
}