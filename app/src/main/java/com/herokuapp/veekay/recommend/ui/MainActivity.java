package com.herokuapp.veekay.recommend.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.herokuapp.veekay.recommend.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
