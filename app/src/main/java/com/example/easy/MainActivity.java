package com.example.easy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = null ;
    Button loginButton, registerButton,cat;
    ImageButton googleLogin_button, facebookLogin_button, twitterLogin_button;
    TextView guestMode;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    public GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.register);
        googleLogin_button = findViewById(R.id.googleLogin_button);
        facebookLogin_button = findViewById(R.id.facebookLogin_button);
        twitterLogin_button = findViewById(R.id.twitterLogin_button);
        cat = findViewById(R.id.cat);
        guestMode = findViewById(R.id.guestMode);
        progressBar = findViewById(R.id.progressBar);

      //  progressBar.setVisibility(View.INVISIBLE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, loginActivity.class));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, registerActivity.class));
            }
        });

        googleLogin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, categories.class));
            }
        });

    }

   // @Override
    //protected void onStart() {
    //    super.onStart();
    //    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    //}


    private void signIn() {
        barActivated();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(MainActivity.this, "Signed In Successfully" , Toast.LENGTH_SHORT).show();
            firebaseAuthWithGoogle(acc);
        } catch (ApiException e) {
            Toast.makeText(MainActivity.this, "Something went Wrongg", Toast.LENGTH_SHORT).show();
            //    FirebaseGoogleAuth(null);
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        final AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    barActivated();
                    Toast.makeText(MainActivity.this,"Successfull",Toast.LENGTH_SHORT).show();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    startActivity(new Intent(MainActivity.this, categories.class));

                }    //   updateUI(user);

                else {
                    Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                    //  updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser fuser) {
       // btnSignOut.setVisibility(View.VISIBLE);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null){
            String personName = account.getDisplayName();
            String personEmail = account.getEmail();
            Toast.makeText(MainActivity.this, personName + personEmail ,Toast.LENGTH_SHORT).show();
        }
    }

    private void barActivated(){
        progressBar.setVisibility(View.VISIBLE);
    }

    private void barDeactivated(){
        progressBar.setVisibility(View.INVISIBLE);
    }
}




