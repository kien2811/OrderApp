package com.example.oderapp.Adapter;

import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.oderapp.Fragment.SanPhamFragment.AllSanPhamFragment;
import com.example.oderapp.Fragment.SanPhamFragment.DaxemSanphamFragment;
import com.example.oderapp.Fragment.SanPhamFragment.SaleSanPhamFragment;
import com.example.oderapp.Fragment.SanPhamFragment.SanphamHotFragment;

public class ViewMenuSanPhamAdapter extends FragmentStatePagerAdapter {
    public ViewMenuSanPhamAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AllSanPhamFragment();
            case 1:
                return new SaleSanPhamFragment();
            case 2:
                return new SanphamHotFragment();
            case 3:
                return new DaxemSanphamFragment();
            default:
                return new AllSanPhamFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title  = "";
        switch (position){
            case 0:
                title = "Tất Cả";
                break;
            case 1:
                title = "Giảm giá";
                break;
            case 2:
                title = "Hot";
                break;
            case 3:
                title = "Đã xem";
                break;


        }
        return title;
    }
}
