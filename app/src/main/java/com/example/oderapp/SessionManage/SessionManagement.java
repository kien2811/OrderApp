package com.example.oderapp.SessionManage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.oderapp.Activity.LoginActivity;
import com.example.oderapp.Activity.MainActivity;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences("App_key",0);
        editor = sharedPreferences.edit();
        editor.apply();
    }
    // create set login
    public void setLogin(Boolean login){
        editor.putBoolean("KEY_LOGIN",login);
        editor.commit();
    }
    public void setIdUser(int idUser){
        editor.putInt("KEY_ID",idUser);
        editor.commit();
    }
    public int getIduser(){
        return sharedPreferences.getInt("KEY_ID",0);
    }
    // create get login method
    public  boolean getLogin(){
        return sharedPreferences.getBoolean("KEY_LOGIN",false);
    }
    // create set username method
    public  void setUsername(String username){
        editor.putString("KEY_USERNAME",username);
        editor.commit();
    }

    public  void setToken(String token){
        editor.putString("KEY_TOKEN",token);
        editor.commit();
    }
    public String getToken(){
        return sharedPreferences.getString("KEY_TOKEN","");
    }
    // create get username method
    public String getUsername(){
        return  sharedPreferences.getString("KEY_USERNAME","");
    }
}
