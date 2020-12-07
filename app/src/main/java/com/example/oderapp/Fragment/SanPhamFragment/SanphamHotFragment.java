package com.example.oderapp.Fragment.SanPhamFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.oderapp.Adapter.Product_Dashboard_sanPham_Adapter;
import com.example.oderapp.Model.DashboardSanPham;
import com.example.oderapp.MySingleton.MySingleton;
import com.example.oderapp.R;
import com.example.oderapp.util.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SanphamHotFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerViewProductAllSanPham;
    Product_Dashboard_sanPham_Adapter product_sanPham_dashboard_adapter;
    List<DashboardSanPham> list;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout SwipeRefreshLayout_product_host;
    private int totalItemCount;
    private int firstVisibleItem;
    private int visibleItemCount;
    private int page = 1;
    private int previousTotal;
    private boolean load = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sanpham_hot, container, false);

        list = new ArrayList<>();
        recyclerViewProductAllSanPham = view.findViewById(R.id.recyclerViewProductAllSanPham);
        SwipeRefreshLayout_product_host = view.findViewById(R.id.SwipeRefreshLayout_product_host);
        SwipeRefreshLayout_product_host.setOnRefreshListener(this);
        SwipeRefreshLayout_product_host.setColorSchemeColors(getResources().getColor(R.color.purple_500));

        load_data();

        return view;
    }

    private void load_data() {
        product_sanPham_dashboard_adapter = new Product_Dashboard_sanPham_Adapter(this.getContext(),R.layout.line_sanpham_dashboardsanpham,list);
        recyclerViewProductAllSanPham.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewProductAllSanPham.setLayoutManager(linearLayoutManager);
        recyclerViewProductAllSanPham.setAdapter(product_sanPham_dashboard_adapter);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URL_PRODUCT_HOST+page, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                try {
//                    product_suggestions_list.clear();
                    for (int i = 0 ; i < response.length();i ++){
                        jsonObject = response.getJSONObject(i);
//                        Log.d("response",jsonObject.getString("name"));

                        list.add(new DashboardSanPham(jsonObject.getInt("id"),jsonObject.getString("name"),jsonObject.getInt("pirce"),Api.URL_IMG_PROFILE+"img/"+jsonObject.getString("image"),jsonObject.getString("details"),jsonObject.getInt("product_id"),jsonObject.getInt("amount")));
                    }
                    product_sanPham_dashboard_adapter.notifyDataSetChanged();
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
        pagination();

        MySingleton.getInstance(getContext().getApplicationContext()).addToRequestQueue(arrayRequest);


    }

    private void pagination() {
        recyclerViewProductAllSanPham.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getChildCount();
                visibleItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                if(load){
                    if (totalItemCount  >  previousTotal) {
                        previousTotal = totalItemCount;
                        page++;
                        load = false;
                    }
                }
                if( !load && (firstVisibleItem + visibleItemCount) >= totalItemCount){

                    load = true;
                    load_data();
                    Log.d("onScrolled", String.valueOf(page));
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        load_data();
        product_sanPham_dashboard_adapter.notifyDataSetChanged();
        pagination();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SwipeRefreshLayout_product_host.setRefreshing(false);
            }
        },2000);
    }
}