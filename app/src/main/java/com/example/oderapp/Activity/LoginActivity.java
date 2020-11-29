package com.example.oderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.oderapp.MySingleton.MySingleton;
import com.example.oderapp.R;
import com.example.oderapp.SessionManage.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button btn_login;
    EditText edutUsername, editPassword;
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
                    if (password.equals("") || username.equals("")) {
                        Toast.makeText(LoginActivity.this, "Vui Lòng Nhập Tài Khoản Mật Khẩu", Toast.LENGTH_SHORT).show();

                    } else {
                        String url = "http://192.168.1.11:8888/OderApp_OOP/public/?username=" + username + "&password=" + password + "";
                        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    String username = response.getString("username");
                                    String sussecfully = response.getString("sussecfully");
                                    String token = response.getString("token");
                                    int id_user = response.getInt("id_user");


                                    if(sussecfully.equals("sussecfully")){
                                        sessionManagement.setLogin(true);
                                        sessionManagement.setUsername(username);
                                        sessionManagement.setToken(token);
                                        sessionManagement.setIdUser(id_user);
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        finish();
                                        Log.d("onResponse: ",token);
//                                        Toast.makeText(LoginActivity.this, "Thanh Cong", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    Toast.makeText(LoginActivity.this, "Sai Tài Khoản Mật Khẩu", Toast.LENGTH_SHORT).show();

                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            public Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("username", username);
                                map.put("password", password);
                                return map;
                            }
                        };
                        requestQueue.add(jsonObjectRequest);




                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "Có Lỗi Sảy Ra Vui Lòng Thử Lại Sau", Toast.LENGTH_SHORT).show();

                }


            }
        });
        //if user already logged in
        if (sessionManagement.getLogin()) {
            //location Main Activity
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
        builder.setMessage("Bạn Có Muốn Thoát !");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.currentTimeMillis();
                finish();
                return;
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