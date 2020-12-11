package com.example.oderapp.Activity.OTP;

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
import com.example.oderapp.Activity.Change_email_Ativity;
import com.example.oderapp.Activity.Change_password_Activity;
import com.example.oderapp.Activity.Change_phone_Activity;
import com.example.oderapp.Activity.MainActivity;
import com.example.oderapp.Activity.VerifyOTPAcitivity;
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

public class OTPActivity extends AppCompatActivity {
    EditText inputcode1, inputcode2, inputcode3, inputcode4, inputcode5, inputcode6;
    private String Verification;
    Button ButtonVery;
    ProgressBar progressBar;
    TextView textmobl;
    SessionManagement sessionManagement;
    public static  String phone;
    public static  int id_render;
    public static  String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);
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
        email = (String) intent.getSerializableExtra("email");
        phone = (String) intent.getSerializableExtra("phones");
        id_render = (int) intent.getSerializableExtra("1");
//        String password = (String) getIntent().getSerializableExtra("password");
//        textMobile.setText(phone);
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
                    Toast.makeText(OTPActivity.this, "Bạn Đã Nhập Thiếu", Toast.LENGTH_SHORT).show();
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
                                        switch (id_render){
                                            case 1:
                                                Intent intent = new Intent(OTPActivity.this, Change_phone_Activity.class);
                                                intent.putExtra(" phones",phone);
                                                startActivity(intent);
                                                finish();
                                                break;
                                            case 2:
                                                Intent intent_email = new Intent(OTPActivity.this, Change_email_Ativity.class);
                                                intent_email.putExtra("email",email);
                                                startActivity(intent_email);
                                                finish();

                                                break;
                                            case 3:
                                                Intent intent_pass = new Intent(OTPActivity.this, Change_password_Activity.class);
                                                startActivity(intent_pass);
                                                finish();
                                                break;
                                        }
                                    } else {
                                        Toast.makeText(OTPActivity.this, "Mã Không Hợp Lệ", Toast.LENGTH_SHORT).show();
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
                        "+84" +phone,
                        60, TimeUnit.SECONDS,
                        OTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                            }

                            @Override
                            public void onCodeSent(@NonNull String newVeryficationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                Verification = newVeryficationId;
                                Toast.makeText(OTPActivity.this, "Gửi Mã Thành Công Vui Lòng Đợi 60 Giây Để Thử Lại !", Toast.LENGTH_SHORT).show();

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