package com.example.oderapp.Fragment.SanPhamFragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.oderapp.Activity.CartActivity;
import com.example.oderapp.Activity.DetailCartActivity;
import com.example.oderapp.Activity.MyDatabase;
import com.example.oderapp.Adapter.Product_Dashboard_sanPham_Adapter;
import com.example.oderapp.Model.DashboardSanPham;
import com.example.oderapp.R;

import java.util.ArrayList;
import java.util.List;


public class DaxemSanphamFragment extends Fragment {
    RecyclerView recyclerViewProductAllSanPham;
    Product_Dashboard_sanPham_Adapter product_sanPham_dashboard_adapter;
    List<DashboardSanPham> list;


    MyDatabase myDatabase;
    DashboardSanPham dashboardSanPham;
    Cursor cursor;
    static final String DB_NAME = "db_shop";
    static final String TABLE_NAME = "tbl_seen";
    static final String ID_FIELD = "id";
    static final String NAME_FIELD = "name";
    static final String PRICE_FIELD = "price";
    static final String AVATAR_FIELD = "avatar";
    static final String DESCRIPTION_FIELD = "description";
    static final String CATEGORYID_FIELD = "categoryid";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_daxem_sanpham, container, false);
        initDatabase();
        list = new ArrayList<>();
        recyclerViewProductAllSanPham = view.findViewById(R.id.recyclerViewProductAllSanPham);
        product_sanPham_dashboard_adapter = new Product_Dashboard_sanPham_Adapter(this.getContext(),R.layout.line_sanpham_dashboardsanpham,list);
        recyclerViewProductAllSanPham.setHasFixedSize(true);
        recyclerViewProductAllSanPham.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewProductAllSanPham.setAdapter(product_sanPham_dashboard_adapter);
        product_sanPham_dashboard_adapter.notifyDataSetChanged();

        myDatabase = new MyDatabase(getContext(), DB_NAME,null, 1);
        try {
            cursor = myDatabase.selectData("select * from "+TABLE_NAME+" ORDER BY id DESC");
            while (cursor.moveToNext()){
                DashboardSanPham productSeen = new DashboardSanPham();

                productSeen.setId(cursor.getInt(0));
                productSeen.setName(cursor.getString(1));
                productSeen.setPrice(cursor.getInt(2));
                productSeen.setAvatar(cursor.getString(3));
                productSeen.setDescription(cursor.getString(4));
                productSeen.setCategoryid(cursor.getInt(5));

                list.add(productSeen);
            }
//            Log.d("seen",cursor.toString());
            Toast.makeText(getContext(), "Hế lô"+cursor.getString(1), Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(getContext(), "bạn chưa xem sản phẩm nào!", Toast.LENGTH_SHORT).show();
        }


        return view;
    }
    private void initDatabase() {
        //gọi database
        myDatabase = new MyDatabase(getContext(), DB_NAME,null, 1);
    }

}