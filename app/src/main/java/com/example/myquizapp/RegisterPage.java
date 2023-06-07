package com.example.myquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myquizapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class RegisterPage extends AppCompatActivity {
    Button loginbtn, registerBtn;
    EditText email, fullName, registerPassword, confirmPassword;
    TextView warningLabel;
    ProgressBar progressBar;
    DatabaseReference userDB;
    FirebaseAuth auth;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        registerBtn = findViewById(R.id.registerBtn);
        email = findViewById(R.id.email);
        fullName = findViewById(R.id.fullName);
        registerPassword = findViewById(R.id.registerPass);
        confirmPassword = findViewById(R.id.confirmPassword);
        warningLabel=findViewById(R.id.warningLabel);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        userDB = FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String userName = fullName.getText().toString();
                String pass1 = registerPassword.getText().toString();
                String pass2 = confirmPassword.getText().toString();
                if(validatePassword(pass1)){
                    try {
                        progressBar.setVisibility(View.VISIBLE);
                        insertUserData(mail,userName,pass1);
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    //not valid
                    warningLabel.setText("Password contains 7 characters mixed with Letters and digits");
                }
            }
        });


        loginbtn = findViewById(R.id.loginBtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });


        //password matching
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable mEdit) {
                if (passwordMatched()) {
                    confirmPassword.setBackgroundResource(R.drawable.inputbg);
                    registerBtn.setEnabled(true);
                } else {
                    confirmPassword.setBackgroundResource(R.drawable.redborder);
                    registerBtn.setEnabled(false);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

    }

    private void insertUserData(String mail,String userName,String password) throws InterruptedException {


        auth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user=new User(mail,userName,password);
                    userDB.child("users").child(task.getResult().getUser().getUid()).setValue(user)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Write failed
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(RegisterPage.this,"Register Failed",Toast.LENGTH_SHORT).show();
                                }
                            });
                    Toast.makeText(RegisterPage.this,"Register succesfull",Toast.LENGTH_SHORT).show();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    startActivity(new Intent(RegisterPage.this,LoginPage.class));
                    finish();

                }
                else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(RegisterPage.this,"Failed:"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    private boolean passwordMatched() {
        String pass1 = registerPassword.getText().toString();
        String pass2 = confirmPassword.getText().toString();
        return pass1.equals(pass2);
    }

    public static boolean validatePassword(String password) {
        // Check minimum length
        if (password.length() < 7) {
            return false;
        }
        boolean hasLetter = false;
        boolean hasNumber = false;
        // Iterate over each character in the password
        for (char c : password.toCharArray()) {
            // Check if it's a letter
            if (Character.isLetter(c)) {
                hasLetter = true;
            }
            // Check if it's a number
            else if (Character.isDigit(c)) {
                hasNumber = true;
            }
        }

        // Check if both letters and numbers are present
        return hasLetter && hasNumber;
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
