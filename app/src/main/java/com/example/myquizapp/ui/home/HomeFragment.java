package com.example.myquizapp.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizapp.R;
import com.example.myquizapp.databinding.FragmentHomeBinding;
import com.example.myquizapp.ui.create.Quiz;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    Button homeBtn;
    private FragmentHomeBinding binding;
   // private EditText searchEditText;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private List<quiz> dataList;
    private DataAdapter dataAdapter;
    private DatabaseReference databaseReference;
    private Query query;
    FirebaseDatabase firebaseDatabase;
    private ChildEventListener childEventListener;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        searchView = binding.searchView;
        recyclerView = binding.SearchToRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataList = new ArrayList<quiz>();
        dataAdapter = new DataAdapter(dataList,getActivity());
        recyclerView.setAdapter(dataAdapter);



        FirebaseApp.initializeApp(getContext());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("publicquiz");


        // Retrieve initial data from Firebase
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                // Retrieve the data as a HashMap
                Map<String,Object> dataMap = (HashMap<String,Object>) dataSnapshot.getValue();

                String quizname = String.valueOf(dataMap.get("quizname"));
                String quizid = String.valueOf(dataMap.get("quizid"));
                String creater = String.valueOf(dataMap.get("creater"));
                String totalquestion=String.valueOf(dataMap.get("totalquestion"));
                String userid=String.valueOf(dataMap.get("userid"));
                String date=String.valueOf(dataMap.get("date"));
                quiz q=new quiz(creater, quizid, quizname, totalquestion,userid,date);


                 dataList.add(q);
                dataAdapter.notifyDataSetChanged();
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

        // Handle search query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    // Display all data when the search query is empty
                    dataAdapter.setFilter(dataList);
                } else {
                    // Filter the data based on the search query
                    List<quiz> filteredList = new ArrayList<quiz>();
                    for (quiz data : dataList) {
                        if (data.getQuizname().toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(data);
                        }
                    }
                    dataAdapter.setFilter(filteredList);
                }
                return true;
            }
        });


        return root;
    }


}
