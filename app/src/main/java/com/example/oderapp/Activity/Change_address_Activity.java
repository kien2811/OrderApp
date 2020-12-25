package com.example.oderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.oderapp.R;
import com.example.oderapp.SessionManage.SessionManagement;
import com.example.oderapp.util.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Change_address_Activity extends AppCompatActivity {
    Toolbar toolbar;
    EditText txt_change_address;
    Button btn_address;
    SessionManagement sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_address_);

        mapping();
        sessionManagement = new SessionManagement(Change_address_Activity.this);
        Intent intent = getIntent();
        String address = (String) intent.getSerializableExtra("address");
        txt_change_address.setText(address);
    }
    private void mapping() {
        toolbar = findViewById(R.id.toolbar);
        txt_change_address = findViewById(R.id.txt_change_address);
        btn_address = findViewById(R.id.btn_address);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cập nhật địa chỉ của bạn");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        btn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_change_addresss = txt_change_address.getText().toString().trim();
                if(txt_change_addresss.equals("")){
                    Toast.makeText(Change_address_Activity.this, "Bạn Chưa Nhập Thông Tin", Toast.LENGTH_SHORT).show();
                }else{
                    StringRequest request = new StringRequest(Request.Method.POST, Api.URl_CHANGE_PROFILE, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String errors = jsonObject.getString("1");
                                Log.d("onResponse: ",errors);
                                if(errors.equals("1")){
                                    Toast.makeText(Change_address_Activity.this, jsonObject.getString("errors"), Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(Change_address_Activity.this, jsonObject.getString("sucesfull"), Toast.LENGTH_SHORT).show();
                                    sessionManagement.setAddress(txt_change_addresss);
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
                        map.put("address", txt_change_addresss);
                        map.put("token", sessionManagement.getToken());

                        return map;
                    }};
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(request);
                }
            }
        });

    }
}