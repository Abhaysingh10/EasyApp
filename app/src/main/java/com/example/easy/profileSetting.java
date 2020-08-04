package com.example.easy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profileSetting extends AppCompatActivity {

    FirebaseUser firebaseUser;
    GoogleSignInClient mgoogleSignInClient;
    GoogleSignInOptions gso;
    FirebaseAuth firebaseAuth;
    ImageView imageView;
    TextView userName;
    TextView email,getGivenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        imageView = findViewById(R.id.profileImage);
        userName = findViewById(R.id.username);
        email = findViewById(R.id.email);
        getGivenName = findViewById(R.id.getGivenName);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }




     gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
     .requestEmail()
     .build();

     mgoogleSignInClient = GoogleSignIn.getClient(this, gso);

     firebaseAuth = firebaseAuth.getInstance();



    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = firebaseAuth.getCurrentUser();
        updateUI(firebaseUser);
    }

    void updateUI(FirebaseUser user){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null){
            Glide.with(this)
                    .load(user.getPhotoUrl().toString())
                    .into(imageView);
            userName.setText( account.getDisplayName());
            email.setText(account.getEmail());
            getGivenName.setText(account.getGivenName());
        }
    }
}