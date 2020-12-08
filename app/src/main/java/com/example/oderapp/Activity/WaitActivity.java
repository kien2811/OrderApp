package com.example.oderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.oderapp.R;
import com.example.oderapp.util.CheckNetWork;

public class WaitActivity extends AppCompatActivity {
    private  static int SPLAS_TIME_OUT = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        load_data();
    }

    private void load_data() {
        if(CheckNetWork.isNetwABoolean(this)){
            // co mang
            // load data
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(WaitActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            },SPLAS_TIME_OUT);
        }else{
            // khong co mang
            Toast.makeText(this, "Bạn Không Có Internet", Toast.LENGTH_SHORT).show();
        }

    }
}