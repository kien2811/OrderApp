package com.example.oderapp.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.oderapp.Activity.LoginActivity;
import com.example.oderapp.Activity.MainActivity;
import com.example.oderapp.R;
import com.example.oderapp.SessionManage.SessionManagement;

public class ProfileFragment extends Fragment {
    SharedPreferences sharedPreferences;
    TextView TxtMessage;
    SessionManagement sessionManagement;
    public  static  final String fileName = "login";
    public static final String Username = "Username";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button btn_logout;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btn_logout = view.findViewById(R.id.btn_logout);
        TxtMessage = view.findViewById(R.id.txtusername);
        sessionManagement = new SessionManagement(getContext());
        //get user name to session
        String username = sessionManagement.getToken();
        TxtMessage.setText(username);
        Log.d("token",username);
//        sharedPreferences = getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
//        if(sharedPreferences.contains(Username)){
//            TxtMessage.setText("Hell"+sharedPreferences.getString(Username,""));
//        }
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  // alert dilog  builder
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
  // set title
  builder.setTitle("Đăng Xuất");
  // set message
                builder.setMessage("Bạn Có Trắc Chắn Muốn Đăng Xuất Không ?");
//set positive button
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sessionManagement.setLogin(false);
                        sessionManagement.setUsername("");
                        sessionManagement.setToken("");
                        Intent intent = new Intent(getContext().getApplicationContext(),LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getContext().startActivity(intent);
                        getActivity().finish();
                    }
                });
//set negative button
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return view;
    }



}