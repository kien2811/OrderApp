package com.example.oderapp.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.oderapp.Fragment.DashboardFragment;
import com.example.oderapp.Fragment.HomeFragment;
import com.example.oderapp.Fragment.ProfileFragment;
import com.example.oderapp.R;
import com.example.oderapp.SessionManage.SessionManagement;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ImageView seartMainActivity,logo;
    SessionManagement sessionManagement;
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
                    break;
                case R.id.dashboard:
                    selectFragment = new DashboardFragment();
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