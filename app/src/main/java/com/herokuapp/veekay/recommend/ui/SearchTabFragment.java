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
public class SearchTabFragment extends Fragment {


    public static SearchTabFragment newInstance() {
        return new SearchTabFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_tab, container, false);
        return view;
    }

}
