package com.herokuapp.veekay.recommend.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import com.herokuapp.veekay.recommend.R;
import com.herokuapp.veekay.recommend.models.Question;

import java.util.ArrayList;


public class DisplayQuestionsAdapter extends RecyclerView.Adapter<DisplayQuestionsAdapter.QuestionViewHolder>{
    private Context mContext;
    private ArrayList<Question> questions;

    public DisplayQuestionsAdapter(Context context, ArrayList<Question> questions){
        mContext = context;
        this.questions = questions;

        Log.d("Questions", Integer.toString(questions.size()));
    }

    @Override
    public DisplayQuestionsAdapter.QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.question_list_item, parent, false);
        QuestionViewHolder questionViewHolder = new QuestionViewHolder(view);
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

        public QuestionViewHolder(View itemView) {
            super(itemView);
            mQuestion = itemView.findViewById(R.id.questionTextView);

        }
        public void bindQuestion(Question question, int position){
            mQuestion.setText(question.getQuestion());
        }
    }
}
