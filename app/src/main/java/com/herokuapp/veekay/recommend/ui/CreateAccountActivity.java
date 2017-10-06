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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.herokuapp.veekay.recommend.R;
import com.herokuapp.veekay.recommend.models.User;
import com.herokuapp.veekay.recommend.utils.Constants;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView alreadyHaveAccountText;
    private EditText newFirstName;
    private EditText newSecondName;
    private EditText newUserName;
    private EditText newEmail;
    private EditText newPassword;
    private EditText confirmNewPassword;
    private Button createAccountButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        
        // initialize all ui elements
        alreadyHaveAccountText = findViewById(R.id.alreadyHaveAccountText);
        //new account details
        newFirstName = findViewById(R.id.newFirstName);
        newSecondName = findViewById(R.id.newSecondName);
        newUserName = findViewById(R.id.newUserName);
        newEmail = findViewById(R.id.newEmail);
        newPassword = findViewById(R.id.newPassword);
        confirmNewPassword = findViewById(R.id.confirmNewPassword);
        //submit button
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
        }else if(view == createAccountButton){
            String firstName = newFirstName.getText().toString().trim();
            String secondName = newSecondName.getText().toString().trim();
            String username = newUserName.getText().toString().trim();
            String email = newEmail.getText().toString().trim();
            String password = newPassword.getText().toString().trim();
            String confrimPassword = confirmNewPassword.getText().toString().trim();
            boolean validCredentials = runCredentialsValidity(firstName, secondName, email, password, confrimPassword);
            if(validCredentials){
                validateUserNameAndCreateAccount(firstName, secondName, email, username, password);
            };
        }
    }
    public boolean runCredentialsValidity(String firstName, String secondName, String email, String password, String confrimPassword){
        if(firstName.length()<2){
            newFirstName.setError("Name cannot be one character long");
            newFirstName.requestFocus();
            return false;
        }else if(secondName.length()<2){
            newSecondName.setError("Name cannot be one character long");
            newSecondName.requestFocus();
            return false;
        }else if(!email.contains("@")) {
            newEmail.setError("Enter valid email");
            newEmail.requestFocus();
            return false;
        }else if(password.length()<6){
            newPassword.setError("Password must not less than 6 characters");
            newPassword.requestFocus();
            return false;
        }else if(!password.equals(confrimPassword)){
            confirmNewPassword.setError("Passwords don't match");
            confirmNewPassword.requestFocus();
            return false;
        }
        return true;
    }
    public void validateUserNameAndCreateAccount(final String firstName, final String secondName, final String email, final String username, final String password){
        DatabaseReference myRef = mDatabase.child(Constants.USERS_DB_KEY);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if (snapshot.child("userName").getValue().toString().equals(username)){
                        newUserName.setError("Username already taken");
                        newUserName.requestFocus();
                        return;
                    }
                }
                createUser(firstName,secondName,email,username,password);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Checks", databaseError.toString());
            }
        });
    }
    public void createUser(final String firstName, final String secondName, String email, final String username, String password){

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){


                    FirebaseUser createdUser = FirebaseAuth.getInstance().getCurrentUser();

                    User newUser = new User(firstName, secondName, username);
                    newUser.setUserId(createdUser.getUid());

                    DatabaseReference myRef = mDatabase.child(Constants.USERS_DB_KEY);

                    DatabaseReference pushRef = myRef.push();

                    String pushId = pushRef.getKey();

                    newUser.setPushId(pushId);

                    pushRef.setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }

            }
        });
    }
}
