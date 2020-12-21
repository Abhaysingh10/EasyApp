package com.example.easy;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearSmoothScroller;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends FragmentPagerAdapter {
    @NonNull

    private final List<Fragment> lstFragment = new ArrayList<>();
    private final List<String>  lststring = new ArrayList<>();
    public  ViewPageAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }


    public Fragment getItem(int position) {
        return lstFragment.get(position);
    }

    @Override
    public int getCount() {
        return lstFragment.size();
    }

    public void addFragment(Fragment fragment){
        lstFragment.add(fragment);
    }

}
