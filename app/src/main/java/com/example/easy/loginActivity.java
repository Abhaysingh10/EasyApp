package com.example.easy;

import androidx.annotation.NonNull;
import  androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class loginActivity extends AppCompatActivity {


    private final static int RC_SIGN_IN= 2 ;
    private FirebaseAuth firebaseAuth;
    private EditText email ;
    private EditText password ;
    private Button login ;
    //GoogleSignInClient mGoogleSignInClient ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.register);

        final String getEmail = email.getText().toString();
        final String getPassword = password.getText().toString();

        // Code for login with SinInOption

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(getEmail)){
                    Toast.makeText(loginActivity.this, "Please enter valid mail", Toast.LENGTH_SHORT);
                }
                else if (TextUtils.isEmpty(getPassword)){
                    Toast.makeText( loginActivity.this, "Please enter valid Passsword", Toast.LENGTH_SHORT);
                }

                firebaseAuth.signInWithEmailAndPassword(getEmail, getPassword)
                        .addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(loginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT);
                                }
                                else {
                                    Toast.makeText(loginActivity.this, "Login Failed", Toast.LENGTH_SHORT);
                                }
                            }
                        });
            }
        });

    }


}
