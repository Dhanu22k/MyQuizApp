package com.example.myquizapp.ui.create;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizapp.R;

public class QuestionViewHolder extends RecyclerView.ViewHolder {
    TextView questionNumber,questionName,answerName,option1,option2,option3;
    public QuestionViewHolder(@NonNull View itemView) {
        super(itemView);
        questionNumber=itemView.findViewById(R.id.questionNumberTextView);
        questionName=itemView.findViewById(R.id.questionNameTextView);
        answerName=itemView.findViewById(R.id.answerNameTextView);
        option1=itemView.findViewById(R.id.option1TextView);
        option2=itemView.findViewById(R.id.option2TextView);
        option3=itemView.findViewById(R.id.option3TextView);
    }
}
