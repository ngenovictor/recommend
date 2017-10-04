package com.herokuapp.veekay.recommend.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.herokuapp.veekay.recommend.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsTabFragment extends Fragment {


    public NotificationsTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications_tab, container, false);
    }

}
