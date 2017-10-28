package com.herokuapp.veekay.recommend.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.herokuapp.veekay.recommend.R;
import com.herokuapp.veekay.recommend.adapters.DisplayQuestionsAdapter;
import com.herokuapp.veekay.recommend.adapters.QuestionListViewHolder;
import com.herokuapp.veekay.recommend.models.Question;
import com.herokuapp.veekay.recommend.models.User;
import com.herokuapp.veekay.recommend.utils.Constants;

import java.util.ArrayList;

public class HomeTabFragment extends Fragment implements View.OnClickListener{
    private FloatingActionButton fab;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Context mContext;
    private RecyclerView homeQuestionsRecyclerView;
    private DisplayQuestionsAdapter displayQuestionsAdapter;
    private ArrayList<Question> questions = new ArrayList<>();
    private static User user;


    public static HomeTabFragment newInstance(User loggedInUser) {
        HomeTabFragment homeTabFragment = new HomeTabFragment();
        user = loggedInUser;
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
        retrieveQuestions();

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
    public void retrieveQuestions(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference(Constants.QUESTIONS_DB_KEY);
//        Query getHomeDisplayQuestions = databaseReference.orderByKey();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Question questionFromDb = dataSnapshot.getValue(Question.class);
                Log.d("questions", questionFromDb.getQuestion());
                questions.add(questionFromDb);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("questions", Integer.toString(questions.size()));
                displayQuestionsAdapter = new DisplayQuestionsAdapter(getContext(), questions, user);
                homeQuestionsRecyclerView.setHasFixedSize(true);
                homeQuestionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                homeQuestionsRecyclerView.setAdapter(displayQuestionsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
