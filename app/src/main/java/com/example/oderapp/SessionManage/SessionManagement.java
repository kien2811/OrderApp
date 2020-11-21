package com.example.oderapp.SessionManage;

import android.content.Context;
import android.content.SharedPreferences;

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
    // create get login method
    public  boolean getLogin(){
        return sharedPreferences.getBoolean("KEY_LOGIN",false);
    }
    // create set username method
    public  void setUsername(String username){
        editor.putString("KEY_USERNAME",username);
        editor.commit();
    }
    // create get username method
    public String getUsername(){
        return  sharedPreferences.getString("KEY_USERNAME","");
    }
}
