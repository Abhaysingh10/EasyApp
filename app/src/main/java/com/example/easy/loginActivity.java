package com.example.easy;

import androidx.annotation.NonNull;
import  androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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


    final static int RC_SIGN_IN= 2 ;
    FirebaseAuth mfirebaseAuth;
    EditText email ;
    EditText password ;
    Button login ;
    //GoogleSignInClient mGoogleSignInClient ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login_button);



        // Code for login with SinInOption

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_Email = email.getText().toString();
                String user_Password = password.getText().toString();
                mfirebaseAuth = FirebaseAuth.getInstance();

                if(TextUtils.isEmpty(user_Email) || !Patterns.EMAIL_ADDRESS.matcher(user_Email).matches() ){
                    Toast.makeText(loginActivity.this, "Please enter valid mail", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(user_Password)){
                    Toast.makeText( loginActivity.this, "Please enter valid Passsword", Toast.LENGTH_SHORT).show();
                    return;
                }
                mfirebaseAuth.signInWithEmailAndPassword(user_Email, user_Password).
                        addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(loginActivity.this, "Logged in Successfully" , Toast.LENGTH_SHORT).show();
                                    FirebaseUser firebaseUser = mfirebaseAuth.getCurrentUser();
                                    String userName = firebaseUser.getDisplayName() ;
                                    Intent intent = new Intent(loginActivity.this, categories.class);
                                    intent.putExtra("This is userName" , userName.toString());
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(loginActivity.this, "Login Failed, Try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}
