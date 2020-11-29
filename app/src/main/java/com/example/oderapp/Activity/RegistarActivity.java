package com.example.oderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.oderapp.R;
import com.example.oderapp.util.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistarActivity extends AppCompatActivity {
    Button btn_resigtar;
    EditText txt_username,txtphone,txtemail,txtpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);
        mapping();
        onlick();
    }

    private void onlick() {
        btn_resigtar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = txt_username.getText().toString().trim();
                String phone = txtphone.getText().toString().trim();
                String email = txtemail.getText().toString().trim();
                String password = txtpassword.getText().toString().trim();
                if(fullname.equals("")||phone.equals("")||email.equals("")||password.equals("")){
                    Toast.makeText(RegistarActivity.this, "Vui Lòng Nhập Đầy Đủ Thông Tin !", Toast.LENGTH_SHORT).show();
                }else{

                    StringRequest request = new StringRequest(Request.Method.POST, Api.URl_REGISTER, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String errors = jsonObject.getString("1");
//                                String sucsec = jsonObject.getString("2");
                                Log.d("onResponse: ",errors);
                                if(errors.equals("1")){
                                    Toast.makeText(RegistarActivity.this, jsonObject.getString("errors"), Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(RegistarActivity.this, jsonObject.getString("sucesfull"), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegistarActivity.this,LoginActivity.class));
                                    finish();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error: ",error.toString());

                        }
                    }){@Override
                    public Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("fullname", fullname);
                        map.put("phone", phone);
                        map.put("email", email);
                        map.put("password", password);
                        return map;
                    }};
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(request);
                }
            }
        });

    }

    private void mapping() {
        btn_resigtar = findViewById(R.id.btn_resigtar);
        txt_username = findViewById(R.id.txtfull_name);
        txtphone = findViewById(R.id.txtphone);
        txtemail = findViewById(R.id.txtemail);
        txtpassword = findViewById(R.id.txtpassword);
    }
}