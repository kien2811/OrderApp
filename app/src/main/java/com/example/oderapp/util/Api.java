package com.example.oderapp.util;

public class Api {
    public static final  String HOST = "192.168.1.6:8089/oder_cart_php/public";

    public static final  String URl_SLIDER = "http://"+HOST+"/?controller=index&action=slider";
    public static final  String URl_PRODUCT_ODER = "http://"+HOST+"/?controller=index&action=product_oders&page=";
    public static final  String URl_PRODUCT_NEW = "http://"+HOST+"/?controller=index&action=product_new&page=";
    public static final  String URl_PRODUCT_SUGGESTION = "http://"+HOST+"/?controller=index&action=product_suggestion&page=";
    public static final  String URl_SEARCH = "http://"+HOST+"/?controller=index&action=search_product&search=";
    public static final  String URL_IMG_PROFILE = "http://"+HOST+"/";
    public static final  String URl_REGISTER = "http://"+HOST+"/?controller=index&action=register";
    public static final  String URl_CHANGE_PROFILE = "http://"+HOST+"/?controller=index&action=change_profile";
    public static final  String URl_UPLOAD_IMAGE = "http://"+HOST+"/?controller=index&action=user_profile&token=";
    public static final  String URl_LOGIN = "http://"+HOST+"/";
    public static final  String URL_ALL_PRODUCT = "http://"+HOST+"/?controller=index&action=all_product&page=";
    public static final  String URL_PRODUCT_SALE = "http://"+HOST+"/?controller=index&action=product_sale&page=";
    public static final  String URL_PRODUCT_HOST = "http://"+HOST+"/?controller=index&action=product_host&page=";
    public static final   String URL_UPLOAD_AVATAR ="http://192.168.1.6:8089/oder_cart_php/public/?controller=index&action=change_profile";

//    public static final  String URl_LOGIN = "https://"+HOST+"/?username="+username+"&password="+password+"";
//    public static final  String username = "https://"+HOST+"/?username="+username+"&password="+password+"";



//    https://tailoha.xyz/?controller=index&action=search_product
//    https://tailoha.xyz/?controller=index&action=slider
//    https://tailoha.xyz/?controller=index&action=product_suggestion
//    https://tailoha.xyz/?controller=index&action=product_oders
//    https://tailoha.xyz/?controller=index&action=product_new
//    https://tailoha.xyz/?/OderApp_OOP/public/?username=admin&password=admin
//    https://tailoha.xyz/?controller=index&action=decode_token&token=

    public static final  String URL_CHECK_ID_PRODUCT_ODER_USER = "http://"+HOST+"/?controller=index&action=select_id_product_order_user&id_user=";
    public static final String URL_UPDATE_ID_PRODUCT_ORDER_USER = "http://"+HOST+"/?controller=index&action=update_id_product_order_user";
    public static final String URL_INSERT_TO_CART_ORDER_USER = "http://"+HOST+"/?controller=index&action=search_id_product_order_user";
    public static final String URL_UPDATE_QUANTILY_ORDER_USER = "http://"+HOST+"/?controller=index&action=update_id_product_order_user";
    public static final String URL_DELETE_ID_PRODUCT_ODER_USER = "http://"+HOST+"/?controller=index&action=delete_id_product_order_user";
    public static final String URI_TOKEN_CART = "http://"+HOST+"/?controller=index&action=user_order_cart&token=";
    public static final String ADD_CART = "http://"+HOST+"/?controller=index&action=search_id_product_order_user&id_user=1&id_product=24&quantily=2&token=";
}
