package com.herokuapp.veekay.recommend.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.herokuapp.veekay.recommend.R;

public class HomeTabFragment extends Fragment implements View.OnClickListener{
    private FloatingActionButton fab;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Context mContext;
    private RecyclerView homeQuestionsRecyclerView;

    public static HomeTabFragment newInstance() {
        HomeTabFragment homeTabFragment = new HomeTabFragment();
        return homeTabFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeQuestionsRecyclerView = view.findViewById(R.id.homeQuestionsRecyclerView);
        fab = view.findViewById(R.id.addNewButton);
        fab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mContext = getContext();
        fab.setOnClickListener(this);

        homeQuestionsRecyclerView.setAdapter();
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == fab){
            if(currentUser==null){
                CharSequence text = "You need to login first!!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(mContext, text, duration);
                toast.show();
            }else{
                Intent intent = new Intent(getActivity(), AskQuestionActivity.class);
                startActivity(intent);
            }
        }
    }
}
