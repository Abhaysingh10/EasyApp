package com.example.easy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.GenericLifecycleObserver;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class categories extends AppCompatActivity {

    TextView userName;
    //ImageView profileImage;
    Button logoutbutn;
    GoogleSignInOptions gso;
    GoogleApi googleApi;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth firebaseAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);



        userName = (TextView) findViewById(R.id.userName);
        //profileImage = findViewById(R.id.userProfile_Image);
        logoutbutn = findViewById(R.id.logout_button);
        firebaseAuth = firebaseAuth.getInstance();


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        logoutbutn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             mGoogleSignInClient.signOut()
             .addOnCompleteListener(categories.this, new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     Toast.makeText(categories.this, "Logged out", Toast.LENGTH_SHORT).show();
                 }
             });
                startActivity(new Intent(categories.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        updateUI(firebaseUser);
    }

    ////// Need to work on ActivityResult

    private void updateUI(FirebaseUser user){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
      if (account != null){
          String displayName = account.getDisplayName();
          userName.setText(displayName);
       /*   Glide.with(this)
                  .load(user.getPhotoUrl().toString())
                  .into(profileImage);
      */
      }
      else{
          Toast.makeText(categories.this, "Something went wrong" , Toast.LENGTH_LONG).show();
      }
    }
}

