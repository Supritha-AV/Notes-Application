package com.project.notesapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText,passwordEditText;
    Button loginBtn;
    ProgressBar progressBar;
    TextView createAccountBtnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.textEdtName);
        passwordEditText = findViewById(R.id.pwdText);
        loginBtn = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.loadBar);
        createAccountBtnTextView = findViewById(R.id.createAccountText_btn);

        loginBtn.setOnClickListener((v)-> loginUser());
        createAccountBtnTextView.setOnClickListener((v)-> startActivity(new Intent(LoginActivity.this,CreateAccountActivity.class)));


    }
    void loginUser() {

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        boolean isValidated = validateData(email, password);
        if (!isValidated) {
            return;
        }
        // If the given data is true then authenticate using Firebase
        loginAccountInFirebase(email, password);
    }
    void loginAccountInFirebase(String email, String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if(task.isSuccessful()){
                    // login is successful
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        //go to main activity
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }
                    else{
                        Utility.showToast(LoginActivity.this,"Email not verified, Please verify your email");
                    }
                }
                else {
                    // login is unsuccessful
                    Utility.showToast(LoginActivity.this,task.getException().getLocalizedMessage());
                }

            }
        });
    }
        void changeInProgress(boolean inProgress){
            if(inProgress){
                progressBar.setVisibility(View.VISIBLE);
                loginBtn.setVisibility(View.GONE);
            }
            else{
                progressBar.setVisibility(View.GONE);
                loginBtn.setVisibility(View.VISIBLE);
            }
        }
        boolean validateData(String email,String password){
            //validate the data that are given by the input user
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                emailEditText.setError("Email is invalid");
                return false;
            }
            if(password.length()<8){
                passwordEditText.setError("Password must contain minimum of 8 characters ");
                return false;
            }
            return true;
        }

}
