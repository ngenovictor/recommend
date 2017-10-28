package com.herokuapp.veekay.recommend.ui;


import android.app.ActionBar;
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
public class InboxTabFragment extends Fragment {
    private static User user;


    public static InboxTabFragment newInstance(User loggedInUser) {
        user = loggedInUser;
        return new InboxTabFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_inbox_tab, container, false);
        return view;
    }

}
