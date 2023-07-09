package com.example.myquizapp.ui.home.quizPlay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myquizapp.R;

public class quizStartPage extends AppCompatActivity {
TextView quiznameTv,totalQuestionsTv;
Button startBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start_page);
        Bundle bundle = getIntent().getExtras();
        String quizname = bundle.getString("quizname");
        String totalQuestions=bundle.getString("totalquestion");
        String userid=bundle.getString("userid");
        String quizid=bundle.getString("quizid");

        quiznameTv=findViewById(R.id.quizStartPageQuizName);
        totalQuestionsTv=findViewById(R.id.quizStartPageTotalQuestions);
        startBtn=findViewById(R.id.quizStartBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("userid",userid);
                bundle.putString("quizid",quizid);
                bundle.putString("totalquestion",totalQuestions);
                bundle.putString("quizname",quizname);
                Intent intent=new Intent(quizStartPage.this,QuizActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        quiznameTv.setText(quizname);
        totalQuestionsTv.setText(totalQuestions);


    }
}