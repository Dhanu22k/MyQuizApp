package com.example.myquizapp.ui.home.quizPlay;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myquizapp.R;
import com.example.myquizapp.databinding.ActivityMainBinding;
import com.example.myquizapp.ui.create.CreateFragment;
import com.example.myquizapp.ui.create.Quiz;
import com.example.myquizapp.ui.create.QuizAdapter;
import com.example.myquizapp.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class QuizActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
   private DatabaseReference databaseReference;
    private ArrayList<QuizModal> list;
private TextView questionTv,questionNumberTv;
private Button option1Btn,option2Btn,option3Btn,option4Btn;
private ArrayList<QuizModal> quizModalArrayList;
int currentScore=0,questionAttempted=1,currentPos;
String userid,quizid,quizname,totalquestion;
int pos=0;
int randomInt[];
    List<Integer> numbers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        questionTv=findViewById(R.id.quizQuestion);
        questionNumberTv=findViewById(R.id.quizPageQuestionAttempted);
        Bundle bundle = getIntent().getExtras();
        quizid = bundle.getString("quizid");
        userid = bundle.getString("userid");
        quizname = bundle.getString("quizname");
        totalquestion = bundle.getString("totalquestion");
        option1Btn=findViewById(R.id.option1Btn);
        option2Btn=findViewById(R.id.option2Btn);
        option3Btn=findViewById(R.id.option3Btn);
        option4Btn=findViewById(R.id.option4Btn);

        currentPos=0;
        pos=0;
        quizModalArrayList=new ArrayList<>();
        getQuizQuestion(quizModalArrayList);

        option1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quizModalArrayList.get(currentPos).getAnwser().trim().toLowerCase().equals(option1Btn.getText().toString().toLowerCase())) {
                    option1Btn.setBackgroundColor(Color.parseColor("#1afc05"));
                    currentScore++;
                }else{
                    option1Btn.setBackgroundColor(Color.parseColor("#e31414"));
                }

                questionAttempted++;
                currentPos=currentPos+1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setDataToView(currentPos);
                    }
                }, 1000);
            }

        });

        option2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quizModalArrayList.get(currentPos).getAnwser().trim().toLowerCase().equals(option2Btn.getText().toString().toLowerCase())) {
                    option2Btn.setBackgroundColor(Color.parseColor("#1afc05"));
                    currentScore++;
                }else{
                    option2Btn.setBackgroundColor(Color.parseColor("#e31414"));
                }

                questionAttempted++;
                currentPos=currentPos+1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setDataToView(currentPos);
                    }
                }, 1000);
            }

        });

        option3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quizModalArrayList.get(currentPos).getAnwser().trim().toLowerCase().equals(option3Btn.getText().toString().toLowerCase())) {
                    option3Btn.setBackgroundColor(Color.parseColor("#1afc05"));
                    currentScore++;
                }else{
                    option3Btn.setBackgroundColor(Color.parseColor("#e31414"));
                }

                questionAttempted++;
                currentPos=currentPos+1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setDataToView(currentPos);
                    }
                }, 1000);
            }

        });
        option4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quizModalArrayList.get(currentPos).getAnwser().trim().toLowerCase().equals(option4Btn.getText().toString())) {
                    option4Btn.setBackgroundColor(Color.parseColor("#1afc05"));
                    currentScore++;
                }else{
                    option4Btn.setBackgroundColor(Color.parseColor("#e31414"));
                }
                questionAttempted++;
                currentPos=currentPos+1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setDataToView(currentPos);
                    }
                }, 1000);
            }


        });


    }

    private void showBootomSheet(){
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(QuizActivity.this);
        View bottomSheetView= LayoutInflater.from(getApplicationContext()).inflate(R.layout.score_bottom_sheet,(LinearLayout)findViewById(R.id.idLLscore));
        TextView scoreTv=bottomSheetView.findViewById(R.id.idTvScore);
        Button homeBtn=bottomSheetView.findViewById(R.id.idGotoHomeBtn);
        scoreTv.setText("Your Score is \n"+currentScore +"/"+quizModalArrayList.size());
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                binding = ActivityMainBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());
                BottomNavigationView navView = findViewById(R.id.nav_view);
                NavController navController = Navigation.findNavController(QuizActivity.this, R.id.nav_host_fragment_activity_main);
                NavigationUI.setupWithNavController(binding.navView, navController);
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    }

    private void saveData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userid).child("quizData").child(quizid);
        databaseReference.child("quizname").setValue(quizname);
        databaseReference.child("quizid").setValue(quizid);
        databaseReference.child("totalquestion").setValue(totalquestion);
        databaseReference.child("date").setValue(java.time.LocalDate.now().toString());
        databaseReference.child("score").setValue(currentScore)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(QuizActivity.this,"Date Saved",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setDataToView(int currentPos){
        if(questionAttempted>quizModalArrayList.size())
        {
            showBootomSheet();
        }
        else {
            option1Btn.setBackgroundColor(Color.parseColor("#CC8BDF"));
            option2Btn.setBackgroundColor(Color.parseColor("#CC8BDF"));
            option3Btn.setBackgroundColor(Color.parseColor("#CC8BDF"));
            option4Btn.setBackgroundColor(Color.parseColor("#CC8BDF"));
            questionNumberTv.setText("Questions : "+questionAttempted+"/"+quizModalArrayList.size());
            questionTv.setText(quizModalArrayList.get(currentPos).getQuestion());
            option1Btn.setText(quizModalArrayList.get(currentPos).getOption1());
            option2Btn.setText(quizModalArrayList.get(currentPos).getOption2());
            option3Btn.setText(quizModalArrayList.get(currentPos).getOption3());
            option4Btn.setText(quizModalArrayList.get(currentPos).getOption4());
        }

}
    private void getQuizQuestion(ArrayList<QuizModal> quizModalArrayList) {

        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userid).child("quiz").child(quizid).child("questions");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Random random = new Random();
                    // Generate a random number from 1 to 4
                    int randomNumber = random.nextInt(4) + 1;
                    String qname=dataSnapshot.child("questionName").getValue().toString();
                    String op1=dataSnapshot.child("option1").getValue().toString();
                    String op2=dataSnapshot.child("option2").getValue().toString();
                    String op3=dataSnapshot.child("option3").getValue().toString();
                    String answer=dataSnapshot.child("answer").getValue().toString();
                    if(randomNumber==1)
                        quizModalArrayList.add(new QuizModal(qname,answer,op2,op3,op1,answer));
                    else if (randomNumber==2) {
                        quizModalArrayList.add(new QuizModal(qname,op1,answer,op3,op2,answer));
                    } else if (randomNumber==3) {
                        quizModalArrayList.add(new QuizModal(qname,op1,op2,answer,op3,answer));
                    }
                    else {
                        quizModalArrayList.add(new QuizModal(qname,op1,op2,op3,answer,answer));
                    }
                }
                setDataToView(currentPos);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}