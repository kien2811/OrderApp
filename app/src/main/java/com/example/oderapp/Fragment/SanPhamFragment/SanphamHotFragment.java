package com.example.oderapp.Fragment.SanPhamFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oderapp.Adapter.Product_Dashboard_sanPham_Adapter;
import com.example.oderapp.Model.DashboardSanPham;
import com.example.oderapp.R;

import java.util.ArrayList;
import java.util.List;


public class SanphamHotFragment extends Fragment {

    RecyclerView recyclerViewProductAllSanPham;
    Product_Dashboard_sanPham_Adapter product_sanPham_dashboard_adapter;
    List<DashboardSanPham> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sanpham_hot, container, false);

        list = new ArrayList<>();
        recyclerViewProductAllSanPham = view.findViewById(R.id.recyclerViewProductAllSanPham);
        product_sanPham_dashboard_adapter = new Product_Dashboard_sanPham_Adapter(this.getContext(),R.layout.line_sanpham_dashboardsanpham,list);
        recyclerViewProductAllSanPham.setHasFixedSize(true);
        recyclerViewProductAllSanPham.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewProductAllSanPham.setAdapter(product_sanPham_dashboard_adapter);

        product_sanPham_dashboard_adapter.notifyDataSetChanged();
        for (int i = 0 ;i < 10 ; i ++){
            list.add(new DashboardSanPham(1,"Banh Ngon gì gì gì đâu á",2500,"https://image.flaticon.com/icons/png/128/2971/2971975.png","haha",1,1));
        }

        return view;
    }
}