package com.example.myquizapp.ui.profile;


import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myquizapp.MainActivity;
import com.example.myquizapp.databinding.FragmentProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {
    TextView profileName,emailName,userId;
    Button logOutBtn;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        profileName = binding.profilename;
        emailName=binding.textViewEmail;
        userId=binding.textViewUserId;
        logOutBtn=binding.btnLogOut;
        progressBar=binding.profileProgresBar;
        progressBar.setVisibility(View.INVISIBLE);

        SharedPreferences sharedPref = getContext().getSharedPreferences("savedUserData", MODE_PRIVATE);
        String uid = sharedPref.getString("uid", "");
        if (profileViewModel.getUid() == null) {
            progressBar.setVisibility(View.VISIBLE);
            databaseReference = FirebaseDatabase.getInstance().getReference("users");
            databaseReference.child(uid).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                      profileViewModel.setName(String.valueOf(dataSnapshot.child("fullName").getValue()));
                      profileViewModel.setEmail(String.valueOf(dataSnapshot.child("mail").getValue()));
                      profileViewModel.setUid(uid);
                        profileName.setText(profileViewModel.getName());
                        emailName.setText(profileViewModel.getEmail());
                        userId.setText("UserId: "+profileViewModel.getUid());

                    } else {
                        Toast.makeText(getActivity(), "data not found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            });
        }else {
            profileName.setText(profileViewModel.getName());
            emailName.setText(profileViewModel.getEmail());
            userId.setText("UserId: "+profileViewModel.getUid());
        }

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                SharedPreferences preferences =getContext().getSharedPreferences("savedUserData", MODE_PRIVATE);
                preferences.edit().remove("uid").commit();
                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });
        return root;
    }


}