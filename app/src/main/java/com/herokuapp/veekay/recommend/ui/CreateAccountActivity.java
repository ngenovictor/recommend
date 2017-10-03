package com.herokuapp.veekay.recommend.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.herokuapp.veekay.recommend.R;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView alreadyHaveAccountText;
    private EditText newFirstName;
    private EditText newSecondName;
    private EditText newUserName;
    private EditText newEmail;
    private EditText newPassword;
    private EditText confirmNewPassword;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        
        // initialize all ui elements
        alreadyHaveAccountText = findViewById(R.id.alreadyHaveAccountText);
        newFirstName = findViewById(R.id.newFirstName);
        newSecondName = findViewById(R.id.newSecondName);
        newUserName = findViewById(R.id.newUserName);
        newEmail = findViewById(R.id.newEmail);
        newPassword = findViewById(R.id.newPassword);
        confirmNewPassword = findViewById(R.id.confirmNewPassword);
        createAccountButton = findViewById(R.id.createAccountButton);

        //setOnclickListeners
        alreadyHaveAccountText.setOnClickListener(this);
        createAccountButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == alreadyHaveAccountText){
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
