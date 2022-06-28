package com.example.linh.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.linh.fragment.FragmentContact;
import com.example.linh.fragment.FragmentLove;
import com.example.linh.fragment.FragmentLast;


public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new FragmentLove();
            case 2:
                return new FragmentLast();
            case 0:
                return new FragmentContact();
            default:
                return new FragmentContact();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
