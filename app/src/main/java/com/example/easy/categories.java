package com.example.easy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.viewpager.widget.ViewPager;

import android.app.StatusBarManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class categories extends AppCompatActivity {

    TextView userName;
    ImageView profileSettingbtn; ;
    Button logoutbutn;
    MaterialCardView cardView, categoryOne, categoryTwo, categoryThree, boxOne ;
    TextView categoryTextOne, categoryTextTwo, categoryTextThree;
    TextView categoryText ;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth firebaseAuth ;
    String givenName;
    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    private Fragment fragment;
;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        userName = (TextView) findViewById(R.id.userName);
        //profileImage = findViewById(R.id.userProfile_Image);
     //   logoutbutn = findViewById(R.id.logout_button);
   //     cardView = findViewById(R.id.cardView);
        profileSettingbtn = findViewById(R.id.profileSetting);
        categoryOne = findViewById(R.id.categoryOne);
        categoryTwo = findViewById(R.id.categoryTwo);
        categoryThree = findViewById(R.id.categoryThree);
      //  boxOne = findViewById(R.id.boxOne);
        categoryText = findViewById(R.id.categoryText);
        firebaseAuth = firebaseAuth.getInstance();
        categoryTextOne = findViewById(R.id.categoryTextOne);
        categoryTextTwo = findViewById(R.id.categoryTextTwo);
        categoryTextThree = findViewById(R.id.categoryTextThree);
        viewPager = findViewById(R.id.viewPager);
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new cat1());
        viewPager.setAdapter(viewPageAdapter);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        categoryTextThree.setOnClickListener(new View.OnClickListener() {
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

        categoryTextTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(categories.this, category.class));
            }
        });

        profileSettingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(categories.this, profileSetting.class));
            }
        });

        categoryTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cat1 firstCategory = new cat1();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                 //   fragmentTransaction.replace()
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
          givenName = account.getGivenName();
          userName.setText(givenName);
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

