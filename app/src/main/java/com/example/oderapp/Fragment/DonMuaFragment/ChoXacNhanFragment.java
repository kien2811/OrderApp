package com.example.oderapp.Fragment.DonMuaFragment;

import android.app.Dialog;
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
import android.view.Window;
import android.widget.Button;
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
import com.example.oderapp.Activity.DetailCartActivity;
import com.example.oderapp.Adapter.ChoXacNhanAdapter;
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

public class ChoXacNhanFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    RecyclerView recyclerViewProductAllSanPham;
    SwipeRefreshLayout SwipeRefreshLayout_all_product;
    ChoXacNhanAdapter ChoXacNhanAdapter;
    List<DonHang> list;
    LinearLayoutManager linearLayoutManager;
    SessionManagement sessionManagement;
    public ChoXacNhanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cho_xac_nhan, container, false);
        recyclerViewProductAllSanPham = view.findViewById(R.id.recyclerViewProductAllSanPham);
        SwipeRefreshLayout_all_product = view.findViewById(R.id.SwipeRefreshLayout_all_product);
        SwipeRefreshLayout_all_product.setOnRefreshListener(this);
        SwipeRefreshLayout_all_product.setColorSchemeColors(getResources().getColor(R.color.purple_500));

        data_all();
        return view;
    }

    private void data_all() {
        list = new ArrayList<>();
        ChoXacNhanAdapter = new ChoXacNhanAdapter(this,R.layout.line_cho_thanh_toan,list);
        recyclerViewProductAllSanPham.setHasFixedSize(true);
        recyclerViewProductAllSanPham.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewProductAllSanPham.setAdapter(ChoXacNhanAdapter);
        sessionManagement = new SessionManagement(getContext());
        int id_user = sessionManagement.getIduser();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URL_DON_MUA_CHO_XAC_NHAN+id_user, null, new Response.Listener<JSONArray>() {
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
                    ChoXacNhanAdapter.notifyDataSetChanged();
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
        ChoXacNhanAdapter.notifyDataSetChanged();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SwipeRefreshLayout_all_product.setRefreshing(false);
            }
        },2000);
    }

    public void UpdateData(int id) {
        Dialog dialog = new Dialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.from_ok_cancel);

        Button btnOK = dialog.findViewById(R.id.btnOK);
        Button btnCannel = dialog.findViewById(R.id.btnCannel);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST, Api.URL_UPDATE_ID_TRANSACTION_DATE_TO_HUY_DON_HANG, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            data_all();
                            list.clear();
                            ChoXacNhanAdapter.notifyDataSetChanged();
                            JSONObject jsonObject = new JSONObject(response);
                            String user_oder = jsonObject.getString("user_oder");
                            Toast.makeText(getContext(), ""+user_oder, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "lỗi chưa hủy đơn hàng được!!", Toast.LENGTH_SHORT).show();
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
                    map.put("id", id+"");
                    return map;
                }};
                RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                requestQueue1.add(request);
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
}