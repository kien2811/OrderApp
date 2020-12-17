package com.example.oderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.oderapp.Adapter.MessageAdapter;
import com.example.oderapp.Model.Message;
import com.example.oderapp.R;
import com.example.oderapp.SessionManage.SessionManagement;
import com.example.oderapp.util.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {
    Toolbar toolbarCartsMEssage;
     RecyclerView rcy_message;
     Button btn_send;
     EditText editTextms;
     MessageAdapter messageAdpater;
    SessionManagement sessionManagement;
    List<Message> messageList;
    public static  String Apis;
    public static   String strMessgae ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        mapping();
        sessionManagement = new SessionManagement(this);
        Apis = Api.URL_READ_MESSAGE_USER+sessionManagement.getToken();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcy_message.setLayoutManager(linearLayoutManager);
        getData();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        editTextms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkKeyload();
            }
        });
        handler.postDelayed(runnable,1000);

    }



    private void getData() {
        messageList = new ArrayList<>();
        messageAdpater = new MessageAdapter(MessageActivity.this,messageList);
        rcy_message.setAdapter(messageAdpater);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Apis, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String id_send = jsonObject.getString("from_id_user").toString();
                        messageList.add(new Message(jsonObject.getString("id"),jsonObject.getString("img"),
                                checkUser(id_send),
                                jsonObject.getString("message")));
//                        Log.d( "onResponse: ",jsonObject.getString("to_id_user"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    messageAdpater.notifyDataSetChanged();

                }
                handler.postDelayed(runnable,1000);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }

    private void checkKeyload(){
        final  View activityRootView = findViewById(R.id.actitviviewgroup);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                activityRootView.getWindowVisibleDisplayFrame(r);
                int heightDiff = activityRootView.getRootView().getHeight() - r.height();
                if(heightDiff > 0.25*activityRootView.getRootView().getHeight()){
                    if(messageList.size() >0){
                        rcy_message.scrollToPosition(messageList.size()-1);
                        activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }
    private void sendMessage() {
         strMessgae = editTextms.getText().toString().trim();
        if(TextUtils.isEmpty(strMessgae)){
            Toast.makeText(this, "Bạn Chưa Nhập Gì", Toast.LENGTH_SHORT).show();
        }else{
            sendApi();
        }
        rcy_message.scrollToPosition(messageList.size()-1);
        editTextms.setText("");
    }

    private void sendApi() {
        StringRequest request = new StringRequest(Request.Method.POST, Api.URL_SEND_MESSAGE_USER+sessionManagement.getToken(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onResponse: ",response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){@Override
        public Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> map = new HashMap<>();
            map.put("message", strMessgae);
            map.put("id_user",sessionManagement.getIduser()+"");
            return map;
        }};
        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        requestQueue1.add(request);
    }

    private boolean checkUser(String id_user) {
//        Log.d( "checkUser: ", String.valueOf(sessionManagement.getIduser()));
        if(String.valueOf(sessionManagement.getIduser()).equals(id_user)){
             return  false;
        }else{
            return true;
        }

    }

    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this,2000);//60 second delay
        }
    };
    private void mapping() {
        toolbarCartsMEssage = findViewById(R.id.toolbarCartsMEssage);
        rcy_message = findViewById(R.id.rcy_message);
        btn_send = findViewById(R.id.btn_send);
        editTextms = findViewById(R.id.ed_message);
        setSupportActionBar(toolbarCartsMEssage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tin Nhắn Của Bạn");
        toolbarCartsMEssage.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}