package com.example.oderapp.util;

public class Api {
<<<<<<< HEAD
    public static final  String HOST = "192.168.1.3:8089/oder_cart_php/public";
=======
    public static final  String HOST = "192.168.1.11:8888/oder_cart_php/public";
>>>>>>> 988e76454aae16d8ca8c7e9ec8937dca3ee4c602

    public static final  String URl_SLIDER = "http://"+HOST+"/?controller=index&action=slider";
    public static final  String URl_SLIDER_PRODUCT = "http://"+HOST+"/?controller=index&action=Select_Slider_Product&id_product=";
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
    public static final   String URL_UPLOAD_AVATAR ="http://"+HOST+"/?controller=index&action=change_profile";
    public static final   String URL_DON_MUA_CHO_XAC_NHAN ="http://"+HOST+"/?controller=index&action=Select_transaction_data_to_id_user_Cho_Xac_Nhan&id_user=";
    public static final   String URL_DON_MUA_DA_HUY ="http://"+HOST+"/?controller=index&action=Select_transaction_data_to_id_user_Da_Huy&id_user=";
    public static final   String URL_DON_MUA_DA_MUA ="http://"+HOST+"/?controller=index&action=Select_transaction_data_to_id_user_Da_Mua&id_user=";
    public static final   String URL_DON_MUA_DANG_GIAO ="http://"+HOST+"/?controller=index&action=Select_transaction_data_to_id_user_Dang_Giao&id_user=";
    public static final   String URL_UPDATE_ID_TRANSACTION_DATE_TO_HUY_DON_HANG ="http://"+HOST+"/?controller=index&action=Update_transaction_data_to_id_Huy_Don_Hang";
    public static final   String URL_READ_MESSAGE_USER ="http://"+HOST+"/?controller=index&action=Api_user_read_message&token=";
    public static final   String URL_SEND_MESSAGE_USER ="http://"+HOST+"/?controller=index&action=Insert_user_message&token=";

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
//    public static final String ADD_CART = "http://"+HOST+"/?controller=index&action=search_id_product_order_user&id_user=1&id_product=24&quantily=2&token=";
    public static final String URL_INSERT_TRANSATION = "http://"+HOST+"/?controller=index&action=insert_transaction_data_to_user_order";


    public static final String URL_SELECT_DON_MUA_PROFILE = "http://"+HOST+"/?controller=index&action=Select_transaction_data_to_id_user";
// gọi sản phẩm gợi ý tương tự sản phẩm trong chi tiết sản phẩm
    public static final  String URl_SELECT_PRODUCT_DONG_GIA = "http://"+HOST+"/?controller=index&action=Select_product_dong_gia&price=";
    //gợi ý tương tự trong màn hình chi tiết
    public static final  String URl_SELECT_PRODUCT_TUONG_TU = "http://"+HOST+"/?controller=index&action=Select_product_tuong_tu&name=";
    // gọi bảng size cho chi tiết sản phẩm
    public static final  String URl_SELECT_PRODUCT_SIZE = "http://"+HOST+"/?controller=index&action=Select_product_size&id_product=";
}
