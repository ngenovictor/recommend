package com.herokuapp.veekay.recommend.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.herokuapp.veekay.recommend.R;
import com.herokuapp.veekay.recommend.models.Question;

/**
 * Created by victor on 10/4/17.
 */

public class QuestionListViewHolder extends RecyclerView.ViewHolder {
    private TextView questionTextView;
    private View mView;
    public QuestionListViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        Context context = mView.getContext();
        questionTextView = mView.findViewById(R.id.questionTextView);
    }
    public void bindQuestion(Question question){
        questionTextView.setText(question.getOwnerId());

    }
}
