package com.example.oderapp.Fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.oderapp.Activity.LoginActivity;
import com.example.oderapp.Activity.MainActivity;
import com.example.oderapp.R;
import com.example.oderapp.SessionManage.SessionManagement;

public class ProfileFragment extends Fragment {
    SharedPreferences sharedPreferences;
    TextView TxtMessage;
    SessionManagement sessionManagement;
    LinearLayout linr_password,linv_phone,linv_email,linv_cart;
    public  static  final String fileName = "login";
    public static final String Username = "Username";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button btn_logout;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btn_logout = view.findViewById(R.id.btn_logout);
        linr_password = view.findViewById(R.id.linr_password);
        linv_email = view.findViewById(R.id.linv_email);
        linv_phone = view.findViewById(R.id.linv_phone);
        linv_cart = view.findViewById(R.id.linv_cart);
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
        onlickData();
        return view;
    }

    private void onlickData() {
        linv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFeedbackDialog(Gravity.CENTER);
            }
        });
        linv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
            }
        });
        linr_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private  void openFeedbackDialog(int gravity){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_show_dialog);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        }else{
            dialog.setCancelable(false);
        }
        EditText editText = dialog.findViewById(R.id.feedback);
        Button btnNo = dialog.findViewById(R.id.btn_no_thank);
        Button btnThanks = dialog.findViewById(R.id.btn_send);

        btnThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "send feedback", Toast.LENGTH_SHORT).show();

            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}