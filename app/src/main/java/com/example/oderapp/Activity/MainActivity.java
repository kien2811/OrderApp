package com.example.oderapp.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.oderapp.Fragment.DashboardFragment;
import com.example.oderapp.Fragment.HomeFragment;
import com.example.oderapp.Fragment.ProfileFragment;
import com.example.oderapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ImageView seartMainActivity,logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
        mapping();
        seartMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));

            }
        });

    }


    private void mapping() {
        seartMainActivity  = findViewById(R.id.seartMainActivity);
        logo  = findViewById(R.id.logo);
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
                    break;



            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,selectFragment).commit();
            return  true;
        }
    };


}