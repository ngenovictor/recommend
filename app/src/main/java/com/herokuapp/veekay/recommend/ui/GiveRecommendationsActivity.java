package com.herokuapp.veekay.recommend.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import com.herokuapp.veekay.recommend.adapters.DisplayRecommendationsAdapter;
import com.herokuapp.veekay.recommend.models.Question;
import com.herokuapp.veekay.recommend.models.Recommendation;
import com.herokuapp.veekay.recommend.models.User;
import com.herokuapp.veekay.recommend.utils.Constants;

import org.parceler.Parcels;

import java.util.ArrayList;


public class GiveRecommendationsActivity extends AppCompatActivity implements View.OnClickListener {
    private Question question;
    private TextView questionToRecommend;
    private EditText addRecommendation;
    private Button submitRecommendation;
    private RecyclerView displayRecommendations;
    private User user;
    private ArrayList<Recommendation> recommendations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_recommendations);
        Intent intent = getIntent();

        Parcelable wrappedQuestion = intent.getParcelableExtra("question");
        question = Parcels.unwrap(wrappedQuestion);

        Parcelable wrappedUser = intent.getParcelableExtra("user");
        user = Parcels.unwrap(wrappedUser);

        questionToRecommend = findViewById(R.id.questionToRecommend);
        questionToRecommend.setText(question.getQuestion());
        addRecommendation = findViewById(R.id.addRecommendation);
        submitRecommendation = findViewById(R.id.submitRecommendation);
        submitRecommendation.setOnClickListener(this);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.RECOMMENDATIONS_DB_KEY);
        Query query = reference.orderByChild("questionPushId").equalTo(question.getPushId());
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                recommendations.add(dataSnapshot.getValue(Recommendation.class));
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
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                displayRecommendations = findViewById(R.id.displayRecommendations);
                DisplayRecommendationsAdapter adapter = new DisplayRecommendationsAdapter(getApplicationContext(), question, recommendations);
                displayRecommendations.setHasFixedSize(true);
                displayRecommendations.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                displayRecommendations.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == submitRecommendation){
            String recommendationText = addRecommendation.getText().toString().trim();
            if (recommendationText.length()<5){
                addRecommendation.setError("Too short to be a valid response");
            }else{
                DatabaseReference recommendationRef = FirebaseDatabase.getInstance().getReference(Constants.RECOMMENDATIONS_DB_KEY);
                Recommendation newRecommendation = new Recommendation(recommendationText);
                newRecommendation.setUserPushId(user.getPushId());
                DatabaseReference pushRef = recommendationRef.push();
                String pushId = pushRef.getKey();
                newRecommendation.setPushId(pushId);
                newRecommendation.setQuestionPushId(question.getPushId());
                pushRef.setValue(newRecommendation);

                addRecommendation.setText("");
                // increase number of comments
                DatabaseReference questionRef = FirebaseDatabase.getInstance().getReference(Constants.QUESTIONS_DB_KEY);
                questionRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Question dbQuestion = dataSnapshot.getValue(Question.class);
                        if (dbQuestion.getPushId().equals(question.getPushId())){
                            int comments = dbQuestion.getComments()+1;
                            dbQuestion.setComments(comments);
                            DatabaseReference thisQuestionRef = dataSnapshot.getRef();
                            thisQuestionRef.setValue(dbQuestion);
                            return;
                        }

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
            }
        }
    }
}
