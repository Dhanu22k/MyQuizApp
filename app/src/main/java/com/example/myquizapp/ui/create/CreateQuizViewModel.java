package com.example.myquizapp.ui.create;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizapp.MainActivity;
import com.example.myquizapp.databinding.FragmentCreateQuizBinding;

import java.util.ArrayList;
import java.util.List;

public class CreateQuizViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public static class CreateQuizFragment extends Fragment {
        Button cancelBtn,saveBtn, removeBtn, addBtn;
        TextView quizNameTextView;
        List<Question> questions = new ArrayList<Question>();
        private CreateQuizViewModel mViewModel;
        private @NonNull FragmentCreateQuizBinding binding;
        private int i=1;

        public static CreateQuizFragment newInstance() {
            return new CreateQuizFragment();
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            binding = FragmentCreateQuizBinding.inflate(inflater, container, false);
            View root = binding.getRoot();
            Bundle bundle=getArguments();
            String quizNameString=String.valueOf(bundle.getString("quizname"));
           quizNameTextView=binding.quizNameTextView;
           quizNameTextView.setText(quizNameString);

            removeBtn = binding.questionRemoveBtn;
            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String[] index = new String[1]; // user input bar
                    AlertDialog.Builder alertName = new AlertDialog.Builder(getContext());
                    final EditText inputIndex = new EditText(getContext());
                    inputIndex.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL); //for decimal numbers
                    inputIndex.setHint(1+" to "+questions.size());
                    alertName.setTitle("Enter Question Number");
                    // titles can be used regardless of a custom layout or not
                    alertName.setView(inputIndex);
                    LinearLayout layoutName = new LinearLayout(getContext());
                    layoutName.setOrientation(LinearLayout.VERTICAL);
                    layoutName.addView(inputIndex); // displays the user input bar
                    alertName.setView(layoutName);
                    alertName.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            index[0] = inputIndex.getText().toString(); // variable to collect user input
                            System.out.println(Integer.valueOf(index[0]));
                                int ival=(int)Integer.valueOf(index[0]);
                            if(ival>0 && ival<=questions.size()){
                                questions.remove(ival-1);
                                Toast.makeText(getActivity(),"Done",Toast.LENGTH_SHORT).show();
                                showQuestions();
                            }
                            else {
                                Toast.makeText(getActivity(),"Question Number Does not Exist",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    alertName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel(); // closes dialog
                        }
                    });
                    alertName.show(); // display the dialog
                }
            });



            //Adding Questions
            addBtn=binding.quesstionAddBtn;
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ArrayList<CharSequence> arrayListCollection = new ArrayList<>();
                    ArrayAdapter<CharSequence> adapter;
                    final String[] index = new String[5]; // user input bar
                    AlertDialog.Builder alertName = new AlertDialog.Builder(getContext());
                    final EditText qName = new EditText(getContext());
                    qName.setHint("Enter The Question");
                    final EditText ansName = new EditText(getContext());
                    ansName.setHint("Enter The Answer");
                    final EditText op1 = new EditText(getContext());
                    op1.setHint("Enter Option 1");
                    final EditText op2 = new EditText(getContext());
                    op2.setHint("Enter Option 2");
                    final EditText op3 = new EditText(getContext());
                    op3.setHint("Enter Option 3");

                    alertName.setTitle("Fill All the Field");

                    LinearLayout layoutName = new LinearLayout(getContext());
                    layoutName.setOrientation(LinearLayout.VERTICAL);
                    layoutName.addView(qName); // displays the user input bar
                    layoutName.addView(ansName);
                    layoutName.addView(op1);
                    layoutName.addView(op2);
                    layoutName.addView(op3);

                    alertName.setView(layoutName);
                    alertName.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String q =qName.getText().toString();
                            String a=ansName.getText().toString();
                            String o1 = op1.getText().toString();
                            String o2 = op2.getText().toString();
                            String o3 = op3.getText().toString();
                            if(!q.isEmpty()&& !a.isEmpty()&& !o1.isEmpty()&& !o2.isEmpty() && !o3.isEmpty()){
                                addQuestion(q, a,o1,o2,o3);
                            }
                            else {
                                Toast.makeText(getContext(),"Please fill all the field",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    alertName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel(); // closes dialog
                        }
                    });
                    alertName.show(); // display the dialog


                }
            });





            cancelBtn = binding.cancelBtn;
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(), MainActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }

            });

            saveBtn=binding.saveBtn;
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


            return root;
        }

        private void addQuestion(String qName,String ans,String op1,String op2,String op3){
            questions.add(new Question(i++, qName, op1, op2, op3, ans));
            showQuestions();
        }

        private void showQuestions() {
            for(int j=0;j<questions.size();j++){
                questions.get(j).setQuestionNumber(j+1);
            }

            RecyclerView questionRecyclerView = binding.questionRecyclerView;
            questionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            questionRecyclerView.setAdapter(new CreateViewModel.questionAdapter(getActivity().getApplicationContext(), questions));
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            mViewModel = new ViewModelProvider(this).get(CreateQuizViewModel.class);
            // TODO: Use the ViewModel
        }

    }

    public static class Question {
        int questionNumber;
        String questionName;
        String option1;
        String option2;
        String option3;
        String answer;

        public Question(int questionNumber, String questionName, String option1, String option2, String option3, String answer) {
            this.questionNumber = questionNumber;
            this.questionName = questionName;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.answer = answer;
        }

        public int getQuestionNumber() {
            return questionNumber;
        }

        public void setQuestionNumber(int questionNumber) {
            this.questionNumber = questionNumber;
        }

        public String getQuestionName() {
            return questionName;
        }

        public void setQuestionName(String questionName) {
            this.questionName = questionName;
        }

        public String getOption1() {
            return option1;
        }

        public void setOption1(String option1) {
            this.option1 = option1;
        }

        public String getOption2() {
            return option2;
        }

        public void setOption2(String option2) {
            this.option2 = option2;
        }

        public String getOption3() {
            return option3;
        }

        public void setOption3(String option3) {
            this.option3 = option3;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }
}