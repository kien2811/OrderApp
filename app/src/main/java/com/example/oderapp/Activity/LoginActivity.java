package com.example.oderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.oderapp.R;
import com.example.oderapp.SessionManage.SessionManagement;

public class LoginActivity extends AppCompatActivity {
    Button btn_login;
    EditText edutUsername,editPassword;
    SharedPreferences sharedPreferences;
    SessionManagement sessionManagement;
    long backPressed;
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
            finish();
        }
    }

    @Override
    public void onBackPressed() {

//        if(backPressed + 3000 > System.currentTimeMillis()){
//            super.onBackPressed();
//            return;
//        }else{
//            Toast.makeText(this, "Chạm Lần Nữa Để Thoát !", Toast.LENGTH_SHORT).show();
//        }
//        backPressed = System.currentTimeMillis();


        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        // set title
        // set message
        builder.setMessage("Bạn Có Thực Sự Muốn Thoát !");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.currentTimeMillis();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void mapping() {
        btn_login = findViewById(R.id.btn_login);
        edutUsername = findViewById(R.id.edutUsername);
        editPassword = findViewById(R.id.editPassword);

    }



}