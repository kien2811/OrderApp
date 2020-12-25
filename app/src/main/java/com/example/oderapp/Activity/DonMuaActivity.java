package com.example.oderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.oderapp.Adapter.ChoXacNhanAdapter;
import com.example.oderapp.Adapter.ViewMenuDonMuaAdapter;
import com.example.oderapp.Adapter.ViewMenuSanPhamAdapter;
import com.example.oderapp.Fragment.ProfileFragment;
import com.example.oderapp.Model.Cart_Model;
import com.example.oderapp.R;
import com.example.oderapp.SessionManage.SessionManagement;
import com.example.oderapp.util.Api;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DonMuaActivity extends AppCompatActivity {
    private TabLayout Don_muatabLayout;
    private ViewPager view_page_Don_Mua;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cho_xac_nhan);
        Don_muatabLayout =findViewById(R.id.Don_muatabLayout);
        view_page_Don_Mua =findViewById(R.id.view_page_Don_Mua);
        toolbar = findViewById(R.id.toolbar);
        ViewMenuDonMuaAdapter viewMenuDonMuaAdapter = new ViewMenuDonMuaAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        view_page_Don_Mua.setAdapter(viewMenuDonMuaAdapter);
        Don_muatabLayout.setupWithViewPager(view_page_Don_Mua);
        viewMenuDonMuaAdapter.notifyDataSetChanged();
        initActionBar();


        Intent intent = getIntent();
        if (intent != null){
            String DANGGIAO = (String) intent.getSerializableExtra("DANGGIAO");
            if (DANGGIAO != null && DANGGIAO.equals("OK")){
//                Toast.makeText(this, "Có dữ liệu", Toast.LENGTH_SHORT).show();
                view_page_Don_Mua.setCurrentItem(1);
            }else {
//                Toast.makeText(this, "K dữ liệu", Toast.LENGTH_SHORT).show();
            }
            String DAMUA = (String) intent.getSerializableExtra("DAMUA");
            if (DAMUA != null && DAMUA.equals("OK")){
//                Toast.makeText(this, "Có dữ liệu", Toast.LENGTH_SHORT).show();
                view_page_Don_Mua.setCurrentItem(2);
            }else {
//                Toast.makeText(this, "K dữ liệu", Toast.LENGTH_SHORT).show();
            }
        }else {
//            Toast.makeText(this, "Không", Toast.LENGTH_SHORT).show();
        }

    }
    private void initActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đơn Mua");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }


}