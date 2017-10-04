package com.herokuapp.veekay.recommend.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.herokuapp.veekay.recommend.ui.HomeTabFragment;

/**
 * Created by victor on 10/4/17.
 */

public class HomeTabsPagerAdapter extends FragmentPagerAdapter {
    public HomeTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
//        switch (position){
//            case 0:
//                return HomeTabFragment.newInstance();
//        }
        return HomeTabFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
