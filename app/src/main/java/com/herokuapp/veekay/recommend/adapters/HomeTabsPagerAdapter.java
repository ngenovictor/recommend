package com.herokuapp.veekay.recommend.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.herokuapp.veekay.recommend.models.User;
import com.herokuapp.veekay.recommend.ui.HomeTabFragment;
import com.herokuapp.veekay.recommend.ui.InboxTabFragment;
import com.herokuapp.veekay.recommend.ui.NotificationsTabFragment;
import com.herokuapp.veekay.recommend.ui.SearchTabFragment;

/**
 * Created by victor on 10/4/17.
 */

public class HomeTabsPagerAdapter extends FragmentPagerAdapter {
    private User user;
    public HomeTabsPagerAdapter(FragmentManager fm, User loggedInUser) {
        super(fm);
        user = loggedInUser;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return HomeTabFragment.newInstance(user);
            case 1:
                return NotificationsTabFragment.newInstance(user);
            case 2:
                return SearchTabFragment.newInstance(user);
            case 3:
                return InboxTabFragment.newInstance(user);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
