package com.example.oderapp.Fragment;

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
import com.example.oderapp.Adapter.Product_hot_Adapter;
import com.example.oderapp.Adapter.Product_oders_Adapter;
import com.example.oderapp.Adapter.Product_suggestion_Adapter;
import com.example.oderapp.Model.Product_hot;
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
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SliderLayout sliderLayout;
    private LinearLayoutManager layoutManager;
    SwipeRefreshLayout swipeRefresh;
    Product_hot_Adapter product_hot_adapter;
    List<Product_hot> product_hots_list;

    Product_oders_Adapter product_oders_adapter;
    List<Product_oders> product_oders_List;

    Product_suggestion_Adapter product_suggestion_adapter;
    List<Product_suggestion> product_suggestions_list;



    //phan trang
    private int totalItemCount;
    private int firstVisibleItem;
    private int visibleItemCount;
    private int page = 1;
    private int previousTotal;
    private boolean load = true;


    RecyclerView recyclerViewlist_product_host,recyclerViewlist_product_oder,recyclerViewlist_product_suggestion;

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
        sliderLayout.setScrollTimeInSec(2);


        data_slider();
        product_hots_list = new ArrayList<>();
        data_product_host();


        product_oders_List = new ArrayList<>();
        data_product_oders();

        product_suggestions_list = new ArrayList<>();
        data_product_suggestion();
        pagination_suggestion();

        return view;

    }



    private void data_product_oders() {
        product_oders_adapter = new Product_oders_Adapter(this.getContext(),R.layout.item_product_orders,product_oders_List);
        recyclerViewlist_product_oder.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewlist_product_oder.setLayoutManager(layoutManager);
        recyclerViewlist_product_oder.setAdapter(product_oders_adapter);

       for (int i = 0 ;i < 100 ; i ++){
           product_oders_List.add(new Product_oders(1,"Banh Ngon",2500,"https://image.flaticon.com/icons/png/128/2971/2971975.png"));
       }
    }

    private void data_slider() {
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URl_SLIDER, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0 ; i < response.length();i ++){
                        try {
                            jsonObject = response.getJSONObject(i);
                            DefaultSliderView sliderView = new DefaultSliderView(getContext());
                            sliderView.setImageUrl(jsonObject.getString("name"));
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
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(arrayRequest);
        MySingleton.getInstance(getContext().getApplicationContext()).addToRequestQueue(arrayRequest);

    }
    private void data_product_host() {
        product_hot_adapter = new Product_hot_Adapter(this.getContext(),R.layout.item_product_hot,product_hots_list);
        recyclerViewlist_product_host.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewlist_product_host.setLayoutManager(layoutManager);
        recyclerViewlist_product_host.setAdapter(product_hot_adapter);

        product_hots_list.add(new Product_hot(1,"Banh Ngot","https://static.vietnammm.com/images/restaurants/vn/NPOQPP7/logo_465x320.png"));
        product_hots_list.add(new Product_hot(1,"Banh Bao","https://static.vietnammm.com/images/restaurants/vn/NRNNQ3O/logo_465x320.png"));
        product_hots_list.add(new Product_hot(1,"Banh Cuon","https://static.vietnammm.com/images/restaurants/vn/5171R5N/logo_465x320.png"));
        product_hots_list.add(new Product_hot(1,"Banh Keo","https://static.vietnammm.com/images/restaurants/vn/NNPOQQP/logo_465x320.png"));
        product_hots_list.add(new Product_hot(1,"Keo Ngot","https://static.vietnammm.com/images/restaurants/vn/OP35R111/logo_465x320.png"));
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
                    for (int i = 0 ; i < response.length();i ++){
                        jsonObject = response.getJSONObject(i);
                        Log.d("response",jsonObject.getString("name"));
                        product_suggestions_list.add(new Product_suggestion(jsonObject.getString("id"),jsonObject.getString("name"),jsonObject.getString("image"),jsonObject.optString("pirce")));
                    }
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
    private void pagination_suggestion() {
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
        product_suggestion_adapter.Product_suggestion_Adapter(this.getContext(),R.layout.item_product_suggestion,product_suggestions_list);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
            }
        },3000);
    }
}