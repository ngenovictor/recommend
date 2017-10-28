package com.herokuapp.veekay.recommend.adapters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.herokuapp.veekay.recommend.R;
import com.herokuapp.veekay.recommend.models.Question;
import com.herokuapp.veekay.recommend.models.User;
import com.herokuapp.veekay.recommend.ui.GiveRecommendationsActivity;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class DisplayQuestionsAdapter extends RecyclerView.Adapter<DisplayQuestionsAdapter.QuestionViewHolder>{
    private Context mContext;
    private ArrayList<Question> questions;
    private User currentUser;


    public DisplayQuestionsAdapter(Context context, ArrayList<Question> questions, User loggedInUser){
        mContext = context;
        this.questions = questions;
        currentUser = loggedInUser;

        Log.d("Questions", Integer.toString(questions.size()));
    }

    @Override
    public DisplayQuestionsAdapter.QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.question_list_item, parent, false);
        QuestionViewHolder questionViewHolder = new QuestionViewHolder(view, currentUser);
        return questionViewHolder;
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        holder.bindQuestion(questions.get(position), position);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder{
        private TextView mQuestion;
        private ImageView qAddComment;
        private User currentUser;
        private TextView qOwnerName;
        private TextView qOwnerUserName;
        private TextView numberOfComments;
        private TextView numberOfShares;

        public QuestionViewHolder(View itemView, User loggedInUser) {
            super(itemView);
            mQuestion = itemView.findViewById(R.id.questionTextView);
            qAddComment = itemView.findViewById(R.id.qAddComment);
            currentUser = loggedInUser;
            qOwnerName = itemView.findViewById(R.id.qOwnerName);
            qOwnerUserName = itemView.findViewById(R.id.qOwnerUserName);
            numberOfComments = itemView.findViewById(R.id.numberOfComments);
            numberOfShares = itemView.findViewById(R.id.numberOfShares);

        }
        public void bindQuestion(final Question question, int position){
            mQuestion.setText(question.getQuestion());
            qOwnerName.setText(currentUser.getFullName());
            qOwnerUserName.setText("@"+currentUser.getUserName());
            if (question.getComments()>0){
                numberOfComments.setText(Integer.toString(question.getComments()));
            }
            if (question.getShares()>0){
                numberOfShares.setText(Integer.toString(question.getComments()));
            }
            qAddComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, GiveRecommendationsActivity.class);
                    intent.putExtra("question", Parcels.wrap(question));
                    intent.putExtra("user", Parcels.wrap(currentUser));
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
