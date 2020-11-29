package com.example.oderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

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

public class Change_email_Ativity extends AppCompatActivity {
    Toolbar   toolbarCartmail;
    EditText txt_change_eamil;
    Button btn_change;
    SessionManagement sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email__ativity);
        maping();
        Intent intent = getIntent();
        String email = (String) intent.getSerializableExtra("email");
        txt_change_eamil.setHint(email);
        sessionManagement = new SessionManagement(Change_email_Ativity.this);
        Log.d( "onCreate: ",sessionManagement.getToken());
    }

    private void maping() {
        toolbarCartmail = findViewById(R.id.toolbarCarts);
        txt_change_eamil = findViewById(R.id.txt_change_eamil);
        btn_change = findViewById(R.id.btn_change);

        setSupportActionBar(toolbarCartmail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đổi Mail Của Bạn");
        toolbarCartmail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txt_change_eamil.getText().toString().trim();
               if(email.equals("")){
                   Toast.makeText(Change_email_Ativity.this, "Bạn Chưa Nhập Thông Tin", Toast.LENGTH_SHORT).show();
               }else{
                   StringRequest request = new StringRequest(Request.Method.POST, Api.URl_CHANGE_PROFILE, new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           try {
                               JSONObject jsonObject = new JSONObject(response);
                               String errors = jsonObject.getString("1");
                               Log.d("onResponse: ",errors);
                               if(errors.equals("1")){
                                   Toast.makeText(Change_email_Ativity.this, jsonObject.getString("errors"), Toast.LENGTH_SHORT).show();
                               }else {
                                   Toast.makeText(Change_email_Ativity.this, jsonObject.getString("sucesfull"), Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(Change_email_Ativity.this,MainActivity.class));
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
                       map.put("email", email);
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