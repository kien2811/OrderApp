package com.example.oderapp.util;

public class Api {
    public static final  String HOST = "tailoha.xyz";

    public static final  String URl_SLIDER = "https://"+HOST+"/?controller=index&action=slider";
    public static final  String URl_PRODUCT_ODER = "https://"+HOST+"/?controller=index&action=product_oders&page=";
    public static final  String URl_PRODUCT_NEW = "https://"+HOST+"/?controller=index&action=product_new&page=";
    public static final  String URl_PRODUCT_SUGGESTION = "https://"+HOST+"/?controller=index&action=product_suggestion&page=";
    public static final  String URl_SEARCH = "https://"+HOST+"/?controller=index&action=search_product&search=";
//    public static final  String URl_LOGIN = "https://"+HOST+"/?username="+username+"&password="+password+"";
//    public static final  String username = "https://"+HOST+"/?username="+username+"&password="+password+"";



//    https://tailoha.xyz/?controller=index&action=search_product
//    https://tailoha.xyz/?controller=index&action=slider
//    https://tailoha.xyz/?controller=index&action=product_suggestion
//    https://tailoha.xyz/?controller=index&action=product_oders
//    https://tailoha.xyz/?controller=index&action=product_new
//    https://tailoha.xyz/?/OderApp_OOP/public/?username=admin&password=admin
//    https://tailoha.xyz/?controller=index&action=decode_token&token=


    public static final  String HOST_COMPUTER = "192.168.1.11";
    public static final  String URL_CHECK_ID_PRODUCT_CART = "http://"+HOST_COMPUTER+":8888/OderApp_OOP/public/?controller=index&action=search_id_product_order_user&id_user=";
    public static final String URI_TOKEN_CART = "http://"+HOST_COMPUTER+":8888/OderApp_OOP/public/?controller=index&action=decode_token&token=";
    public static final String URL_CHECK_LOGIN = "http://"+HOST_COMPUTER+":8888/OderApp_OOP/?username=";
    public static final String ADD_CART = "http://"+HOST_COMPUTER+":8888/OderApp_OOP/public/?controller=index&action=search_id_product_order_user&id_user=1&id_product=24&quantily=2&token=";
}
