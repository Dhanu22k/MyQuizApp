package com.example.myquizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.myquizapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {
    Button RegisterBtn, loginButton;
    EditText editTextmail, editTextpassword;
    ProgressBar progressBar;
    FirebaseAuth auth;
    private ActivityMainBinding binding;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        editTextmail = findViewById(R.id.loginEmail);
        editTextpassword = findViewById(R.id.loginPassword);
        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.loginProgressBar);
        progressBar.setVisibility(View.INVISIBLE);
        loginButton = findViewById(R.id.loginFieldBtn);
        RegisterBtn = findViewById(R.id.loginFieldRegisterBtn);
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(intent);
                finish();

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextmail.getText().toString();
                String password = editTextpassword.getText().toString();

                if (!(email.isEmpty() || password.isEmpty())) {
                    progressBar.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(LoginPage.this, "Success", Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedPreferences = getSharedPreferences("savedUserData", MODE_PRIVATE);
// Creating an Editor object to edit(write to the file)
                                    String uid=authResult.getUser().getUid();
                                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
// Storing the key and its value as the data fetched from edittext
                                    myEdit.putString("uid",uid);
                                    myEdit.commit();


                                    binding = ActivityMainBinding.inflate(getLayoutInflater());
                                    setContentView(binding.getRoot());
                                    BottomNavigationView navView = findViewById(R.id.nav_view);
                                    NavController navController = Navigation.findNavController(LoginPage.this, R.id.nav_host_fragment_activity_main);
                                    NavigationUI.setupWithNavController(binding.navView, navController);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(LoginPage.this, "Failed:" + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
                    Toast.makeText(LoginPage.this, "Please fill all the field", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}