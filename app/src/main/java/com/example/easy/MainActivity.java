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

import com.example.easy.session.SessionManagment;
import com.example.easy.session.UserSessionModel;
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
    private final static int RC_SIGN_IN = 0;


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
                startActivity(new Intent(MainActivity.this, category.class));
            }
        });

    }

    private void signIn() {   // This is will be called when Google SignIN Button is clicked
        barActivated();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account.getIdToken());
                } else{
                    Log.w("AUTH", "Account is NULL");
                    Toast.makeText(MainActivity.this, "Sign-in failed, try again later.", Toast.LENGTH_LONG).show();
                }
            } catch (ApiException e) {
                Log.w("AUTH", "Google sign in failed", e);
                Toast.makeText(MainActivity.this, "Sign-in failed, try again later.", Toast.LENGTH_LONG).show();
            }
        }
    }



    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(MainActivity.this, MainPage.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            // Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
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

    // Checking session for user ---->

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!= null){
            Intent intent = new Intent(MainActivity.this, MainPage.class);
            intent.putExtra("userName", firebaseUser.getDisplayName());
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, "", Toast.LENGTH_LONG).show();
        }
    }

}




