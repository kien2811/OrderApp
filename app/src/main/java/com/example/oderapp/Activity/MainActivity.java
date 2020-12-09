package com.example.oderapp.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.oderapp.Adapter.CartAdapter;
import com.example.oderapp.Fragment.DashboardFragment;
import com.example.oderapp.Fragment.HomeFragment;
import com.example.oderapp.Fragment.ProfileFragment;
import com.example.oderapp.Model.Cart_Model;
import com.example.oderapp.R;
import com.example.oderapp.SessionManage.SessionManagement;
import com.example.oderapp.util.Api;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    ImageButton imgCart;
    ImageView seartMainActivity,logo;
    SessionManagement sessionManagement;
    TextView txtvQuantityCartMain;
    long backPressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManagement = new SessionManagement(MainActivity.this);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
        mapping();

        seartMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        getDataCart();

        handler.postDelayed(runnable,1000);
    }
    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getDataCart();

            handler.postDelayed(this,2000);//60 second delay
        }
    };
    private void getDataCart() {
        sessionManagement = new SessionManagement(this);
        String token = sessionManagement.getToken();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URI_TOKEN_CART+token, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0 ; i < response.length();i ++){
                    txtvQuantityCartMain.setText("  "+(i+1));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), "error"+error, Toast.LENGTH_SHORT).show();
                Log.d("error",error.toString());
//                Toast.makeText(MainActivity.this, "Giỏ hàng của bạn trống !", Toast.LENGTH_SHORT).show();
                txtvQuantityCartMain.setText("  0");
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }

    @Override
    public void onBackPressed() {
        if(backPressed + 3000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else{
            Toast.makeText(this, "Chạm Lần Nữa Để Thoát !", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }

    private void mapping() {
        imgCart = findViewById(R.id.imgCart);
        txtvQuantityCartMain = findViewById(R.id.txtvQuantityCartMain);
        seartMainActivity  = findViewById(R.id.seartMainActivity);
        BottomNavigationView btnNav = findViewById(R.id.button_navication_view);
        btnNav.setOnNavigationItemSelectedListener(navListent);

    }

    private  BottomNavigationView.OnNavigationItemSelectedListener navListent = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        //even tuong click từng màn hình
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectFragment = null;
            switch (item.getItemId()){
                case R.id.home:
                    selectFragment = new HomeFragment();
                    FragmentTransaction home = getSupportFragmentManager().beginTransaction();
                    home.add(R.id.frame_layout, new HomeFragment());
                    home.addToBackStack(null);
                    home.commit();
                    break;
                case R.id.dashboard:
                    selectFragment = new DashboardFragment();
                    FragmentTransaction dashboard = getSupportFragmentManager().beginTransaction();
                    dashboard.add(R.id.frame_layout, new DashboardFragment());
                    dashboard.addToBackStack(null);
                    dashboard.commit();
                    break;
                case R.id.profile:
                    selectFragment = new ProfileFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.commit();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,selectFragment).commit();
            return  true;
        }
    };


}