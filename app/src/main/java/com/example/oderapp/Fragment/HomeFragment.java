package com.example.oderapp.Fragment;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.oderapp.Adapter.Product_New_Adapter;
import com.example.oderapp.Adapter.Product_oders_Adapter;
import com.example.oderapp.Adapter.Product_suggestion_Adapter;
import com.example.oderapp.Model.Product_new;
import com.example.oderapp.Model.Product_oders;
import com.example.oderapp.Model.Product_suggestion;
import com.example.oderapp.MySingleton.MySingleton;
import com.example.oderapp.R;
import com.example.oderapp.util.Api;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SliderLayout sliderLayout;
    private LinearLayoutManager layoutManager;
    SwipeRefreshLayout swipeRefresh;
    Product_New_Adapter product_new_adapter;
    List<Product_new> product_hots_list;

    Product_oders_Adapter product_oders_adapter;
    List<Product_oders> product_oders_List;

    Product_suggestion_Adapter product_suggestion_adapter;
    List<Product_suggestion> product_suggestions_list;



    //phan trang cho san pham goi y
    private int totalItemCount;
    private int firstVisibleItem;
    private int visibleItemCount;
    private int page = 1;
    private int previousTotal;
    private boolean load = true;

    RecyclerView recyclerViewlist_product_host,recyclerViewlist_product_oder,recyclerViewlist_product_suggestion;

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewlist_product_host = view.findViewById(R.id.product_hot);
        recyclerViewlist_product_oder = view.findViewById(R.id.recyclerViewlist_product_oder);
        recyclerViewlist_product_suggestion = view.findViewById(R.id.product_suggestion);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.purple_500));
        //silder
        sliderLayout = view.findViewById(R.id.silder);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(4);


        data_slider();
        //san pham moi
        product_hots_list = new ArrayList<>();
        data_product_host();

        // san pham mua nhieu
        product_oders_List = new ArrayList<>();
        data_product_oders();
        // goi y san pham
        product_suggestions_list = new ArrayList<>();
        data_product_suggestion();

        return view;

    }



    private void data_product_oders() {
        product_oders_adapter = new Product_oders_Adapter();
        product_oders_adapter.Product_oders_Adapter(this.getContext(),R.layout.item_product_orders,product_oders_List);
        recyclerViewlist_product_oder.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewlist_product_oder.setLayoutManager(layoutManager);
        recyclerViewlist_product_oder.setAdapter(product_oders_adapter);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URl_PRODUCT_ODER, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                product_oders_List.clear();
                for (int i = 0 ; i < response.length();i ++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        product_oders_List.add(new Product_oders(jsonObject.getInt("id"),"Banh Ngon",jsonObject.getInt("pirce"),jsonObject.getString("image"),jsonObject.getString("details"),jsonObject.getInt("product_id"),jsonObject.getInt("amount")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            product_oders_adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), "error"+error, Toast.LENGTH_SHORT).show();
//                Log.d("error",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(arrayRequest);

    }

    private void data_slider() {
        JsonArrayRequest arrayRequest_slider = new JsonArrayRequest(Request.Method.GET, Api.URl_SLIDER, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject_slider;
                sliderLayout.clearSliderViews();
                for (int i = 0 ; i < response.length();i ++){
                        try {
                            jsonObject_slider = response.getJSONObject(i);
                            DefaultSliderView sliderView = new DefaultSliderView(getContext());
                            sliderView.setImageUrl(jsonObject_slider.getString("image"));
                            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                            sliderLayout.addSliderView(sliderView);

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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(arrayRequest_slider);
//        MySingleton.getInstance(getContext().getApplicationContext()).addToRequestQueue(arrayRequest_slider);

    }
    // san pham moi
    private void data_product_host() {
        product_new_adapter = new Product_New_Adapter();
        product_new_adapter.Product_New_Adapter(this.getContext(),R.layout.item_product_hot,product_hots_list);
        recyclerViewlist_product_host.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewlist_product_host.setLayoutManager(layoutManager);
        recyclerViewlist_product_host.setAdapter(product_new_adapter);
        JsonArrayRequest arrayRequest_product_host = new JsonArrayRequest(Request.Method.GET, Api.URl_PRODUCT_NEW, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject_product_host;
                product_hots_list.clear();
                for (int i = 0 ; i < response.length();i ++){
                    try {
                        jsonObject_product_host = response.getJSONObject(i);
                        product_hots_list.add(new Product_new(jsonObject_product_host.getInt("id"),jsonObject_product_host.getString("name"),jsonObject_product_host.getString("image"),jsonObject_product_host.getInt("pirce"),jsonObject_product_host.getString("details"),jsonObject_product_host.getInt("product_id"),jsonObject_product_host.getInt("amount")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                product_new_adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), "error"+error, Toast.LENGTH_SHORT).show();
//                Log.d("error",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(arrayRequest_product_host);



        }
    private void data_product_suggestion() {
        recyclerViewlist_product_suggestion.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(),2);
        recyclerViewlist_product_suggestion.setLayoutManager(layoutManager);
        product_suggestion_adapter = new Product_suggestion_Adapter();
        product_suggestion_adapter.Product_suggestion_Adapter(this.getContext(),R.layout.item_product_suggestion,product_suggestions_list);
        recyclerViewlist_product_suggestion.setAdapter(product_suggestion_adapter);


        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URl_PRODUCT_SUGGESTION+page, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                try {
                    product_suggestions_list.clear();
                    for (int i = 0 ; i < response.length();i ++){
                        jsonObject = response.getJSONObject(i);
//                        Log.d("response",jsonObject.getString("name"));

                        product_suggestions_list.add(new Product_suggestion(jsonObject.getInt("id"),jsonObject.getString("name"),jsonObject.getString("image"),jsonObject.getInt("pirce"),jsonObject.getString("details"),jsonObject.getInt("product_id"),jsonObject.getInt("amount")));
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
        pagination_suggestion();

        MySingleton.getInstance(getContext().getApplicationContext()).addToRequestQueue(arrayRequest);
    }

    private void pagination_suggestion() {
        // even load data cua san pham goi y
        recyclerViewlist_product_suggestion.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    data_product_suggestion();
                    load = true;
                    Log.d("onScrolled", String.valueOf(page));
                }
            }
        });


    }

    // refresh page data
    @Override
    public void onRefresh() {
        data_product_suggestion();
        data_product_oders();
        data_product_host();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
            }
        },2000);
    }
}