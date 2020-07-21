package com.example.easy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.GenericLifecycleObserver;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

public class categories extends AppCompatActivity {

    TextView userName;
    ImageView profileImage;
    Button logoutbutn;
    GoogleSignInOptions gso;
    GoogleApi googleApi;
    GoogleSignInClient mSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);



        userName = (TextView) findViewById(R.id.userName);
        profileImage = findViewById(R.id.userProfile_Image);
        logoutbutn = findViewById(R.id.logout_button);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }

    ////// Need to work on ActivityResult


        public void  handleSignInResult(GoogleSignInResult result){
            if (result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                userName.setText(account.getDisplayName());
                try {

            }catch (NullPointerException e){
                    Toast.makeText(categories.this, "Image not found", Toast.LENGTH_SHORT).show();
                }
        }
            else{
                gotoMainActivity();
            }


    }

    private void gotoMainActivity(){
        startActivity(new Intent(categories.this, MainActivity.class));
    }

}

