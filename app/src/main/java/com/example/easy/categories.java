package com.example.easy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.view.ViewStub;
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

import java.util.concurrent.ConcurrentSkipListMap;

public class categories extends AppCompatActivity {

    private TextView userName;
    private ImageView profileSettingbtn; ;
    private Button logoutbutnn;
    private MaterialCardView  categoryOne, categoryTwo, categoryThree ;
    private TextView categoryTextOne, categoryTextTwo, categoryTextThree;
    private TextView categoryText ;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth firebaseAuth ;
    private String givenName;
    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    private Fragment fragment;
    private ConstraintLayout constraintLayoutTwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        userName = (TextView) findViewById(R.id.userName);
        //profileImage = findViewById(R.id.userProfile_Image);
        logoutbutnn = findViewById(R.id.logout_button);
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
        constraintLayoutTwo = findViewById(R.id.constraintTwo);
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new cat1());

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

        profileSettingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(categories.this, profileSetting.class));
            }
        });

        logoutbutnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.getInstance().signOut();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser == null) {
                    startActivity(new Intent(categories.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                                                                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                                                                                                    | Intent.FLAG_ACTIVITY_NEW_TASK));
                }else {
                    Toast.makeText(categories.this, "Nothing happened", Toast.LENGTH_LONG).show();
                }
            }
        });

        categoryTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstraintLayout stub = findViewById(R.id.constraintTwo);
                stub.addView(stub, 100, R.layout.activity_category);
            }
        });

    }
}

