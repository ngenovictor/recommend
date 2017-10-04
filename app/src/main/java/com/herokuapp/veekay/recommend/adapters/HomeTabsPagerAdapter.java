package com.herokuapp.veekay.recommend.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.herokuapp.veekay.recommend.ui.HomeTabFragment;
import com.herokuapp.veekay.recommend.ui.InboxTabFragment;
import com.herokuapp.veekay.recommend.ui.NotificationsTabFragment;
import com.herokuapp.veekay.recommend.ui.SearchTabFragment;

/**
 * Created by victor on 10/4/17.
 */

public class HomeTabsPagerAdapter extends FragmentPagerAdapter {
    public HomeTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return HomeTabFragment.newInstance();
            case 1:
                return NotificationsTabFragment.newInstance();
            case 2:
                return SearchTabFragment.newInstance();
            case 3:
                return InboxTabFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
