package com.example.oderapp.Fragment.DonMuaFragment;

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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.oderapp.Adapter.DaMuaAdapter;
import com.example.oderapp.Adapter.DangGiaoAdapter;
import com.example.oderapp.Model.DonHang;
import com.example.oderapp.MySingleton.MySingleton;
import com.example.oderapp.R;
import com.example.oderapp.SessionManage.SessionManagement;
import com.example.oderapp.util.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DangGiaoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    RecyclerView recyclerViewProductAllSanPham;
    SwipeRefreshLayout SwipeRefreshLayout_all_product;
    DangGiaoAdapter dangGiaoAdapter;
    List<DonHang> list;
    LinearLayoutManager linearLayoutManager;
    SessionManagement sessionManagement;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dang_giao, container, false);
        recyclerViewProductAllSanPham = view.findViewById(R.id.recyclerViewProductAllSanPham);
        SwipeRefreshLayout_all_product = view.findViewById(R.id.SwipeRefreshLayout_all_product);
        SwipeRefreshLayout_all_product.setOnRefreshListener(this);
        SwipeRefreshLayout_all_product.setColorSchemeColors(getResources().getColor(R.color.purple_500));

        data_all();
        return view;
    }
    private void data_all() {
        list = new ArrayList<>();
        dangGiaoAdapter = new DangGiaoAdapter(this,R.layout.line_product_dang_giao,list);
        recyclerViewProductAllSanPham.setHasFixedSize(true);
        recyclerViewProductAllSanPham.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewProductAllSanPham.setAdapter(dangGiaoAdapter);
        sessionManagement = new SessionManagement(getContext());
        int id_user = sessionManagement.getIduser();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URL_DON_MUA_DANG_GIAO+id_user, null, new Response.Listener<JSONArray>() {
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
                    dangGiaoAdapter.notifyDataSetChanged();
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
        dangGiaoAdapter.notifyDataSetChanged();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SwipeRefreshLayout_all_product.setRefreshing(false);
            }
        },2000);
    }


    public void InsertData(int id) {
    }
}