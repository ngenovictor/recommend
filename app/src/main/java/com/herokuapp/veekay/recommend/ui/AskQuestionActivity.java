package com.herokuapp.veekay.recommend.ui;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.herokuapp.veekay.recommend.models.Question;
import com.herokuapp.veekay.recommend.models.User;
import com.herokuapp.veekay.recommend.utils.Constants;

public class AskQuestionActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText questionEditText;
    private Button submitQuestionButton;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        createAuthListener();
        questionEditText = findViewById(R.id.questionEditText);
        submitQuestionButton = findViewById(R.id.submitQuestionButton);
        submitQuestionButton.setOnClickListener(this);

        getSupportActionBar().setTitle("Ask for Recommendation");


    }

    @Override
    public void onClick(View view) {
        if(view == submitQuestionButton){
            String question = questionEditText.getText().toString().trim();
            if (question.length()<1){
                questionEditText.setError("Cannot send empty question.");
            }else if(question.length()<10){
                questionEditText.setError("Too short to form an enquiry");
            }else{
                final Question newQuestion = new Question(question);
                String userId = currentUser.getUid();
                DatabaseReference usersRef = FirebaseDatabase.getInstance()
                        .getReference(Constants.USERS_DB_KEY);
                Query query = usersRef.orderByChild("userId").equalTo(userId);
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        User user = dataSnapshot.getValue(User.class);
                        newQuestion.setOwnerId(user.getPushId());
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                                .getReference(Constants.QUESTIONS_DB_KEY);
                        DatabaseReference pushRef = databaseReference.push();
                        String pushId = pushRef.getKey();
                        newQuestion.setPushId(pushId);

                        pushRef.setValue(newQuestion).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    finish();
                                }
                            }
                        });
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
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }
    public void createAuthListener(){

    }
}
