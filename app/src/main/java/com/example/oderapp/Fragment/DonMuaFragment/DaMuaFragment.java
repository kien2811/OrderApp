package com.example.oderapp.Fragment.DonMuaFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.oderapp.Activity.CartActivity;
import com.example.oderapp.Adapter.ChoXacNhanAdapter;
import com.example.oderapp.Adapter.DaHuyAdapter;
import com.example.oderapp.Adapter.DaMuaAdapter;
import com.example.oderapp.Model.DonHang;
import com.example.oderapp.MySingleton.MySingleton;
import com.example.oderapp.R;
import com.example.oderapp.SessionManage.SessionManagement;
import com.example.oderapp.util.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaMuaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    RecyclerView recyclerViewProductAllSanPham;
    SwipeRefreshLayout SwipeRefreshLayout_all_product;
    DaMuaAdapter daMuaAdapter;
    List<DonHang> list;
    LinearLayoutManager linearLayoutManager;
    SessionManagement sessionManagement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_da_mua, container, false);
        recyclerViewProductAllSanPham = view.findViewById(R.id.recyclerViewProductAllSanPham);
        SwipeRefreshLayout_all_product = view.findViewById(R.id.SwipeRefreshLayout_all_product);
        SwipeRefreshLayout_all_product.setOnRefreshListener(this);
        SwipeRefreshLayout_all_product.setColorSchemeColors(getResources().getColor(R.color.purple_500));

        data_all();
        return view;
    }

    private void data_all() {
        list = new ArrayList<>();
        daMuaAdapter = new DaMuaAdapter(this,R.layout.line_product_da_huy,list);
        recyclerViewProductAllSanPham.setHasFixedSize(true);
        recyclerViewProductAllSanPham.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewProductAllSanPham.setAdapter(daMuaAdapter);
        sessionManagement = new SessionManagement(getContext());
        int id_user = sessionManagement.getIduser();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URL_DON_MUA_DA_MUA+id_user, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                try {
//                    product_suggestions_list.clear();
                    for (int i = 0 ; i < response.length();i ++){
                        jsonObject = response.getJSONObject(i);
//                        Log.d("response",jsonObject.getString("name"));
                        list.add(new DonHang(jsonObject.getInt("id"),
                                jsonObject.getInt("id_product"),
                                jsonObject.getString("name_product"),
                                jsonObject.getInt("pirce"),
                                jsonObject.getString("image"),
                                jsonObject.getString("details"),
                                jsonObject.getInt("quantity"),
                                jsonObject.getString("name_status")));
                    }
                    daMuaAdapter.notifyDataSetChanged();
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
        MySingleton.getInstance(getContext().getApplicationContext()).addToRequestQueue(arrayRequest);
    }

    @Override
    public void onRefresh() {
        daMuaAdapter.notifyDataSetChanged();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SwipeRefreshLayout_all_product.setRefreshing(false);
            }
        },2000);
    }



    public void InsertData(int id_Product,int quantily) {
        sessionManagement = new SessionManagement(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,Api.URL_CHECK_ID_PRODUCT_ODER_USER+sessionManagement.getIduser()+"&id_product="+id_Product, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
//                        Log.d("aac", jsonObject.toString());
                        int quantily_db = jsonObject.getInt("amount_user_oder");
                        int id_product = jsonObject.getInt("id_product");
                        updateCart(id_product,quantily_db,quantily);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "thêm vào giỏ hàng lỗi !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(DetailCartActivity.this, "error"+error, Toast.LENGTH_SHORT).show();
                Log.d("error",error.toString());
                InsertCart(id_Product,quantily);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }
    private  void updateCart(int id_Product ,int update_quantily,int quantily){
        sessionManagement = new SessionManagement(getContext());
        update_quantily += quantily;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        int finalUpdate_quantily = update_quantily;
        StringRequest request = new StringRequest(Request.Method.POST, Api.URL_UPDATE_ID_PRODUCT_ORDER_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String user_oder = jsonObject.getString("user_oder");
                    Toast.makeText(getContext(), ""+user_oder, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), CartActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "lỗi chưa thêm được giỏ hàng", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue1.add(request);
    }
    private void InsertCart(int id_Product,int quantily){
        sessionManagement = new SessionManagement(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Api.URL_INSERT_TO_CART_ORDER_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Intent intent = new Intent(getContext(), CartActivity.class);
                    startActivity(intent);
                    JSONObject jsonObject = new JSONObject(response);
                    String user_oder = jsonObject.getString("user_oder");
                    Toast.makeText(getContext(), ""+user_oder, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "lỗi chưa thêm được giỏ hàng", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Lỗi thêm giỏ hàng insertCart"+error, Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
}