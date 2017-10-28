package com.herokuapp.veekay.recommend.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.herokuapp.veekay.recommend.R;
import com.herokuapp.veekay.recommend.models.Question;
import com.herokuapp.veekay.recommend.models.Recommendation;
import com.herokuapp.veekay.recommend.models.User;
import com.herokuapp.veekay.recommend.utils.Constants;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by victor on 10/9/17.
 */

public class DisplayRecommendationsAdapter extends RecyclerView.Adapter<DisplayRecommendationsAdapter.RecommendationViewHolder> {
    private Context mContext;
    private Question mQuestion;
    private ArrayList<Recommendation> recommendations;
    public DisplayRecommendationsAdapter(Context context, Question question, ArrayList<Recommendation> recommendations){
        mContext = context;
        mQuestion = question;
        this.recommendations = recommendations;
    }
    @Override
    public DisplayRecommendationsAdapter.RecommendationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recommendations_list_item, parent, false);
        DisplayRecommendationsAdapter.RecommendationViewHolder viewHolder = new DisplayRecommendationsAdapter.RecommendationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecommendationViewHolder holder, int position) {
        holder.bindRecommendation(recommendations.get(position), position);
    }

    @Override
    public int getItemCount() {
        return recommendations.size();
    }
    public class RecommendationViewHolder extends RecyclerView.ViewHolder {
        private TextView rOwnerName;
        private TextView rOwnerUserName;
        private TextView recommendationTextView;
        private TextView numberOfThumbsUp;
        private TextView numberOfThumbsDown;
        private ImageView rThumbsUp;
        private ImageView rThumbsDown;

        public RecommendationViewHolder(View itemView) {
            super(itemView);
            rOwnerName = itemView.findViewById(R.id.rOwnerName);
            rOwnerUserName = itemView.findViewById(R.id.rOwnerUserName);
            recommendationTextView = itemView.findViewById(R.id.recommendationTextView);
            numberOfThumbsUp = itemView.findViewById(R.id.numberOfThumbsUp);
            numberOfThumbsDown = itemView.findViewById(R.id.numberOfThumbsDown);
            rThumbsUp = itemView.findViewById(R.id.rThumbsUp);
            rThumbsDown = itemView.findViewById(R.id.rThumbsDown);

        }
        public void bindRecommendation(final Recommendation recommendation, int position){
            recommendationTextView.setText(recommendation.getRecommendation());
            numberOfThumbsUp.setText(Integer.toString(recommendation.getThumbsUp()));
            numberOfThumbsDown.setText(Integer.toString(recommendation.getThumbsDown()));
            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.USERS_DB_KEY);
            final Query query = reference.orderByChild("pushId").equalTo(recommendation.getUserPushId());
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    final User recommedationOwner = dataSnapshot.getValue(User.class);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            rOwnerName.setText(recommedationOwner.getFullName());
                            rOwnerUserName.setText("@"+recommedationOwner.getUserName());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

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
//            rThumbsUp.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    recommendation.addLike();
//                    query.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            query.getRef().setValue(recommendation);
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//
//                }
//            });
        }
    }
}
