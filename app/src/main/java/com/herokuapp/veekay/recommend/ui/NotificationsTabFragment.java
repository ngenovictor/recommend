package com.herokuapp.veekay.recommend.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.herokuapp.veekay.recommend.R;
import com.herokuapp.veekay.recommend.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsTabFragment extends Fragment {
    private static User user;


    public static NotificationsTabFragment newInstance(User loggedInUser) {
        user = loggedInUser;
        return new NotificationsTabFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications_tab, container, false);
        return view;
    }

}
