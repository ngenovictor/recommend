package com.herokuapp.veekay.recommend.ui;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.herokuapp.veekay.recommend.R;

public class AskQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        getSupportActionBar().setTitle("Ask for Recommendation");
    }
}
