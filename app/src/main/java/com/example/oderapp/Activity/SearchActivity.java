package com.example.oderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

public class SearchActivity extends AppCompatActivity{
    Toolbar toolbarSearch;
    RecyclerView recyclerView;
    EditText edtSearch;

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



        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URl_SEARCH_PRODUCT_SUGGESTION, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                try {
                    for (int i = 0 ; i < response.length();i ++){
                        jsonObject = response.getJSONObject(i);
                        Log.d("response",jsonObject.getString("name"));
                        list.add(new DashboardSanPham(jsonObject.getInt("id"),jsonObject.getString("name"),jsonObject.getInt("pirce"),jsonObject.getString("image"),jsonObject.getString("details"),jsonObject.getInt("product_id")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
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
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URl_SEARCH_PRODUCT_SUGGESTION+keyname, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                try {
                    for (int i = 0 ; i < response.length();i ++){
                        jsonObject = response.getJSONObject(i);
                        Log.d("response",jsonObject.getString("name"));
                        list.add(new DashboardSanPham(jsonObject.getInt("id"),jsonObject.getString("name"),jsonObject.getInt("pirce"),jsonObject.getString("image"),jsonObject.getString("details"),jsonObject.getInt("product_id")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(arrayRequest);
    }
    private void mapping() {
        recyclerView = findViewById(R.id.recyclerViewProduct);
        toolbarSearch = findViewById(R.id.toolbarSearch);
        edtSearch = findViewById(R.id.edtSearch);
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

}