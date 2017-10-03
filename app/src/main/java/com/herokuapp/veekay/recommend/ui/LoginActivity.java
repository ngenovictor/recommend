package com.herokuapp.veekay.recommend.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.herokuapp.veekay.recommend.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView notYetRegisteredText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        notYetRegisteredText = findViewById(R.id.notYetRegisteredText);
        notYetRegisteredText.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == notYetRegisteredText){
            Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        }
    }
}
