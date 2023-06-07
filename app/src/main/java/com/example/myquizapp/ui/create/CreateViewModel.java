package com.example.myquizapp.ui.create;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizapp.R;

import java.util.List;

public class CreateViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public static class questionAdapter extends RecyclerView.Adapter<QuestionViewHolder> {
        Context context;
        List<CreateQuizViewModel.Question> questions;

        public questionAdapter(Context context, List<CreateQuizViewModel.Question> questions) {
            this.context = context;
            this.questions = questions;
        }

        @NonNull
        @Override
        public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new QuestionViewHolder(LayoutInflater.from(context).inflate(R.layout.question_view,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
            String num= String.valueOf(questions.get(position).getQuestionNumber());
            holder.questionNumber.setText(num);
            holder.questionName.setText(questions.get(position).getQuestionName());
            holder.option1.setText(questions.get(position).getOption1());
            holder.option2.setText(questions.get(position).getOption2());
            holder.option3.setText(questions.get(position).getOption3());
            holder.answerName.setText(questions.get(position).getAnswer());
        }

        @Override
        public int getItemCount() {
            return questions.size();
        }
    }
}