package com.example.oderapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.android.volley.toolbox.Volley;
import com.example.oderapp.R;
import com.example.oderapp.SessionManage.SessionManagement;
import com.example.oderapp.util.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerifyOTPAcitivity extends AppCompatActivity {
    EditText inputcode1, inputcode2, inputcode3, inputcode4, inputcode5, inputcode6;
    private String Verification;
    Button ButtonVery;
    ProgressBar progressBar;
    TextView textmobl;
    SessionManagement sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p_acitivity);
        TextView textMobile = findViewById(R.id.textmoblile);
        Intent intent = getIntent();
        inputcode1 = findViewById(R.id.inputCode);
        inputcode2 = findViewById(R.id.inputCode1);
        inputcode3 = findViewById(R.id.inputCode2);
        inputcode4 = findViewById(R.id.inputCode3);
        inputcode5 = findViewById(R.id.inputCode4);
        inputcode6 = findViewById(R.id.inputCode5);
        textmobl = findViewById(R.id.textmobl);

        progressBar = findViewById(R.id.progressbar);
        ButtonVery = findViewById(R.id.btn_very);
        sessionManagement = new SessionManagement(getApplicationContext());
        Verification = getIntent().getStringExtra("VeryficationId");
        String phone = (String) intent.getSerializableExtra("phone");
        String password = (String) getIntent().getSerializableExtra("password");
        textMobile.setText(phone);
        setupOTPinput();
        ButtonVery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputcode1.getText().toString().trim().isEmpty() ||
                        inputcode2.getText().toString().trim().isEmpty() ||
                        inputcode3.getText().toString().trim().isEmpty() ||
                        inputcode4.getText().toString().trim().isEmpty() ||
                        inputcode5.getText().toString().trim().isEmpty() ||
                        inputcode6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(VerifyOTPAcitivity.this, "Bạn Đã Nhập Thiếu", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = inputcode1.getText().toString() +
                        inputcode2.getText().toString() +
                        inputcode3.getText().toString() +
                        inputcode4.getText().toString() +
                        inputcode5.getText().toString() +
                        inputcode6.getText().toString();
                if (Verification != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    ButtonVery.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(Verification, code);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    ButtonVery.setVisibility(View.VISIBLE);
                                    if (task.isSuccessful()) {
                                        String login = Api.URl_LOGIN+"?username=" + phone + "&password=" + password + "";

                                        RequestQueue requestQueue = Volley.newRequestQueue(VerifyOTPAcitivity.this);
                                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, login, null, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {

                                                    String username = response.getString("username");
                                                    String sussecfully = response.getString("sussecfully");
                                                    String token = response.getString("token");
                                                    String fullname = response.getString("full_name");
                                                    int id_user = response.getInt("id_user");
                                                    int Phone = response.getInt("phone");


                                                    if(sussecfully.equals("sussecfully")){
                                                        sessionManagement.setLogin(true);
                                                        sessionManagement.setUsername(username);
                                                        sessionManagement.setToken(token);
                                                        sessionManagement.setIdUser(id_user);
                                                        sessionManagement.setFullName(fullname);
                                                        sessionManagement.setPhone(Phone);
                                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                                        startActivity(intent);
                                                        finish();
                                                        Log.d("onResponse: ",token);
                                                    }

                                                } catch (JSONException e) {

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
                                                map.put("username", phone);
                                                map.put("password", password);
                                                return map;
                                            }
                                        };
                                        requestQueue.add(jsonObjectRequest);

                                    } else {
                                        Toast.makeText(VerifyOTPAcitivity.this, "Mã Không Hợp Lệ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        textmobl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+84" + getIntent().getStringExtra("phone"),
                        60, TimeUnit.SECONDS,
                        VerifyOTPAcitivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(VerifyOTPAcitivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newVeryficationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                Verification = newVeryficationId;
                                Toast.makeText(VerifyOTPAcitivity.this, "OTP sent", Toast.LENGTH_SHORT).show();

                            }
                        }
                );
            }
        });
    }

    private void setupOTPinput() {
        inputcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputcode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputcode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputcode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputcode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputcode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}