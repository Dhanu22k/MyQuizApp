package com.example.myquizapp.ui.create;

import static android.content.Context.MODE_PRIVATE;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myquizapp.LoginPage;
import com.example.myquizapp.MainActivity;
import com.example.myquizapp.R;
import com.example.myquizapp.databinding.FragmentCreateBinding;
import com.example.myquizapp.databinding.FragmentHomeBinding;
import com.example.myquizapp.databinding.FragmentProfileBinding;
import com.example.myquizapp.ui.dashboard.DashboardFragment;
import com.example.myquizapp.ui.home.HomeFragment;
import com.example.myquizapp.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateFragment extends Fragment {
    ImageButton createBtn;
    BottomNavigationView navView;
    DatabaseReference databaseReference;
    private @NonNull FragmentCreateBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        createBtn=binding.createBtn;

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
                            Fragment createQuizFrg=new CreateQuizFragment();
                            createQuizFrg.setArguments(bundle);
                            FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.container,createQuizFrg,"tag").addToBackStack(null).commit();
                            navView=getActivity().findViewById(R.id.nav_view);
                            navView.setVisibility(View.GONE);
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

    private void showQuiz(){
        SharedPreferences sharedPref = getContext().getSharedPreferences("savedUserData", MODE_PRIVATE);
        String uid = sharedPref.getString("uid", "");
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    DataSnapshot dataSnapshot = task.getResult();
                  Long quizLength= dataSnapshot.child("quiz").getChildrenCount();
                    for(int i=1;i<=quizLength;i++){
                        System.out.println(dataSnapshot.child("quiz").child(String.valueOf(i)));
                    }
                } else {
                    Toast.makeText(getActivity(), "data not found", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
            }

        });

    }


}