package com.example.easy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.provider.ContactsContract;
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

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class registerActivity extends AppCompatActivity {



    EditText email ;
    EditText password ;
    Button register;
    FirebaseAuth firebaseAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register =  findViewById(R.id.register1);
        firebaseAuth =FirebaseAuth.getInstance();





        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               final   String getEmail = email.getText().toString();
                 final String getPassword = password.getText().toString();



                if (TextUtils.isEmpty(getEmail) || !Patterns.EMAIL_ADDRESS.matcher(getEmail).matches()) {
                    Toast.makeText(registerActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(getPassword)) {
                    Toast.makeText(registerActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(getEmail, getPassword).
                            addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(registerActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(registerActivity.this, afterLogin.class ));
                                    }    else {
                                        Toast.makeText(registerActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

            }
            });


        }
}


