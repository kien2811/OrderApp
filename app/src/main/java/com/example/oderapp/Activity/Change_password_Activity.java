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

public class Change_password_Activity extends AppCompatActivity {
    Toolbar toolbarCartmail;
    EditText txt_change_password2,txt_change_password;
    Button btn_change_pass;
    SessionManagement sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mapping();
        sessionManagement = new SessionManagement(Change_password_Activity.this);
//        Intent intent = getIntent();
//        String phone = (String) intent.getSerializableExtra("phones");
//        txt_change_password.setHint(phone);

    }

    private void mapping() {
        toolbarCartmail = findViewById(R.id.toolbarCartsss);
        txt_change_password = findViewById(R.id.txt_change_password);
        txt_change_password2 = findViewById(R.id.txt_change_password2);
        btn_change_pass = findViewById(R.id.btn_change_pass);

        setSupportActionBar(toolbarCartmail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đổi Mật Khẩu Của Bạn !");
        toolbarCartmail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        btn_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_change_pass = txt_change_password.getText().toString().trim();
                String txt_change_pass2 = txt_change_password2.getText().toString().trim();
                if(txt_change_pass.equals("")||txt_change_pass2.equals("")){
                    Toast.makeText(Change_password_Activity.this, "Bạn Chưa Nhập Thông Tin", Toast.LENGTH_SHORT).show();
                }else{
                    StringRequest request = new StringRequest(Request.Method.POST, Api.URl_CHANGE_PROFILE, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String errors = jsonObject.getString("1");
                                Log.d("onResponse: ",errors);
                                if(errors.equals("1")){
                                    Toast.makeText(Change_password_Activity.this, jsonObject.getString("errors"), Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(Change_password_Activity.this, jsonObject.getString("sucesfull"), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Change_password_Activity.this,MainActivity.class));
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
                        map.put("passwords", txt_change_pass);
                        map.put("passwords2", txt_change_pass2);
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