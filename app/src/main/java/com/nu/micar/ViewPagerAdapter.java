package com.nu.micar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0) {
            return new MiCarTrackFragment();
        }
        else if (position == 1) {
            return new MiCarLockFragment();
        }else if (position == 2) {
            return new MiCarLocateFragment();
        } else return new MiCarCarsFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
