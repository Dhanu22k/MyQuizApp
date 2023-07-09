package com.example.myquizapp.ui.create;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizapp.R;
import com.example.myquizapp.databinding.FragmentCreateBinding;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateFragment extends Fragment {
    ImageButton createBtn;
    BottomNavigationView navView;
    DatabaseReference databaseReference;
    RecyclerView quizRecyclerView;
    QuizAdapter quizAdapter;
    ArrayList<Quiz> list;
    String uid;
    ShimmerFrameLayout shimmerFrameLayout;

    private @NonNull FragmentCreateBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        createBtn=binding.createBtn;
        quizRecyclerView=binding.quizRecyclerView;
        quizRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SharedPreferences sharedPref = getContext().getSharedPreferences("savedUserData", MODE_PRIVATE);
        uid = sharedPref.getString("uid", "");
        shimmerFrameLayout=binding.shimmerView;
        shimmerFrameLayout.startShimmerAnimation();

        showQuiz();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertName = new AlertDialog.Builder(getContext());
                final EditText inputName = new EditText(getContext());
                inputName.setHint("Quiz name");
                alertName.setTitle("Enter the name for Quiz");
                // titles can be used regardless of a custom layout or not
                alertName.setView(inputName);
                LinearLayout layoutName = new LinearLayout(getContext());
                layoutName.setOrientation(LinearLayout.VERTICAL);
                layoutName.addView(inputName); // displays the user input bar
                alertName.setView(layoutName);
                alertName.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String quizName=inputName.getText().toString();
                        if(!quizName.isEmpty())
                        {
                            Bundle bundle=new Bundle();
                            bundle.putString("quizname",quizName);
                            //changing fragment
                            Fragment createQuizFrg=new CreateQuizViewModel.CreateQuizFragment();
                            createQuizFrg.setArguments(bundle);
                            FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.nav_host_fragment_activity_main,createQuizFrg).commit();
                            navView=getActivity().findViewById(R.id.nav_view);
                            navView.setVisibility(View.GONE);
                            createBtn.setVisibility(View.INVISIBLE);
                            quizRecyclerView.setVisibility(View.INVISIBLE);
                        }
                        else{
                            Toast.makeText(getContext(),"Name is required",Toast.LENGTH_SHORT).show();
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


        return root;
    }




    @SuppressLint("SuspiciousIndentation")
    private void showQuiz(){

        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid).child("quiz");
                databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                quizAdapter=new QuizAdapter(getContext(),list);
                quizRecyclerView.setAdapter(quizAdapter);
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Quiz quiz=dataSnapshot.getValue(Quiz.class);
                    list.add(quiz);
                }
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.INVISIBLE);
                quizAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


}