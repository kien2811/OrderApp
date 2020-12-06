package com.example.oderapp.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.oderapp.Fragment.DonMuaFragment.ChoXacNhanFragment;
import com.example.oderapp.Fragment.DonMuaFragment.DaHuyFragment;
import com.example.oderapp.Fragment.DonMuaFragment.DaMuaFragment;
import com.example.oderapp.Fragment.DonMuaFragment.DangGiaoFragment;
import com.example.oderapp.Fragment.SanPhamFragment.AllSanPhamFragment;
import com.example.oderapp.Fragment.SanPhamFragment.DaxemSanphamFragment;
import com.example.oderapp.Fragment.SanPhamFragment.SaleSanPhamFragment;
import com.example.oderapp.Fragment.SanPhamFragment.SanphamHotFragment;

public class ViewMenuDonMuaAdapter extends FragmentStatePagerAdapter {
    public ViewMenuDonMuaAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ChoXacNhanFragment();
            case 1:
                return new DangGiaoFragment();
            case 2:
                return new DaMuaFragment();
            case 3:
                return new DaHuyFragment();
            default:
                return new ChoXacNhanFragment();
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
                title = "Chờ xác nhận";
                break;
            case 1:
                title = "Đang giao";
                break;
            case 2:
                title = "Đã Mua";
                break;
            case 3:
                title = "Đã hủy";
                break;


        }
        return title;
    }
}
