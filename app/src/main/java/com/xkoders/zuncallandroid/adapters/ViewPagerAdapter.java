package com.xkoders.zuncallandroid.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xkoders.zuncallandroid.fragments.FragmentHelp;


public class ViewPagerAdapter extends FragmentStatePagerAdapter{
    int numberOfViews; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        numberOfViews = 5;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentHelp.newInstance(position);
    }

    @Override
    public int getCount() {
        return numberOfViews;
    }
}
