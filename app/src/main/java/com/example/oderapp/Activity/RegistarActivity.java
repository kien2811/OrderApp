package com.example.oderapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RegistarActivity extends AppCompatActivity {
    Button btn_resigtar;
    TextView login;
     ProgressBar progressBar;
    EditText txt_username,txtphone,txtemail,txtpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);
        mapping();
        onlick();
    }

    private void onlick() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistarActivity.this,LoginActivity.class));
                finish();
            }
        });

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
                    progressBar.setVisibility(View.VISIBLE);
                    btn_resigtar.setVisibility(View.INVISIBLE);
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
//
                                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+84" + phone, 60, TimeUnit.SECONDS, RegistarActivity.this,
                                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                @Override
                                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                    progressBar.setVisibility(View.GONE);
                                                    btn_resigtar.setVisibility(View.VISIBLE);
                                                }

                                                @Override
                                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                                    progressBar.setVisibility(View.GONE);
                                                    btn_resigtar.setVisibility(View.VISIBLE);
                                                    Toast.makeText(RegistarActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                                }

                                                @Override
                                                public void onCodeSent(@NonNull String VeryficationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                    progressBar.setVisibility(View.GONE);
                                                    btn_resigtar.setVisibility(View.VISIBLE);
                                                    try {
                                                        jsonObject.getString("sucesfull");
//                                                        Toast.makeText(RegistarActivity.this, , Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getApplicationContext(), VerifyOTPAcitivity.class);
                                                        intent.putExtra("phone",phone);
                                                        intent.putExtra("password",password);
                                                        intent.putExtra("VeryficationId",VeryficationId);
                                                        startActivity(intent);
                                                        finish();

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            });
                                }
                                progressBar.setVisibility(View.GONE);
                                btn_resigtar.setVisibility(View.VISIBLE);
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
        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);

    }
}