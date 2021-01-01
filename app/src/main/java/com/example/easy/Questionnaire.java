
package com.example.easy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.protobuf.StringValue;

import java.lang.ref.Reference;
import java.sql.Time;
import java.util.ArrayList;

import static android.graphics.Color.RED;
import static android.graphics.Color.red;
import static com.example.easy.R.color.accent_material_dark;
import static com.example.easy.R.color.colorPrimary;
import static com.example.easy.R.color.leak_canary_heap_float_array;
import static com.example.easy.R.color.material_blue_grey_800;
import static com.example.easy.R.color.rightAnswerColor;
import static com.example.easy.R.color.white;
import static com.example.easy.R.color.wrongAnswerColor;
import static com.example.easy.R.color.wrongAnswerColor;
import static com.example.easy.R.drawable.rightanswer;

public class Questionnaire extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private TextView Question, question, movieName, scorecardTextView;
    private Node node;
    private int branchAddress = 1;
    int temp = 0 ;
    private MaterialButton optionOne, optionTwo, optionThree, optionFour, nextBtn;
    private String movieNameTxt, documentIDtext, answer;
    private int score = 114;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReference;
    private CollectionReference notebookRef;
    private SharedPreferences scoreCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        movieNameTxt = getIntent().getStringExtra("movieName");
        documentIDtext = getIntent().getStringExtra("documentID");
        SharedPreferences sharedPreferences = getSharedPreferences("GenreName", MODE_PRIVATE);
        String genreName = sharedPreferences.getString("Genre", null);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        scoreCard = getSharedPreferences("scoreCardFile", MODE_PRIVATE);
        SharedPreferences.Editor scoreEditor = scoreCard.edit();
        editor.putInt("score", score);
        editor.apply();
        movieName = findViewById(R.id.movieName);
        question = findViewById(R.id.Question);
        optionOne = findViewById(R.id.optionOne);
        optionTwo = findViewById(R.id.optionTwo);
        optionThree = findViewById(R.id.optionThree);
        optionFour = findViewById(R.id.optionFour);
        nextBtn = findViewById(R.id.nextButton);
        firebaseFirestore = FirebaseFirestore.getInstance();
        notebookRef = firebaseFirestore.collection(genreName)
                .document(documentIDtext)
                .collection(movieNameTxt);
        movieName.setText(movieNameTxt);

//        Toast.makeText(this, String.valueOf(branchAddress), Toast.LENGTH_SHORT).show();
        startGame(notebookRef, branchAddress);
        scorecardTextView = findViewById(R.id.textView2);

    }

    private void startGame(CollectionReference notebookRef, int branchAddress) {
        this.notebookRef = notebookRef;
        this.branchAddress = branchAddress;


        notebookRef.document(String.valueOf(branchAddress))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                String QUESION = documentSnapshot.get("question").toString();
                                //Toast.makeText(Questionnaire.this, QUESION, Toast.LENGTH_SHORT).show();
                                question.setText(QUESION);
                                optionOne.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(white)));
                                optionOne.setText(documentSnapshot.get("optionOne").toString());
                                optionOne.setOnClickListener(Questionnaire.this::onClick);
                                optionTwo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(white)));
                                optionTwo.setText(documentSnapshot.get("optionTwo").toString());
                                optionTwo.setOnClickListener(Questionnaire.this::onClick);
                                optionThree.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(white)));
                                optionThree.setText(documentSnapshot.get("optionThree").toString());
                                optionThree.setOnClickListener(Questionnaire.this::onClick);
                                optionFour.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(white)));
                                optionFour.setText(documentSnapshot.get("optionFour").toString());
                                optionFour.setOnClickListener(Questionnaire.this::onClick);
                                answer = documentSnapshot.get("answer").toString();
                            }
                        }
                    });

            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startGame(notebookRef, branchAddress+1);
                }
            });

    }

    public void onClick(View view) {
        SharedPreferences scoreCard = getSharedPreferences("scoreCardFile", MODE_PRIVATE);
        SharedPreferences.Editor scoreEditor = scoreCard.edit();
        Button b1, b2, b3, b4;
        b1 = (Button) findViewById(R.id.optionOne);
        b2 = (Button) findViewById(R.id.optionTwo);
        b3 = (Button) findViewById(R.id.optionThree);
        b4 = (Button) findViewById(R.id.optionFour);
        String buttonText;
        switch (view.getId()) {
            case R.id.optionOne:
                buttonText = b1.getText().toString();
                if (buttonText.equals(answer)) {
                    //Saving the score
                    temp++ ;
                    // Changing the colors of the buttons after click
                    optionOne.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(rightAnswerColor)));
                    optionOne.setClickable(false);
                    //displaying the score simultaneoulsy
                    scorecardTextView.setText(String.valueOf(temp));
                } else {
                    optionOne.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(wrongAnswerColor)));
                    if ((b2.getText().toString()).equals(answer)) {
                        optionTwo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(rightAnswerColor)));
                        optionTwo.setClickable(false);
                    } else if ((b3.getText().toString()).equals(answer)) {
                        optionThree.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(rightAnswerColor)));
                        optionThree.setClickable(false);
                    } else if ((b4.getText().toString()).equals(answer)) {
                        optionFour.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(rightAnswerColor)));
                        optionFour.setClickable(false);
                    }
                }
                break;
            case R.id.optionTwo:
                buttonText = b2.getText().toString();
                if (buttonText.equals(answer)) {
                    temp++;
                    // Changing the colors of the buttons after click
                    optionTwo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(rightAnswerColor)));
                    //displaying the score simultaneoulsy
                    scorecardTextView.setText(String.valueOf(temp));
                    optionTwo.setClickable(false);
                }else {
                    optionTwo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(wrongAnswerColor)));
                    if ((b2.getText().toString()).equals(answer)) {
                        optionOne.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(rightAnswerColor)));
                        optionOne.setClickable(false);
                    } else if ((b3.getText().toString()).equals(answer)) {
                        optionThree.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(rightAnswerColor)));
                        optionThree.setClickable(false);
                    } else if ((b4.getText().toString()).equals(answer)) {
                        optionFour.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(rightAnswerColor)));
                        optionFour.setClickable(false);
                    }
                }

                break;
            case R.id.optionThree:
                buttonText = b3.getText().toString();
                if (buttonText.equals(answer)) {
                    temp++;
                    // Changing the colors of the buttons after click
                    optionThree.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(rightAnswerColor)));
                    //displaying the score simultaneoulsy
                    scorecardTextView.setText(String.valueOf(temp));
                }else {
                    optionThree.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(wrongAnswerColor)));
                    if ((b2.getText().toString()).equals(answer)) {
                        optionTwo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(rightAnswerColor)));
                        optionTwo.setClickable(false);
                    } else if ((b3.getText().toString()).equals(answer)) {
                        optionOne.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(rightAnswerColor)));
                        optionOne.setClickable(false);
                    } else if ((b4.getText().toString()).equals(answer)) {
                        optionFour.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(rightAnswerColor)));
                        optionFour.setClickable(false);
                    }
                }
                break;
            case R.id.optionFour:
                buttonText = b4.getText().toString();
                if (buttonText.equals(answer)) {
                    temp++;
                    // Changing the colors of the buttons after click
                    optionFour.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(rightAnswerColor)));
                    //displaying the score simultaneoulsy
                    optionFour.setClickable(false);
                    scorecardTextView.setText(String.valueOf(temp));
                }else {
                    optionFour.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(wrongAnswerColor)));
                    if ((b2.getText().toString()).equals(answer)) {
                        optionTwo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(rightAnswerColor)));
                        optionTwo.setClickable(false);
                    } else if ((b3.getText().toString()).equals(answer)) {
                        optionThree.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(rightAnswerColor)));
                        optionThree.setClickable(false);
                    } else if ((b4.getText().toString()).equals(answer)) {
                        optionOne.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(rightAnswerColor)));
                        optionOne.setClickable(false);
                    }
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }
}