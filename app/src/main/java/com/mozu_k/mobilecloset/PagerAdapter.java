package com.mozu_k.mobilecloset;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 8;
    private String[] categories = {"トップス","ボトムス","ワンピース","ジャケット","インナー","靴","バッグ","小物・アクセサリー"};

    public PagerAdapter(FragmentManager fm) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        CategoryFragment fragment = new CategoryFragment();
        fragment.setFragmentNumber(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public String getPageTitle(int position){
        return categories[position];
    }
}
