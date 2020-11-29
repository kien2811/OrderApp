package com.example.oderapp.util;

public class Api {
    public static final  String HOST = "tailoha.xyz";

    public static final  String URl_SLIDER = "https://"+HOST+"/?controller=index&action=slider";
    public static final  String URl_PRODUCT_ODER = "https://"+HOST+"/?controller=index&action=product_oders&page=";
    public static final  String URl_PRODUCT_NEW = "https://"+HOST+"/?controller=index&action=product_new&page=";
    public static final  String URl_PRODUCT_SUGGESTION = "https://"+HOST+"/?controller=index&action=product_suggestion&page=";
    public static final  String URl_SEARCH = "https://"+HOST+"/?controller=index&action=search_product&search=";

    public static final  String URL_IMG_PROFILE = "http://192.168.1.11:8888/oder_cart_php/";
    public static final  String URl_REGISTER = "http://192.168.1.11:8888/oder_cart_php/public/?controller=index&action=register";
    public static final  String URl_CHANGE_PROFILE = "http://192.168.1.11:8888/oder_cart_php/public/?controller=index&action=change_profile";
    public static final  String URl_UPLOAD_IMAGE = "http://192.168.1.11:8888/oder_cart_php/public/?controller=index&action=user_profile&token=";
//    public static final  String URl_LOGIN = "https://"+HOST+"/?username="+username+"&password="+password+"";
//    public static final  String username = "https://"+HOST+"/?username="+username+"&password="+password+"";



//    https://tailoha.xyz/?controller=index&action=search_product
//    https://tailoha.xyz/?controller=index&action=slider
//    https://tailoha.xyz/?controller=index&action=product_suggestion
//    https://tailoha.xyz/?controller=index&action=product_oders
//    https://tailoha.xyz/?controller=index&action=product_new
//    https://tailoha.xyz/?/OderApp_OOP/public/?username=admin&password=admin
//    https://tailoha.xyz/?controller=index&action=decode_token&token=

    public static final  String URL_CHECK_ID_PRODUCT_CART = "http://192.168.1.11:8888/oder_cart_php/public/?controller=index&action=search_id_product_order_user&id_user=";
    public static final String URI_TOKEN_CART = "http://192.168.1.11:8888/oder_cart_php/public/?controller=index&action=user_order_cart&token=";
    public static final String ADD_CART = "http://192.168.1.11:8888/oder_cart_php/public/?controller=index&action=search_id_product_order_user&id_user=1&id_product=24&quantily=2&token=";
}