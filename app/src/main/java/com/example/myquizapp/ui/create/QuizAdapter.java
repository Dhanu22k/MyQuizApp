package com.example.myquizapp.ui.create;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizapp.MainActivity;
import com.example.myquizapp.R;
import com.example.myquizapp.ui.profile.ProfileFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.MyViewHolder> {
    Context context;

    public QuizAdapter(Context context, ArrayList<Quiz> list) {
        this.context = context;
        this.list = list;
    }

    ArrayList<Quiz> list;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.quiz_view,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Quiz quiz=list.get(position);
        holder.quizName.setText(quiz.getQuizName());
        holder.date.setText(quiz.getDate());
        holder.totalQuestion.setText(String.valueOf(quiz.questionCount));

        holder.deleteQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert=new AlertDialog.Builder(holder.itemView.getContext());
                alert.setTitle("Delete Panel");
                alert.setMessage("Delete.....?");

                alert.setPositiveButton("Yes",((dialog, which) -> {
                    FirebaseDatabase.getInstance().getReference().child("users").child(MainActivity.GlobalUserId).child("quiz")
                                    .child(quiz.getQuizId()).removeValue();
                    Toast.makeText(context," Quiz Deleted",Toast.LENGTH_SHORT).show();
                }));
                alert.setNegativeButton("No",(dialog, which) -> {

                });

                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView quizName,date,totalQuestion;
        Button deleteQuiz;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            quizName=itemView.findViewById(R.id.quizViewQuizNmae);
            date=itemView.findViewById(R.id.date);
            deleteQuiz=itemView.findViewById(R.id.deleteQuizBtn);
            totalQuestion=itemView.findViewById(R.id.totalQuestion);
        }
    }

}

