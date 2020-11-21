package com.example.oderapp.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.oderapp.Paginnation.PaginnationScrolListent;
import com.example.oderapp.R;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private SliderLayout sliderLayout;

    Product_hot_Adapter product_hot_adapter;
    List<Product_hot> product_hots_list;

    Product_oders_Adapter product_oders_adapter;
    List<Product_oders> product_oders_List;

    Product_suggestion_Adapter product_suggestion_adapter;
    List<Product_suggestion> product_suggestions_list;


    private boolean isLoading;
    private boolean isLastPage;
    private  int totalPage = 4;
    private int currentPage = 0;


    RecyclerView recyclerViewlist_product_host,recyclerViewlist_product_oder,recyclerViewlist_product_suggestion;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewlist_product_host = view.findViewById(R.id.product_hot);
        recyclerViewlist_product_oder = view.findViewById(R.id.recyclerViewlist_product_oder);
        recyclerViewlist_product_suggestion = view.findViewById(R.id.product_suggestion);




        sliderLayout = view.findViewById(R.id.silder);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(2);


        product_hots_list = new ArrayList<>();
        product_oders_List = new ArrayList<>();
        setSildetView();
        data_product_host();
        data_product_oders();
        data_product_susggestion();
        return view;

    }


    private void data_product_susggestion() {


        product_suggestion_adapter = new Product_suggestion_Adapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerViewlist_product_suggestion.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewlist_product_suggestion.setLayoutManager(gridLayoutManager);
        recyclerViewlist_product_suggestion.setAdapter(product_suggestion_adapter);


        // gach duoi chan
//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
//        recyclerViewlist_product_suggestion.addItemDecoration(itemDecoration);

        product_suggestions_list = new ArrayList<>();
        product_suggestions_list = getProduct_suggestions_list();
        product_suggestion_adapter.setmList_Product_suggestion(product_suggestions_list);
        if(currentPage < totalPage){
            product_suggestion_adapter.addFooterLoading();
        }else{
            isLastPage = true;
        }
        recyclerViewlist_product_suggestion.addOnScrollListener(new PaginnationScrolListent(gridLayoutManager) {
            @Override
            // load 5 san pham moi xong cong ra 1 page
            public void loadMoreItem() {
                isLoading = true;
                currentPage +=1;
                loadNextPage();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });
    }
    private void loadNextPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Product_suggestion> list = getProduct_suggestions_list();
                product_suggestion_adapter.removeFooterLoading();
                product_suggestions_list.addAll(list);
                product_suggestion_adapter.notifyDataSetChanged();
                isLoading = false;
                if(currentPage < totalPage){
                    product_suggestion_adapter.addFooterLoading();
                }else{
                    isLastPage = true;
                }
            }
        },2000);

    }
    private List<Product_suggestion> getProduct_suggestions_list() {
        String URL_GOI_Y_HOM_NAY = "https://tailoha.xyz/?controller=index&action=product_suggestion";
        List<Product_suggestion> list = new ArrayList<>();
        list.add(new Product_suggestion(1,"bánh","",30000));
//        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET,URL_GOI_Y_HOM_NAY, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                JSONObject jsonObject;
////                Toast.makeText(getContext(), "AAA"+response, Toast.LENGTH_SHORT).show();
////                Log.d("AAAC",response.toString());
////                        Toast.makeText(getContext(), "assa"+response.toString(), Toast.LENGTH_SHORT).show();
//                for (int i = 0 ; i < response.length();i ++){
//                    try {
//                        jsonObject = response.getJSONObject(i);
//                        DefaultSliderView sliderView = new DefaultSliderView(getContext());
//                        sliderView.setImageUrl(jsonObject.getString("name"));
//                        sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
//                        sliderLayout.addSliderView(sliderView);
//
////                        list.add(new Product_suggestion(jsonObject.getInt("id"),jsonObject.getString("name"),jsonObject.getString("image"),j));
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                Toast.makeText(getContext(), "error"+error, Toast.LENGTH_SHORT).show();
//                Log.d("error",error.toString());
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(arrayRequest);

        return list;
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

    private void setSildetView() {
        String url = "https://tailoha.xyz/?controller=index&action=slider";
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET,url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
//                Toast.makeText(getContext(), "AAA"+response, Toast.LENGTH_SHORT).show();
//                Log.d("AAAC",response.toString());
//                        Toast.makeText(getContext(), "assa"+response.toString(), Toast.LENGTH_SHORT).show();
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
                Log.d("error",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(arrayRequest);

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


}