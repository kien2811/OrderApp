 package com.example.oderapp.Fragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.oderapp.Activity.CartActivity;
import com.example.oderapp.Activity.Change_email_Ativity;
import com.example.oderapp.Activity.Change_password_Activity;
import com.example.oderapp.Activity.Change_phone_Activity;
import com.example.oderapp.Activity.DonMuaActivity;
import com.example.oderapp.Activity.LoginActivity;
import com.example.oderapp.Fragment.DonMuaFragment.DaMuaFragment;
import com.example.oderapp.Fragment.DonMuaFragment.DangGiaoFragment;
import com.example.oderapp.MySingleton.MySingleton;
import com.example.oderapp.R;
import com.example.oderapp.SessionManage.SessionManagement;
import com.example.oderapp.util.Api;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    SharedPreferences sharedPreferences;
    TextView txtusername,txt_email,phone,txtvCart;
    ImageView imgAva;
    SwipeRefreshLayout swipeRefresh;
    SessionManagement sessionManagement;
    LinearLayout linr_password,linv_phone,linv_email,linv_cart,linearLayoutCart,linearLayoutChoXacNhan,linearLayoutDangGiao,linearLayoutDaMua;
    Button btn_logout,upload_to_sever;
    ImageButton imageButton_open_file;
    Bitmap bitmap;
    String encodeImageString;

    public  static  final String fileName = "login";
    public static final String Username = "Username";
    public static String full_name;
    public static String email;
    public static String phones;
    public static String img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btn_logout = view.findViewById(R.id.btn_logout);
        linr_password = view.findViewById(R.id.linr_password);
        linv_email = view.findViewById(R.id.linv_email);
        linv_phone = view.findViewById(R.id.linv_phone);
        linv_cart = view.findViewById(R.id.linv_cart);
        linearLayoutCart = view.findViewById(R.id.linearLayoutCart);
        linearLayoutChoXacNhan = view.findViewById(R.id.linearLayoutChoXacNhan);
        linearLayoutDangGiao = view.findViewById(R.id.linearLayoutDangGiao);
        linearLayoutDaMua = view.findViewById(R.id.linearLayoutDaMua);

        txtusername = view.findViewById(R.id.txtusername);
        txt_email = view.findViewById(R.id.txt_email);
        txtvCart = view.findViewById(R.id.txtvCart);
        upload_to_sever = view.findViewById(R.id.upload_to_sever);
        phone = view.findViewById(R.id.phone);
        imgAva = view.findViewById(R.id.imgAva);
        imageButton_open_file = view.findViewById(R.id.imageButtonupfile);
        swipeRefresh = view.findViewById(R.id.swipeRefreshprofile);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.purple_500));
        sessionManagement = new SessionManagement(getContext());
        //get user name to session
//        TxtMessage.setText(username);
        load_data_profile();
        onlickData();
        getDataCart();
        return view;


    }
    private void getDataCart() {
        sessionManagement = new SessionManagement(getContext());
        String token = sessionManagement.getToken();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URI_TOKEN_CART+token, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0 ; i < response.length();i ++){
                    txtvCart.setText(" "+(i+1));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), "error"+error, Toast.LENGTH_SHORT).show();
                Log.d("error",error.toString());
//                Toast.makeText(getContext(), "Giỏ hàng của bạn trống !", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);

    }
    private void load_data_profile() {
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Api.URl_UPLOAD_IMAGE+sessionManagement.getToken(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                try {
                    jsonObject = response.getJSONObject(0);
                    full_name =  jsonObject.getString("full_name");
                     email =  jsonObject.getString("email");
                    phones =  jsonObject.getString("Phone");
                     img =  Api.URL_IMG_PROFILE+jsonObject.getString("img");
//                    Log.d("onResponse: ",img);
//                    Log.d("onResponse: ",sessionManagement.getToken());
                    txtusername.setText(full_name);
                    Picasso.get().load(img)
                            .placeholder(R.drawable.loader)
                            .error(R.drawable.noimage)
                            .into(imgAva);
                    txt_email.setText(email);
                    phone.setText(phones);
                    phone.clearFocus();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d( "onResponse: ",error.toString());

            }
        });
        MySingleton.getInstance(getContext().getApplicationContext()).addToRequestQueue(arrayRequest);

    }

    private void onlickData() {
        imageButton_open_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity((Activity) getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"browse Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                    permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        upload_to_sever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload_avtar();
            }
        });
        imgAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getContext(), UpFile_Activity.class));
            }
        });
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
        linv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Change_email_Ativity.class);
                intent.putExtra("email",email);
                startActivity(intent);
//                openFeedbackDialog(Gravity.CENTER,email);
            }
        });
        linv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Change_phone_Activity.class);
                intent.putExtra("phones",phones);
                startActivity(intent);
//                openFeedbackDialog(Gravity.CENTER,phones);

            }
        });
        linr_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Change_password_Activity.class);
//                intent.putExtra("phones",phones);
                startActivity(intent);
//                openFeedbackDialog(Gravity.CENTER,"");
            }
        });
        linearLayoutCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);
            }
        });
        linearLayoutChoXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DonMuaActivity.class);
                startActivity(intent);
            }
        });
        linearLayoutDangGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DonMuaActivity.class);
                startActivity(intent);
            }
        });
        linearLayoutDaMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DonMuaActivity.class);
                startActivity(intent);
            }
        });
    }

    private void upload_avtar() {

        StringRequest request_avatar = new StringRequest(Request.Method.POST, Api.URL_UPLOAD_AVATAR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Đổi Avatar Thành Công", Toast.LENGTH_SHORT).show();

                try {

                    JSONObject  jsonObject = new JSONObject(response);
                    String errors = jsonObject.getString("1");

                    Picasso.get().load(img)
                            .placeholder(R.drawable.loader)
                            .error(R.drawable.noimage)
                            .into(imgAva);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d( "error: ",error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<>();
                map.put("upload",encodeImageString);
                map.put("token",sessionManagement.getToken());
                return map;
            }
        };
        RequestQueue requestQueue_avatar = Volley.newRequestQueue(getContext().getApplicationContext());
        requestQueue_avatar.add(request_avatar);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode ==1 && resultCode == RESULT_OK){
            Uri file_path = data.getData();
            try {
                InputStream inputStream =  getContext().getContentResolver().openInputStream(file_path);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgAva.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte[] bytesofimage= byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
//        if(encodeImageString.equals(null)){
//            Toast.makeText( getContext(),"Bạn Chưa Chọn Ảnh Nào !", Toast.LENGTH_SHORT).show();
//        }
//        Log.d( "getParams: ",encodeImageString.toString());

    }

    private  void openFeedbackDialog(int gravity, String data_change){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_show_dialog);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        EditText feedback = getView().findViewById(R.id.feedback);
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
        editText.setHint(data_change);

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


    @Override
    public void onRefresh() {
        load_data_profile();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
            }
        },2000);
    }
}