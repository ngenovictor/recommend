package com.herokuapp.veekay.recommend.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.herokuapp.veekay.recommend.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView notYetRegisteredText;
    private Button email_sign_in_button;
    private EditText loginEmail;
    private EditText loginPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        notYetRegisteredText = findViewById(R.id.notYetRegisteredText);
        email_sign_in_button = findViewById(R.id.email_sign_in_button);
        loginPassword = findViewById(R.id.loginPassword);
        loginEmail = findViewById(R.id.loginEmail);
        notYetRegisteredText.setOnClickListener(this);
        email_sign_in_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == notYetRegisteredText){
            Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        }else if(view == email_sign_in_button){
            mAuth = FirebaseAuth.getInstance();
            String emailAddress = loginEmail.getText().toString().trim();
            String password = loginPassword.getText().toString().trim();
            mAuth.signInWithEmailAndPassword(emailAddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }
}
