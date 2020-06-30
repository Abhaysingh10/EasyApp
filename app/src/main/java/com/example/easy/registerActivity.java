package com.example.easy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import org.w3c.dom.Text;

public class registerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final FirebaseAuth firebaseAuth = null;
        EditText email, password ;
        final Button register;

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register =  findViewById(R.id.register);
        firebaseAuth.getInstance();

        final String getEmail = email.getText().toString();
        final String getPassword = password.getText().toString();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(getEmail)) {
                    Toast.makeText(registerActivity.this, "Please enter valid email", Toast.LENGTH_SHORT);
                } else if (TextUtils.isEmpty(getPassword)) {
                    Toast.makeText(registerActivity.this, "Please enter valid email", Toast.LENGTH_SHORT);
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(getEmail, getPassword).
                            addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(registerActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(registerActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
            });

    }
}

