package com.example.oderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.oderapp.R;
import com.example.oderapp.SessionManage.SessionManagement;

public class LoginActivity extends AppCompatActivity {
    RelativeLayout btn_login;
    EditText edutUsername,editPassword;
    SharedPreferences sharedPreferences;
    SessionManagement sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mapping();
        checklogin();
    }

    private void checklogin() {
        sessionManagement = new SessionManagement(getApplicationContext());
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edutUsername.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                try {
                    if(password.equals("") || username.equals("")){
                        Toast.makeText(LoginActivity.this, "Vui Lòng Nhập Tài Khoản Mật Khẩu", Toast.LENGTH_SHORT).show();

                    }else if(password.equals("admin") && username.equals("admin")){
                        sessionManagement.setLogin(true);
                        sessionManagement.setUsername(username);
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Có Lỗi Sảy Ra Vui Lòng Thử Lại Sau", Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e){
                    Toast.makeText(LoginActivity.this, "Có Lỗi Sảy Ra Vui Lòng Thử Lại Sau", Toast.LENGTH_SHORT).show();

                }




            }
        });
        //if user already logged in
        if(sessionManagement.getLogin()){
            //location Main Activity
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }



    private void mapping() {
        btn_login = findViewById(R.id.btn_login);
        edutUsername = findViewById(R.id.edutUsername);
        editPassword = findViewById(R.id.editPassword);

    }



}