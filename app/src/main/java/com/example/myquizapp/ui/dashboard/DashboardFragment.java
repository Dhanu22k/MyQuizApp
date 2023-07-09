package com.example.myquizapp.ui.dashboard;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizapp.databinding.FragmentDashboardBinding;
import com.example.myquizapp.ui.home.DataAdapter;
import com.example.myquizapp.ui.home.quiz;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment {
    RecyclerView recyclerView;
    private List<dashQuiz> dataList;
    private dashDataAdapter dashdataAdapter;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private FragmentDashboardBinding binding;
    String uid;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SharedPreferences sharedPref = getContext().getSharedPreferences("savedUserData", MODE_PRIVATE);
       uid = sharedPref.getString("uid", "");
        recyclerView = binding.dashRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataList = new ArrayList<dashQuiz>();
        dashdataAdapter = new dashDataAdapter(dataList,getActivity());
        recyclerView.setAdapter(dashdataAdapter);



        FirebaseApp.initializeApp(getContext());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child(uid).child("quizData");
        // Retrieve initial data from Firebase
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                // Retrieve the data as a HashMap
                Map<String,Object> dataMap = (HashMap<String,Object>) dataSnapshot.getValue();

                String quizname = String.valueOf(dataMap.get("quizname"));
                String quizid = String.valueOf(dataMap.get("quizid"));
                String totalquestion=String.valueOf(dataMap.get("totalquestion"));
                String date=String.valueOf(dataMap.get("date"));
                String score=String.valueOf(dataMap.get("score"));
                dashQuiz q=new dashQuiz(quizid, quizname, totalquestion,date,score);
                dataList.add(q);
                dashdataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // Not implemented
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // Not implemented
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // Not implemented
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Failed to retrieve data from Firebase", Toast.LENGTH_SHORT).show();
            }
        };
     databaseReference.addChildEventListener(childEventListener);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}