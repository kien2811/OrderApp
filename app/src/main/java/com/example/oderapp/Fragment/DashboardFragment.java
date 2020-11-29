package com.example.oderapp.Fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oderapp.Activity.MyDatabase;
import com.example.oderapp.Adapter.ViewMenuSanPhamAdapter;
import com.example.oderapp.Model.DashboardSanPham;
import com.example.oderapp.R;
import com.google.android.material.tabs.TabLayout;


public class DashboardFragment extends Fragment {
    private TabLayout SanPhamtableLayout;
    private ViewPager view_page_dashboard;

    public DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);

        SanPhamtableLayout = view.findViewById(R.id.SanPhamtabLayout);
        view_page_dashboard = view.findViewById(R.id.view_page_dashboard);

        ViewMenuSanPhamAdapter viewMenuSanPhamAdapter = new ViewMenuSanPhamAdapter(getParentFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        view_page_dashboard.setAdapter(viewMenuSanPhamAdapter);
        SanPhamtableLayout.setupWithViewPager(view_page_dashboard);
        viewMenuSanPhamAdapter.notifyDataSetChanged();




        return view;
    }

}